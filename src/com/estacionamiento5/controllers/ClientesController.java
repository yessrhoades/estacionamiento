
package com.estacionamiento5.controllers;

import com.estacionamiento5.pojos.Clientes;
import com.estacionamiento5.repository.Repository;
import com.estacionamiento5.views.ClientesView;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.estacionamiento5.pojos.Coches;
import com.estacionamiento5.pojos.Pensiones;
import com.estacionamiento5.pojos.ServicioPension;
/**
 *
 * @author Rhoades - pc
 */
public class ClientesController extends Controller {
    
    private ClientesView ClientesView;
    private Clientes ClientesModel;    
    
    public ClientesController() {
        ClientesView = new ClientesView(null, true);
        ClientesModel = new Clientes();
    }

    @Override
    public void inicio() {
        //propiedades de la vista
        ClientesView.setTitle("Clientes");
        ClientesView.setLocationRelativeTo(null);
        ClientesView.setResizable(false);
        //remover listener
        ClientesView.btnFinalizar.removeActionListener(this);
        ClientesView.btnCancelar.removeActionListener(this);
        ClientesView.btnEliminar.removeActionListener(this);
        ClientesView.btnActualizar.removeActionListener(this);
        ClientesView.tbClientes.removeMouseListener(this);
        //listener de botones
        ClientesView.btnFinalizar.addActionListener(this);
        ClientesView.btnCancelar.addActionListener(this);
        ClientesView.btnEliminar.addActionListener(this);
        ClientesView.btnActualizar.addActionListener(this);
        ClientesView.tbClientes.addMouseListener(this);
        //ClientesView.tbClientes.disable();
        //
        ClientesView.tfNombre.removeKeyListener(this);
        ClientesView.tfNombre.addKeyListener(this);
        //carga tabla        
        try {
            Repository.getInstance().openSession();
            table2("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Error al cargar tabla de clientes:\n" + ex.getMessage(), "Error!",JOptionPane.ERROR_MESSAGE);
        }
        //mostrar vista
        ClientesView.show();
    }
    
    @Override
    public void table() {
        
    }
    
    public void table2(String value) {
        int x = 1;
        ArrayList<Clientes> arr = Repository.getInstance().searchObjectLike(ClientesModel.getClass(), "nombre", value);
        cleanTable(ClientesView.tbClientes);
        tab = (DefaultTableModel) ClientesView.tbClientes.getModel();
        for(Clientes cl : arr) {
            tab.addRow(new Object[]{ x, cl.getIdCliente(), cl.getNombre(), cl.getAlias(), cl.getFechaAlta().toLocaleString(), cl.getFechaAlta() });
            x++;
        }
    }
    
    private void limpiarFormulario() {
        ClientesView.tfNombre.setText("");
        ClientesView.tfAlias.setText("");
        ClientesView.tfNombre.requestFocus();
    }
    
    @Override
    public void validator() throws Exception {
        String name = ClientesView.tfNombre.getText().trim().toUpperCase();
        if(name.isEmpty()) {
            ClientesView.tfNombre.requestFocus();
            throw new Exception("El campo nombre es obligatorio");
        }        
        ClientesModel.setNombre(name);
        
        String alias = ClientesView.tfAlias.getText().trim().toUpperCase();
        ClientesModel.setAlias(alias);
        ClientesModel.setFechaAlta(fechaActual());
    }
    
    private boolean verificadorFila()
    {
        if(ClientesView.tbClientes.getSelectedRow() == -1) return false;
        else return true;
    }
    
