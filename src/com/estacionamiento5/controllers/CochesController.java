
package com.estacionamiento5.controllers;

import com.estacionamiento5.repository.Repository;
import com.estacionamiento5.views.NewCoche;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.estacionamiento5.pojos.Clientes;
import com.estacionamiento5.pojos.Coches;
import com.estacionamiento5.pojos.ServicioPension;
import java.util.Date;

/**
 *
 * @author Rhoades - pc
 */
public class CochesController extends Controller {
    
    private NewCoche view;
    private Coches model;
    
    List<Object> ls;
    Iterator<Object> it;
    
    public CochesController()
    {
        this.view = new NewCoche(null, true);
        this.model = new Coches();
    }

    @Override
    public void inicio() {
        view.setTitle("Coches");
        view.setLocationRelativeTo(null);
        view.setResizable(false);
        
        view.btnNewCoche.removeActionListener(this);
        view.btnActualizar.removeActionListener(this);
        view.btnEliminar.removeActionListener(this);
        view.btnCancelar.removeActionListener(this);
        view.tbCoches.removeMouseListener(this);
        try {
            Repository.getInstance().openSession();
            table2("");
            combo();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Error al cargar datos de coches:\n" + ex.getMessage(), "Error!",JOptionPane.ERROR_MESSAGE);
        }
        limpiarFormulario();
        view.btnNewCoche.addActionListener(this);
        view.btnActualizar.addActionListener(this);
        view.btnEliminar.addActionListener(this);
        view.btnCancelar.addActionListener(this);
        view.tbCoches.addMouseListener(this);
        
        view.tfModelo.removeKeyListener(this);
        view.tfModelo.addKeyListener(this);
        view.show();
    }
    
    public void combo()
    {
        JComboBox<Clientes> combo = (JComboBox) view.cbCliente;
        combo.removeAllItems();
        combo.addItem(new Clientes(null,"Elegir cliente...","...",null));
        ArrayList<Clientes> arr = Repository.getInstance().getObjectAsc(new Clientes().getClass(),"nombre");
        for(Clientes cl : arr) {
            combo.addItem(new Clientes(cl.getIdCliente(), cl.getNombre(), cl.getAlias(), cl.getFechaAlta()));
        }
    }

    @Override
    public void table() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void table2(String modelo)
    {
        int x = 1;
        ArrayList<Object> array = Repository.getInstance().getCochesRepository().getCoches(modelo);
        cleanTable(view.tbCoches);
        tab = (DefaultTableModel) view.tbCoches.getModel();
        it = array.iterator();
        while(it.hasNext()) {
            Object[] obj = (Object[]) it.next();
            Coches ch = (Coches) obj[0];
            Clientes cl = (Clientes) obj[1];
            tab.addRow(new Object[]{ x, ch.getIdCoche(), cl.getIdCliente(), cl.getNombre() +" - "+ cl.getAlias(), ch.getModelo(), ch.getPlacas(), ch.getFechaAlta().toLocaleString(), formatoTimeStamp.format(ch.getFechaAlta()) });
            x++;
        }
    }
    
    private void limpiarFormulario() {
        view.cbCliente.setSelectedIndex(0);
        view.tfModelo.setText("");
        view.tfPlacas.setText("");
    }

    @Override
    public void validator() throws Exception {
        String cliente = view.cbCliente.getItemAt(view.cbCliente.getSelectedIndex()).getIdCliente();
        if(cliente == null) {
            view.cbCliente.requestFocus();
            throw new Exception("Debe seleccionar un cliente");
        }
        model.setIdCliente(cliente);
        
        String modelo = view.tfModelo.getText().trim().toUpperCase();
        if(modelo.isEmpty()) {
            view.tfModelo.requestFocus();
            throw new Exception("Ingrese el modelo del coche");
        }
        model.setModelo(modelo);
        
        String placas = view.tfPlacas.getText().trim().toUpperCase();
        model.setPlacas(placas);        
    }
    
    private boolean verificadorFila()
    {
        if(view.tbCoches.getSelectedRow() == -1) return false;
        else return true;
    }
    
