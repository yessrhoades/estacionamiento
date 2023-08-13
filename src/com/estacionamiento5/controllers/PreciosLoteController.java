
package com.estacionamiento5.controllers;

import com.estacionamiento5.repository.Repository;
import com.estacionamiento5.repository.ValidatorTypeInput;
import com.estacionamiento5.views.preciosLotes;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import com.estacionamiento5.pojos.ServiciosLote;
import com.estacionamiento5.views.panelprincipal;

/**
 *
 * @author Rhoades - pc
 */
public class PreciosLoteController extends Controller {
    
    private preciosLotes view;
    private ServiciosLote model;
    private panelprincipal principalView;
    private LotesController lotesController;
    
    public PreciosLoteController(panelprincipal principalView, LotesController lotesController) {
        this.view = new preciosLotes(null, true);
        this.model = new ServiciosLote();
        this.principalView = principalView;
        this.lotesController = lotesController;
    }

    @Override
    public void inicio() {
        view.setTitle("precios servicios de Lotes");
        view.setLocationRelativeTo(null);
        view.setResizable(false);
        view.jbtnGuardarPrecio.removeActionListener(this);
        view.jcbPreciosLote.removeActionListener(this);
        try {
            Repository.getInstance().openSession();
            poblarCombo();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar combo de servicios de lote:\n"+ex.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);
        }
        view.jtfPreciosLote.setText("");
        view.jbtnGuardarPrecio.addActionListener(this);
        view.jcbPreciosLote.addActionListener(this);
        view.show();
    }
    
    private void poblarCombo()
    {
        ArrayList<ServiciosLote> arr = Repository.getInstance().getObjectAsc(model.getClass(),"nombre");
        JComboBox combo = (JComboBox) this.view.jcbPreciosLote;
        combo.removeAllItems();
        combo.addItem(new ServiciosLote(null,"Elija servicio...",null));
        for(ServiciosLote sl : arr) {
            combo.addItem(new ServiciosLote(sl.getId(), sl.getNombre(), sl.getCosto()));
        }
    }
    
    private void mandarPrecios()
    {
        BigDecimal precio = view.jcbPreciosLote.getItemAt(view.jcbPreciosLote.getSelectedIndex()).getCosto();
        view.jtfPreciosLote.setText(precio.toString());
        view.jtfPreciosLote.requestFocus();
    }
    
    private void actualizarServicio()
    {
        try {
            Repository.getInstance().openSession();
            Repository.getInstance().openTransaction();
            validator();
            String idServ = view.jcbPreciosLote.getItemAt(view.jcbPreciosLote.getSelectedIndex()).getId();
            String nombreServ = view.jcbPreciosLote.getItemAt(view.jcbPreciosLote.getSelectedIndex()).getNombre();
            BigDecimal precio = new BigDecimal(view.jtfPreciosLote.getText().trim());
            
            model.setId(idServ);
            model.setNombre(nombreServ);
            model.setCosto(precio);
            Repository.getInstance().updateObject(model);
            lotesController.ComboServicios();
            Repository.getInstance().commit();
            principalView.jcbservicio.setSelectedIndex(0);            
            inicio();
            JOptionPane.showMessageDialog(null, "El servicio "+nombreServ+" ha sido actualizado");
        } catch (Exception ex) {
            Repository.getInstance().rollback();
            JOptionPane.showMessageDialog(null, "Error al actualizar precio del servicio de lote:\n"+ex.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void table() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validator() throws Exception {
        if(view.jcbPreciosLote.getSelectedIndex() == 0) {
            view.jcbPreciosLote.requestFocus();
            throw new Exception("Seleccione un tipo de servicio");
        }        
        String precio = view.jtfPreciosLote.getText().trim();
        if(precio.isEmpty()) {
            view.jtfPreciosLote.requestFocus();
            throw new Exception("Coloque un precio al servicio seleccionado");
        }
        if(!ValidatorTypeInput.getInstance().isNumeric(precio)) {
            view.jtfPreciosLote.requestFocus();
            throw new Exception("El precio de ser en formato numérico");
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == view.jbtnGuardarPrecio) {
            actualizarServicio();
        }
        
        if(ae.getSource() == view.jcbPreciosLote) {
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
