
package com.estacionamiento5.controllers;

import com.estacionamiento5.repository.Repository;
import com.estacionamiento5.repository.ValidatorTypeInput;
import com.estacionamiento5.views.preciosBanios;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import com.estacionamiento5.pojos.Banios;

/**
 *
 * @author Rhoades - pc
 */
public class PreciosBaniosController extends Controller {
    
    private preciosBanios view;
    private Banios model;
    
    public PreciosBaniosController() {
        this.view = new preciosBanios(null, true);
        this.model = new Banios();
    }

    @Override
    public void inicio() {
        view.setTitle("Precios servicios de Baños");
        view.setLocationRelativeTo(null);
        view.setResizable(false);
        view.btnGuardar.removeActionListener(this);
        view.jcbPreciosBanios.removeActionListener(this);
        try {
            Repository.getInstance().openSession();
            poblarCombo();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar combo de servicios de Baños:\n"+ex.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);
        }
        view.tfPreciosBanios.setText("");
        view.btnGuardar.addActionListener(this);
        view.jcbPreciosBanios.addActionListener(this);
        view.show();
    }
    
    private void poblarCombo()
    {
        ArrayList<Banios> arr = Repository.getInstance().getObjectAsc(model.getClass(),"nombre");
        JComboBox combo = (JComboBox) this.view.jcbPreciosBanios;
        combo.removeAllItems();
        combo.addItem(new Banios(null,"Elija servicio...",null));
        for(Banios b : arr) {
            combo.addItem(new Banios(b.getId(),b.getNombre(),b.getCosto()));
        }
    }
    
    private void mandarPrecios()
    {
        BigDecimal precio = view.jcbPreciosBanios.getItemAt(view.jcbPreciosBanios.getSelectedIndex()).getCosto();
        view.tfPreciosBanios.setText(precio.toString());
        view.tfPreciosBanios.requestFocus();
    }

    @Override
    public void table() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validator() throws Exception {
        if(view.jcbPreciosBanios.getSelectedIndex() == 0) {
            view.jcbPreciosBanios.requestFocus();
            throw new Exception("Seleccione un tipo de servicio");
        }        
        String precio = view.tfPreciosBanios.getText().trim();
        if(precio.isEmpty()) {
            view.tfPreciosBanios.requestFocus();
            throw new Exception("Coloque un precio al servicio seleccionado");
        }
        if(!ValidatorTypeInput.getInstance().isNumeric(precio)) {
            view.tfPreciosBanios.requestFocus();
            throw new Exception("El precio de ser en formato numérico");
        }
    }
    
    private void actualizarServicio()
    {
        try {
            Repository.getInstance().openSession();
            Repository.getInstance().openTransaction();
            validator();
            String idServ = view.jcbPreciosBanios.getItemAt(view.jcbPreciosBanios.getSelectedIndex()).getId();
            String nombreServ = view.jcbPreciosBanios.getItemAt(view.jcbPreciosBanios.getSelectedIndex()).getNombre();
            BigDecimal precio = new BigDecimal(view.tfPreciosBanios.getText().trim());
            
            model.setId(idServ);
            model.setNombre(nombreServ);
            model.setCosto(precio);
            Repository.getInstance().updateObject(model);                           
            Repository.getInstance().commit();
            inicio();
            JOptionPane.showMessageDialog(null, "El servicio "+nombreServ+" ha sido actualizado");            
        } catch (Exception ex) {
            Repository.getInstance().rollback();
            JOptionPane.showMessageDialog(null, "Error al actualizar precio del servicio de baños:\n"+ex.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == view.btnGuardar) {
            actualizarServicio();
        }
        
        if(ae.getSource() == view.jcbPreciosBanios) {
            mandarPrecios();
        }
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
