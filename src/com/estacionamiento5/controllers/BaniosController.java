
package com.estacionamiento5.controllers;

import com.estacionamiento5.repository.Repository;
import com.estacionamiento5.repository.ValidatorTypeInput;
import com.estacionamiento5.views.historialBanios;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.estacionamiento5.pojos.Banios;
import com.estacionamiento5.pojos.HisServBanios;

/**
 *
 * @author Rhoades - pc
 */
public class BaniosController extends Controller {
    
    private historialBanios view;
    
    public BaniosController() {
        this.view = new historialBanios(null, true);
    }

    @Override
    public void inicio() {
        this.view.setTitle("Historial de baños");
        this.view.setLocationRelativeTo(null);
        this.view.setResizable(false);
        this.view.btnBuscar.removeActionListener(this);
        this.view.btnResetear.removeActionListener(this);
        this.colocaFechas();
        try {
            Repository.getInstance().openSession();
            this.table(formatoFecha.format(fechaActual()), formatoFecha.format(fechaActual()));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar tabla de auditoria:\n"+ex.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);
        }
        this.view.btnBuscar.addActionListener(this);
        this.view.btnResetear.addActionListener(this);
        this.view.show();
    }
    
    private void colocaFechas()
    {
        view.tdFecha1.setText(formatoFecha.format(fechaActual()));
        view.tdFecha2.setText(formatoFecha.format(fechaActual()));
    }
    
    private void table(String date1, String date2)
    {
        ArrayList<Object> array = Repository.getInstance().getBaniosRepository().getHisBanios(date1, date2);
        cleanTable(view.tbHistorialBanios);
        if(array.isEmpty()) {
            JOptionPane.showMessageDialog(null, "¡No se encontró resultados!","Error!",JOptionPane.ERROR_MESSAGE);
        } else {
            tab = (DefaultTableModel) view.tbHistorialBanios.getModel();
            for (Object objs : array) {
                Object[] obj = (Object[]) objs;
                HisServBanios his = (HisServBanios) obj[0];
                Banios ba = (Banios) obj[1];
                tab.addRow(new Object[] { his.getId(), ba.getNombre(), his.getCosto(), his.getFecha().toLocaleString() });
            }
        }
    }
        
    public void addServicio(String servicio)
    {
        try {
            Repository.getInstance().openSession();
            Repository.getInstance().openTransaction();
            Banios banios = (Banios) Repository.getInstance().getObject(new Banios().getClass(), "nombre", servicio);
            HisServBanios his = new HisServBanios(banios.getId(), banios.getCosto(), fechaActual());
            Repository.getInstance().addObject(his);
            Repository.getInstance().commit();
            JOptionPane.showMessageDialog(null, "El servicio de baño se agrego correctamente");
        } catch (Exception ex) {
            Repository.getInstance().rollback();
            JOptionPane.showMessageDialog(null, "Error al agregar servicio de baños:\n"+ex.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void table() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validator() throws Exception {
        String fecha1 = view.tdFecha1.getText().trim();      
        if(fecha1.isEmpty()) {
            view.tdFecha1.requestFocus();
            throw new Exception("La fecha de inicio es obligatoria");
        } else {
            if(!ValidatorTypeInput.getInstance().isDate(fecha1)) {
                view.tdFecha1.requestFocus();
                throw new Exception("La fecha de inicio debe ser en formato de fecha");
            }
        }
        
        String fecha2 = view.tdFecha2.getText().trim();
        if(fecha2.isEmpty()) {
            view.tdFecha2.requestFocus();
            throw new Exception("La fecha final es obligatoria");
        } else {
            if(!ValidatorTypeInput.getInstance().isDate(fecha2)) {
                view.tdFecha2.requestFocus();
                throw new Exception("La fecha final debe ser en formato de fecha");
            }
        }
        
        if(!ValidatorTypeInput.getInstance().validateDatesString(fecha1, fecha2)) {
            view.tdFecha1.requestFocus();
            throw new Exception("La fecha inicial de ser menor a la fecha final");
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == view.btnBuscar) {
            try {
                Repository.getInstance().openSession();
                validator();
                table(view.tdFecha1.getText().trim(), view.tdFecha2.getText().trim());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al buscar datos de auditoria de baños:\n"+ex.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);
            }
        }
        
        if(ae.getSource() == view.btnResetear) {
            try {
                colocaFechas();
                Repository.getInstance().openSession();
                table(formatoFecha.format(fechaActual()), formatoFecha.format(fechaActual()));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al resetear historial de baños:\n"+ex.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);
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
