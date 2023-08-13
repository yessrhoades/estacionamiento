
package com.estacionamiento5.controllers;

import com.estacionamiento5.reports.CorteCaja;
import com.estacionamiento5.repository.Repository;
import com.estacionamiento5.repository.ValidatorTypeInput;
import com.estacionamiento5.views.corteCaja;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Rhoades - pc
 */
public class ReporteController extends Controller {
    
    private corteCaja view;
    private CorteCaja report;
    
    private String fecha1;
    private String fecha2;
    
    public ReporteController() {
        this.view = new corteCaja(null, true);
        this.report = new CorteCaja();
    }

    @Override
    public void inicio() {
        this.view.setTitle("Reporte de servicios");
        this.view.setLocationRelativeTo(null);
        this.view.setResizable(false);
        this.view.btnExcel.removeActionListener(this);
        this.view.btnResetear.removeActionListener(this);
        this.colocaFechas();
        limpiarChecks();
        this.view.btnExcel.addActionListener(this);
        this.view.btnResetear.addActionListener(this);        
        this.view.show();
    }
    
    private void colocaFechas()
    {
        view.tfFecha1.setText(formatoFecha.format(fechaActual()));
        view.tfFecha2.setText(formatoFecha.format(fechaActual()));
    }
    
    private void limpiarChecks() {
        this.view.chLotes.setSelected(false);
        this.view.chPensiones.setSelected(false);
        this.view.chBaños.setSelected(false);
    }

    @Override
    public void table() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validator() throws Exception {
        fecha1 = view.tfFecha1.getText().trim();      
        if(fecha1.isEmpty()) {
            view.tfFecha1.requestFocus();
            throw new Exception("La fecha de inicio es obligatoria");
        } else {
            if(!ValidatorTypeInput.getInstance().isDate(fecha1)) {
                view.tfFecha1.requestFocus();
                throw new Exception("La fecha de inicio debe ser en formato de fecha");
            }
        }
        
        fecha2 = view.tfFecha2.getText().trim();
        if(fecha2.isEmpty()) {
            view.tfFecha2.requestFocus();
            throw new Exception("La fecha final es obligatoria");
        } else {
            if(!ValidatorTypeInput.getInstance().isDate(fecha2)) {
                view.tfFecha2.requestFocus();
                throw new Exception("La fecha final debe ser en formato de fecha");
            }
        }
        
        if(!ValidatorTypeInput.getInstance().validateDatesString(fecha1, fecha2)) {
            view.tfFecha1.requestFocus();
            throw new Exception("La fecha inicial de ser menor a la fecha final");
        }
        
        if(!view.chLotes.isSelected() && !view.chPensiones.isSelected() && !view.chBaños.isSelected())
            throw new Exception("Seleccione por lo menos un tipo de servicio");
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == view.btnExcel) {
            try {
                Repository.getInstance().openSession();
                validator();
                
                ArrayList<Object> banios = null;
                ArrayList<Object> lotes = null;
                ArrayList<Object> pensiones = null;
                if(view.chBaños.isSelected()) {
                    banios = Repository.getInstance().getBaniosRepository().getHisBanios(fecha1, fecha2);
                    if(banios.isEmpty()) {
                        banios = null;
                        JOptionPane.showMessageDialog(null, "No se encontraron datos de baños","Error!",JOptionPane.ERROR_MESSAGE);
                    }
                }
                if(view.chLotes.isSelected()) {
                    lotes = Repository.getInstance().getHisLotesRepository().getHisServLote(fecha1, fecha2);
                    if(lotes.isEmpty()) {
                        lotes = null;
                        JOptionPane.showMessageDialog(null, "No se encontraron datos de lotes","Error!",JOptionPane.ERROR_MESSAGE);
                    }
                }
                if(view.chPensiones.isSelected()) {
                    pensiones = Repository.getInstance().getPensionesRepository().getPensiones(fecha1, fecha2);
                    if(pensiones.isEmpty()) {
                        pensiones = null;
                        JOptionPane.showMessageDialog(null, "No se encontraron datos de pensiones","Error!",JOptionPane.ERROR_MESSAGE);
                    }
                }
                report.reporte(formatoTimeStamp.format(fechaActual()).replace(" ", "_").replace(":", ""), banios, lotes, pensiones);
                JOptionPane.showMessageDialog(null, "El reporte se generó correctamente");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al generar excel:\n"+ex.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);
            }
        }
        
        if(ae.getSource() == view.btnResetear) {
            colocaFechas();                
            limpiarChecks();
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
