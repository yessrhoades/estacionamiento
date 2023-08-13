
package com.estacionamiento5.controllers;

import com.estacionamiento5.repository.Repository;
import com.estacionamiento5.repository.ValidatorTypeInput;
import com.estacionamiento5.views.historialAuditoria;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.estacionamiento5.pojos.Auditoria;

/**
 *
 * @author Rhoades - pc
 */
public class AuditoriaController extends Controller {
    
    private historialAuditoria auditoriaView;
    
    public AuditoriaController() {
        this.auditoriaView = new historialAuditoria(null, true);
    }

    @Override
    public void inicio() {
        this.auditoriaView.setTitle("Historial de servicios");
        this.auditoriaView.setLocationRelativeTo(null);
        this.auditoriaView.setResizable(false);
        this.auditoriaView.btnBuscar.removeActionListener(this);
        this.auditoriaView.btnResetear.removeActionListener(this);
        tableRenderHistorial tr = new tableRenderHistorial();
        this.colocaFechas();
        try {
            Repository.getInstance().openSession();
            this.table(formatoFecha.format(fechaActual()), formatoFecha.format(fechaActual()));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar tabla de auditoria:\n"+ex.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);
        }
        auditoriaView.tbHistorialAuditoria.setDefaultRenderer(Object.class, tr);
        this.auditoriaView.btnBuscar.addActionListener(this);
        this.auditoriaView.btnResetear.addActionListener(this);
        this.auditoriaView.show();
    }
    
    private void colocaFechas()
    {
        auditoriaView.tdFecha1.setText(formatoFecha.format(fechaActual()));
        auditoriaView.tdFecha2.setText(formatoFecha.format(fechaActual()));
    }

    private void table(String date1, String date2) throws ParseException {        
        ArrayList<Auditoria> array = Repository.getInstance().searchObjectByDate("Auditoria", "fecha", "idServicio", date1, date2);
        cleanTable(auditoriaView.tbHistorialAuditoria);
        if(array.isEmpty()) {
            JOptionPane.showMessageDialog(null, "¡No se encontró resultados!","Error!",JOptionPane.ERROR_MESSAGE);
        } else {
            tab = (DefaultTableModel) auditoriaView.tbHistorialAuditoria.getModel();
            for(Auditoria au : array) {
                tab.addRow(new Object[] { au.isTransaccion(), au.getIdServicio(), au.getTipoServicio(), au.getFecha1().toLocaleString(), au.getFecha2().toLocaleString(), au.getCosto(), au.getFecha().toLocaleString() });
            }
        }
    }

    private void buscarAuditoria() throws Exception
    {
        String fecha1 = auditoriaView.tdFecha1.getText().trim();        
        if(fecha1.isEmpty()) {
            auditoriaView.tdFecha1.requestFocus();
            throw new Exception("La fecha de inicio es obligatoria");
        } else {
            if(!ValidatorTypeInput.getInstance().isDate(fecha1)) {
                auditoriaView.tdFecha1.requestFocus();
                throw new Exception("La fecha de inicio debe ser en formato de fecha");
            }
        }
        
        String fecha2 = auditoriaView.tdFecha2.getText().trim();
        if(fecha2.isEmpty()) {
            auditoriaView.tdFecha2.requestFocus();
            throw new Exception("La fecha final es obligatoria");
        } else {
            if(!ValidatorTypeInput.getInstance().isDate(fecha2)) {
                auditoriaView.tdFecha2.requestFocus();
                throw new Exception("La fecha final debe ser en formato de fecha");
            }
        }
        
        if(!ValidatorTypeInput.getInstance().validateDatesString(fecha1, fecha2)) {
            auditoriaView.tdFecha1.requestFocus();
            throw new Exception("La fecha inicial de ser menor a la fecha final");
        }
        
        table(fecha1, fecha2);
    }
    
    
    @Override
    public void validator() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == auditoriaView.btnBuscar) {
            try {
                Repository.getInstance().openSession();
                this.buscarAuditoria();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al buscar datos de auditoria:\n"+ex.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);
            }
        } 
        
        if(ae.getSource() == auditoriaView.btnResetear) {
            try {
                colocaFechas();
                Repository.getInstance().openSession();
                table(formatoFecha.format(fechaActual()), formatoFecha.format(fechaActual()));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al resetear auditoria:\n"+ex.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);
            }
        }
        
    }

    @Override
    public void table() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
