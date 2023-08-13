
package com.estacionamiento5.controllers;

import com.estacionamiento5.repository.Repository;
import com.estacionamiento5.repository.ValidatorTypeInput;
import com.estacionamiento5.views.preciosPensiones;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import com.estacionamiento5.pojos.Pensiones;

/**
 *
 * @author Rhoades - pc
 */
public class PreciosPensionesController extends Controller {
    
    private preciosPensiones view;
    private Pensiones pensiones;
    
    public PreciosPensionesController() {
        this.view = new preciosPensiones(null, true);
        this.pensiones = new Pensiones();
    }

    @Override
    public void inicio() {
        view.setTitle("precios servicios de Pensiones");
        view.setLocationRelativeTo(null);
        view.setResizable(false);
        view.jbtnGuardarPrecioPensiones.removeActionListener(this);
        view.jcbPreciosPensiones.removeActionListener(this);
        try {
            Repository.getInstance().openSession();
            combo();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Error al poblar combo:\n" + ex.getMessage(), "Error!",JOptionPane.ERROR_MESSAGE);
        }
        view.jtfPreciosPensiones.setText("");
        view.jbtnGuardarPrecioPensiones.addActionListener(this);
        view.jcbPreciosPensiones.addActionListener(this);
        view.show();
    }
    
    private void mandarPrecios()
    {
        if(view.jcbPreciosPensiones.getSelectedIndex() == 0) {
            view.jtfPreciosPensiones.setText("");
        } else {
            BigDecimal precio = view.jcbPreciosPensiones.getItemAt(view.jcbPreciosPensiones.getSelectedIndex()).getCosto();
            view.jtfPreciosPensiones.setText(precio.toString()); 
            view.jtfPreciosPensiones.requestFocus();
        }
    }
    
    private void combo()
    {
        JComboBox<Pensiones> combo = (JComboBox) view.jcbPreciosPensiones;
        combo.removeAllItems();
        ArrayList<Pensiones> arr = Repository.getInstance().getObjectAsc(pensiones.getClass(),"nombre");
        combo.addItem(new Pensiones(null,"Elija tipo de Pensión...",null,null));
        for(Pensiones pen : arr) {
            combo.addItem(new Pensiones(pen.getId(), pen.getNombre(), pen.getPeriodo(), pen.getCosto()));
        }
    }

    @Override
    public void table() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validator() throws Exception {
        if(view.jcbPreciosPensiones.getSelectedIndex() == 0) {
            view.jcbPreciosPensiones.requestFocus();
            throw new Exception("Seleccione un tipo de pensión");
        }        
        String precio = view.jtfPreciosPensiones.getText().trim();
        if(precio.isEmpty()) {
            view.jtfPreciosPensiones.requestFocus();
            throw new Exception("Coloque un precio al servicio seleccionado");
        }
        if(!ValidatorTypeInput.getInstance().isNumeric(precio)) {
            view.jtfPreciosPensiones.requestFocus();
            throw new Exception("El precio de ser en formato numérico");
        }
    }
    
    private void actualizarServicio()
    {
        try {
            Repository.getInstance().openSession();
            Repository.getInstance().openTransaction();
            validator();
            String idServ = view.jcbPreciosPensiones.getItemAt(view.jcbPreciosPensiones.getSelectedIndex()).getId();
            String nombreServ = view.jcbPreciosPensiones.getItemAt(view.jcbPreciosPensiones.getSelectedIndex()).getNombre();
            BigDecimal precio = new BigDecimal(view.jtfPreciosPensiones.getText().trim());
            int periodo = view.jcbPreciosPensiones.getItemAt(view.jcbPreciosPensiones.getSelectedIndex()).getPeriodo();
            
            pensiones.setId(idServ);
            pensiones.setNombre(nombreServ);
            pensiones.setPeriodo(periodo);
            pensiones.setCosto(precio);
            Repository.getInstance().updateObject(pensiones);
            Repository.getInstance().commit();
            inicio();
            JOptionPane.showMessageDialog(null, "El servicio "+nombreServ+" ha sido actualizado");            
        } catch (Exception ex) {
            Repository.getInstance().rollback();
            JOptionPane.showMessageDialog(null, "Error al actualizar precio del servicio de pensiones:\n"+ex.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == view.jcbPreciosPensiones) mandarPrecios();
        if(ae.getSource() == view.jbtnGuardarPrecioPensiones) actualizarServicio();
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
