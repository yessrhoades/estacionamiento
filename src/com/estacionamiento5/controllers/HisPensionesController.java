
package com.estacionamiento5.controllers;

import com.estacionamiento5.repository.Repository;
import com.estacionamiento5.repository.ValidatorTypeInput;
import com.estacionamiento5.views.historialPensiones;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.estacionamiento5.pojos.Auditoria;
import com.estacionamiento5.pojos.Clientes;
import com.estacionamiento5.pojos.Coches;
import com.estacionamiento5.pojos.Pensiones;
import com.estacionamiento5.pojos.ServicioPension;

/**
 *
 * @author Rhoades - pc
 */
public class HisPensionesController extends Controller {
    
    private historialPensiones view;
    private ServicioPension servicio;
    
    public HisPensionesController() {
        this.view = new historialPensiones(null, true);
        this.servicio = new ServicioPension();
    }

    @Override
    public void inicio() {
        this.view.setTitle("Historial de pensiones");
        this.view.setLocationRelativeTo(null);
        this.view.setResizable(false);
        this.view.btnBuscar.removeActionListener(this);
        this.view.btnResetear.removeActionListener(this);
        tableRenderHistorial tr = new tableRenderHistorial();
        this.colocaFechas();
        try {
            Repository.getInstance().openSession();
            this.table(formatoFecha.format(fechaActual()), formatoFecha.format(fechaActual()));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar tabla de auditoria:\n"+ex.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);
        }
        view.tbHistorialpensiones.setDefaultRenderer(Object.class, tr);
        this.view.btnBuscar.addActionListener(this);
        this.view.btnResetear.addActionListener(this);
        this.view.show();
    }
    
    private void colocaFechas()
    {
        view.tdFecha1.setText(formatoFecha.format(fechaActual()));
        view.tdFecha2.setText(formatoFecha.format(fechaActual()));
    }

    private void table(String date1, String date2) {        
        ArrayList<Object> array = Repository.getInstance().getPensionesRepository().getPensiones(date1, date2);
        cleanTable(view.tbHistorialpensiones);
        if(array.isEmpty()) {
            JOptionPane.showMessageDialog(null, "¡No se encontró resultados!","Error!",JOptionPane.ERROR_MESSAGE);
        } else {
            int x = 1;
            Coches c;
            Clientes cl;
            Pensiones p;
            Auditoria au;
            tab = (DefaultTableModel) view.tbHistorialpensiones.getModel();
            for(Object Obj : array) {
                Object[] obj = (Object[]) Obj;
                servicio = (ServicioPension) obj[0];
                c = (Coches) obj[1];
                cl = (Clientes) obj[2];
                p = (Pensiones) obj[3];
                au = (Auditoria) obj[4];
                tab.addRow(new Object[]{ au.isTransaccion(), x, p.getNombre(), cl.getNombre()+" - "+cl.getAlias(), c.getModelo()+" - "+c.getPlacas(), formatoFecha.format(au.getFecha1()), formatoFecha.format(au.getFecha2()), au.getFecha().toLocaleString(), au.getCosto() });
                x++;
            }
        }
    }
    
    private void buscarPensiones() throws Exception
    {
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
        
        table(fecha1, fecha2);
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
        if(ae.getSource() == view.btnBuscar) {
            try {
                Repository.getInstance().openSession();
                this.buscarPensiones();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al buscar datos de historial de pensiones:\n"+ex.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);
            }
        } 
        
        if(ae.getSource() == view.btnResetear) {
            try {
                colocaFechas();
                Repository.getInstance().openSession();
                table(formatoFecha.format(fechaActual()), formatoFecha.format(fechaActual()));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al resetear historial de pensiones:\n"+ex.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);
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