    public void newModel() {
        try {
            Repository.getInstance().openSession();
            Repository.getInstance().openTransaction();
            validator();
            int random = (int) (Math.random()*10000+1);
            String name = ClientesModel.getNombre();
            ClientesModel.setIdCliente("CL"+name.substring(0, 3)+random);            
            Repository.getInstance().addObject(ClientesModel);
            table2("");
            Repository.getInstance().commit();
            limpiarFormulario();
            JOptionPane.showMessageDialog(null,"EL CLIENTE FUE DADO DE ALTA CORRECTAMENTE");
        } catch (Exception ex) {
            Repository.getInstance().rollback();
            JOptionPane.showMessageDialog(null,"Error al agregar nuevo cliente:\n" + ex.getMessage(), "Error!",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void updateModel() {
        try {
            if(!verificadorFila()) {
                JOptionPane.showMessageDialog(null,"Seleccione el cliente a actualizar en la tabla", "Error!",JOptionPane.ERROR_MESSAGE);
                return;
            }
            Repository.getInstance().openSession();
            Repository.getInstance().openTransaction();            
            validator();
            ClientesModel.setIdCliente(ClientesView.tbClientes.getValueAt(ClientesView.tbClientes.getSelectedRow(), 1).toString());
            Repository.getInstance().updateObject(ClientesModel);
            table2("");
            Repository.getInstance().commit();
            limpiarFormulario();            
            JOptionPane.showMessageDialog(null,"EL CLIENTE SE ACTUALIZO CORRECTAMENTE");
        } catch (Exception ex) {
            Repository.getInstance().rollback();
            JOptionPane.showMessageDialog(null,"Error al actualizar cliente:\n" + ex.getMessage(), "Error!",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void deleteModel() {
        
        try {
            if(!verificadorFila()) {
                JOptionPane.showMessageDialog(null,"Seleccione el cliente a eliminar en la tabla", "Error!",JOptionPane.ERROR_MESSAGE);
                return;
            }
            Repository.getInstance().openSession();
            Repository.getInstance().openTransaction();
            int result = JOptionPane.showConfirmDialog(null, "Si elimina al cliente también lo hará con los coches que pueda tener, desea continuar?","Opción!", JOptionPane.YES_NO_OPTION);
            if(result == 0) {
                ClientesModel.setIdCliente(ClientesView.tbClientes.getValueAt(ClientesView.tbClientes.getSelectedRow(), 1).toString());
                ClientesModel.setNombre("");
                //valida si tiene pensiones
                //selecciona coches
                ServicioPension servicio = new ServicioPension();
                ArrayList<Coches> coches = Repository.getInstance().searchObjectLike(new Coches().getClass(), "idCliente", ClientesModel.getIdCliente());
                //valida si tiene coches
                if(!coches.isEmpty()) {
                    for(Coches coche : coches) {
                        ArrayList<Pensiones> pensiones = Repository.getInstance().searchObjectLike(servicio.getClass(), "idCoche", coche.getIdCoche());
                        //valida si el coche tiene pensiones asociadas
                        if(!pensiones.isEmpty()) {
                            throw new Exception("No puede eliminar cliente porque tiene coches asociados con pensiones");                            
                        }
                    }
                }
                //elimina el cliente
                Repository.getInstance().deleteObject(ClientesModel);
                table2("");
                Repository.getInstance().commit();
                limpiarFormulario();                
                JOptionPane.showMessageDialog(null, "EL CLIENTE SE ELIMINO CORRECTAMENTE");
            }            
        } catch (Exception ex) {
            Repository.getInstance().rollback();
            JOptionPane.showMessageDialog(null,"Error al eliminar cliente:\n" + ex.getMessage(), "Error!",JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == ClientesView.btnFinalizar) newModel();
        if(ae.getSource() == ClientesView.btnEliminar) deleteModel();
        if(ae.getSource() == ClientesView.btnActualizar) updateModel();
        if(ae.getSource() == ClientesView.btnCancelar) {
            limpiarFormulario();
            try {
                Repository.getInstance().openSession();
                table2("");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,"Error al resetear ventana de clientes:\n" + e.getMessage(), "Error!",JOptionPane.ERROR_MESSAGE);
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
        if(ke.getSource() == ClientesView.tfNombre) {
            if(!verificadorFila()) {
                try {
                    //Repository.getInstance().openSession();
                    table2(ClientesView.tfNombre.getText().trim().toUpperCase());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,"Error en el buscador de clientes:\n" + ex.getMessage(), "Error!",JOptionPane.ERROR_MESSAGE);
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
        if(e.getClickCount()== 1){

            int fila = ClientesView.tbClientes.getSelectedRow();

            if (fila > -1){
                ClientesView.tfNombre.setText(ClientesView.tbClientes.getValueAt(ClientesView.tbClientes.getSelectedRow(), 2).toString());
                ClientesView.tfAlias.setText(ClientesView.tbClientes.getValueAt(ClientesView.tbClientes.getSelectedRow(), 3).toString());
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