    public void newModel() {
        try {
            Repository.getInstance().openSession();
            Repository.getInstance().openTransaction();
            validator();
            int random = (int) (Math.random()*10000+1);
            String modelo = model.getModelo();
            model.setIdCoche("CH"+modelo.substring(0, 3)+random); 
            model.setFechaAlta(fechaActual());
            Repository.getInstance().addObject(model);
            table2("");
            Repository.getInstance().commit();
            limpiarFormulario();
            JOptionPane.showMessageDialog(null,"EL COCHE FUE DADO DE ALTA CORRECTAMENTE");
        } catch (Exception ex) {
            Repository.getInstance().rollback();
            JOptionPane.showMessageDialog(null,"Error al agregar nuevo coche:\n" + ex.getMessage(), "Error!",JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateModel() {
        if(!verificadorFila()) {
            JOptionPane.showMessageDialog(null,"Seleccione el coche a actualizar en la tabla", "Error!",JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            Repository.getInstance().openSession();
            Repository.getInstance().openTransaction();
            validator();
            model.setIdCoche(view.tbCoches.getValueAt(view.tbCoches.getSelectedRow(), 1).toString());
            model.setFechaAlta(formatoTimeStamp.parse(view.tbCoches.getValueAt(view.tbCoches.getSelectedRow(), 7).toString()));
            Repository.getInstance().updateObject(model);
            table2("");
            Repository.getInstance().commit();
            limpiarFormulario();
            JOptionPane.showMessageDialog(null,"EL COCHE SE ACTUALIZO CORRECTAMENTE");
        } catch (Exception ex) {
            Repository.getInstance().rollback();
            JOptionPane.showMessageDialog(null,"Error al actualizar coche:\n"+ex.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteModel() {
        if(!verificadorFila()) {
            JOptionPane.showMessageDialog(null,"Seleccione el coche a eliminar en la tabla", "Error!",JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {            
            Repository.getInstance().openSession();
            Repository.getInstance().openTransaction();
            int result = JOptionPane.showConfirmDialog(null, "Desea eliminar el coche?","Opción!", JOptionPane.YES_NO_OPTION);
            if(result == 0) {
                model.setIdCoche(view.tbCoches.getValueAt(view.tbCoches.getSelectedRow(), 1).toString());
                model.setIdCliente(view.tbCoches.getValueAt(view.tbCoches.getSelectedRow(), 2).toString());
                model.setModelo(view.tbCoches.getValueAt(view.tbCoches.getSelectedRow(), 4).toString());
                //valida si el coche tiene pensiones
                ServicioPension servicio = new ServicioPension();
                ArrayList<Object> pensiones = Repository.getInstance().searchObjectLike(servicio.getClass(), "idCoche", model.getIdCoche());
                if(!pensiones.isEmpty()) {
                    throw new Exception("No puede eliminar coche porque tiene pensiones asociadas");
                }
                //elimina coche
                Repository.getInstance().deleteObject(model);
                table2("");
                Repository.getInstance().commit();
                limpiarFormulario();
                JOptionPane.showMessageDialog(null, "EL COCHE SE ELIMINO CORRECTAMENTE");
            }            
        } catch (Exception ex) {
            Repository.getInstance().rollback();
            JOptionPane.showMessageDialog(null,"Error al eliminar coche:\n" + ex.getMessage(), "Error!",JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == view.btnNewCoche) newModel();
        if(ae.getSource() == view.btnActualizar) updateModel();
        if(ae.getSource() == view.btnEliminar) deleteModel();
        if(ae.getSource() == view.btnCancelar) {
            try{
                limpiarFormulario();
                Repository.getInstance().openSession();
                table2("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,"Error al resetear formlario de coches:\n" + ex.getMessage(), "Error!",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        if(ke.getSource() == view.tfModelo) {
            if(!verificadorFila()) {
                try {
                    //Repository.getInstance().openSession();
                    table2(view.tfModelo.getText().trim().toUpperCase());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,"Error en el buscador de coches:\n" + ex.getMessage(), "Error!",JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) 
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getClickCount() == 1) {
            int fila = view.tbCoches.getSelectedRow();
            if (fila > -1) {
                view.tfModelo.setText(view.tbCoches.getValueAt(view.tbCoches.getSelectedRow(), 4).toString());
                view.tfPlacas.setText(view.tbCoches.getValueAt(view.tbCoches.getSelectedRow(), 5).toString());
                String cliente = view.tbCoches.getValueAt(view.tbCoches.getSelectedRow(), 2).toString();
                int index;
                for(index = 0; index < view.cbCliente.getItemCount(); index++) {
                    if(cliente.equals(view.cbCliente.getItemAt(index).getIdCliente())) break;
                }
                view.cbCliente.setSelectedIndex(index);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
