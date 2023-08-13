
package com.estacionamiento5.controllers;

import com.estacionamiento5.repository.Repository;
import com.estacionamiento5.repository.ValidatorTypeInput;
import com.estacionamiento5.views.historialLotes;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.estacionamiento5.pojos.Auditoria;
import com.estacionamiento5.pojos.HisServLote;
import com.estacionamiento5.pojos.ServiciosLote;

/**
 *
 * @author Rhoades - pc
 */
public class HisLotesController extends Controller {
    
    private historialLotes hisLotesView;
    
    private List<Object> ls;
    private Iterator<Object> it;
    
    public HisLotesController() {
        this.hisLotesView = new historialLotes(null, true);
    }

    @Override
    public void inicio() {
        this.hisLotesView.setTitle("Historial de servicios de lotes");
        this.hisLotesView.setLocationRelativeTo(null);
        this.hisLotesView.setResizable(false);
        this.hisLotesView.btnBuscar.removeActionListener(this);
        this.hisLotesView.btnResetear.removeActionListener(this);
        tableRenderHistorial tr = new tableRenderHistorial();
        this.colocaFechas();
        try {
            Repository.getInstance().openSession();
            this.table(formatoFecha.format(fechaActual()), formatoFecha.format(fechaActual()));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar tabla de historial de lote:\n"+ex.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);
        }
        hisLotesView.tbHistorialLotes.setDefaultRenderer(Object.class, tr);
        this.hisLotesView.btnBuscar.addActionListener(this);
        this.hisLotesView.btnResetear.addActionListener(this);
        this.hisLotesView.show();
    }

    private void colocaFechas()
    {
        hisLotesView.tdFecha1.setText(formatoFecha.format(fechaActual()));
        hisLotesView.tdFecha2.setText(formatoFecha.format(fechaActual()));
    }
    
    private void table(String fecha1, String fecha2) {
        int x = 1;
        ArrayList<Object> array = Repository.getInstance().getHisLotesRepository().getHisServLote(fecha1, fecha2);
        cleanTable(hisLotesView.tbHistorialLotes);
        if(array.isEmpty()) {
            JOptionPane.showMessageDialog(null, "¡No se encontró resultados!","Error!",JOptionPane.ERROR_MESSAGE);
        } else {
            tab = (DefaultTableModel) this.hisLotesView.tbHistorialLotes.getModel();
            it = array.iterator();        
            while(it.hasNext()) {
                Object[] obj = (Object[]) it.next();
                HisServLote hsl = (HisServLote) obj[0];
                ServiciosLote sl = (ServiciosLote) obj[1];
                Auditoria au = (Auditoria) obj[2];
                tab.addRow(new Object[]{ au.isTransaccion(), x, hsl.getIdServicio(), hsl.getIdLote(), sl.getNombre(), au.getFecha1().toLocaleString(), au.getFecha2().toLocaleString(), au.getFecha().toLocaleString(), hsl.getTiempo(), au.getCosto() });
                x++;
            }
        }
    }
    
    private void buscarHislotes() throws Exception
    {
        String fecha1 = hisLotesView.tdFecha1.getText().trim();
        if(fecha1.isEmpty()) {
            hisLotesView.tdFecha1.requestFocus();
            throw new Exception("La fecha de inicio es obligatoria");
        } else {
            if(!ValidatorTypeInput.getInstance().isDate(fecha1)) {
                hisLotesView.tdFecha1.requestFocus();
                throw new Exception("La fecha de inicio debe ser en formato de fecha");
            }
        }
        
        String fecha2 = hisLotesView.tdFecha2.getText().trim();
        if(fecha2.isEmpty()) {
            hisLotesView.tdFecha2.requestFocus();
            throw new Exception("La fecha final es obligatoria");
        } else {
            if(!ValidatorTypeInput.getInstance().isDate(fecha2)) {
                hisLotesView.tdFecha2.requestFocus();
                throw new Exception("La fecha final debe ser en formato de fecha");
            }
        }
        
        if(!ValidatorTypeInput.getInstance().validateDatesString(fecha1, fecha2)) {
            hisLotesView.tdFecha1.requestFocus();
            throw new Exception("La fecha inicial de ser menor a la fecha final");
        }
        
        this.table(fecha1, fecha2);
    }
    
    @Override
    public void table() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validator() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == hisLotesView.btnBuscar) {
            try {
                Repository.getInstance().openSession();
                this.buscarHislotes();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al buscar datos de auditoria:\n"+ex.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);
            }
        } 
        
        if(ae.getSource() == hisLotesView.btnResetear) {
            try {
                colocaFechas();
                Repository.getInstance().openSession();
                this.table(formatoFecha.format(fechaActual()), formatoFecha.format(fechaActual()));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al resetear auditoria:\n"+ex.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);
            }
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
