
package com.estacionamiento5.controllers;

import com.estacionamiento5.reports.ticketPensiones;
import com.estacionamiento5.repository.Repository;
import com.estacionamiento5.repository.ValidatorTypeInput;
import com.estacionamiento5.views.pensiones;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import com.estacionamiento5.pojos.Auditoria;
import com.estacionamiento5.pojos.Clientes;
import com.estacionamiento5.pojos.Coches;
import com.estacionamiento5.pojos.DatosEstacionamiento;
import com.estacionamiento5.pojos.Pensiones;
import com.estacionamiento5.pojos.ServicioPension;

/**
 *
 * @author Rhoades - pc
 */
public class PensionesController extends Controller {
    
    private pensiones view;
    private ServicioPension servicio;
    
    private String fechaInicio;
    private String precio;
    
    public PensionesController() {
        this.view = new pensiones(null, true);
        this.servicio = new ServicioPension();
    }

    @Override
    public void inicio() {
        view.setTitle("Servicio de pensiones");
        view.setLocationRelativeTo(null);
        view.setResizable(false);

        tableRenderPensiones tr = new tableRenderPensiones();

        view.cbClientesPensiones.removeActionListener(this);
        view.cbPensiones.removeActionListener(this);
        view.btnNuevaPension.removeActionListener(this);
        view.btnResetear.removeActionListener(this);
        view.btnCancelar.removeActionListener(this);
        try {
            Repository.getInstance().openSession();
            comboClientes();
            comboPensiones();
            table();
        } catch (Exception ex) {
            Repository.getInstance().rollback();
            JOptionPane.showMessageDialog(null, "Error al cargar datos de pensiones:\n"+ex.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);
        }
        limpiarFormulario();
        view.tbPensiones.setDefaultRenderer(Object.class, tr);
        view.cbPensiones.addActionListener(this);
        view.cbClientesPensiones.addActionListener(this);
        view.btnNuevaPension.addActionListener(this);
        view.btnResetear.addActionListener(this);
        view.btnCancelar.addActionListener(this);
        view.show();
    }
    
    private void limpiarFormulario()
    {
        String fecha = formatoFecha.format(fechaActual());
        view.tfFecha.setText(fecha);
        view.tfCostoPension.setText("0.00");
        view.cbClientesPensiones.setSelectedIndex(0);
        view.cbPensiones.setSelectedIndex(0);
        view.cbAutomovil.removeAllItems();
    }
    
    private void setPrecio()
    {
        if(view.cbPensiones.getSelectedIndex() == 0) {
            view.tfCostoPension.setText("0.00");
        } else {
            BigDecimal costo = view.cbPensiones.getItemAt(view.cbPensiones.getSelectedIndex()).getCosto();
            view.tfCostoPension.setText(costo.toString());
        }
    }
    
    private void comboClientes()
    {
        JComboBox<Clientes> combo = (JComboBox) view.cbClientesPensiones;
        combo.removeAllItems();
        combo.addItem(new Clientes(null,"Elegir cliente...","...",null));
        ArrayList<Clientes> arr = Repository.getInstance().getObjectAsc(new Clientes().getClass(),"nombre");
        for(Clientes cl : arr) {
            combo.addItem(new Clientes(cl.getIdCliente(), cl.getNombre(), cl.getAlias(), cl.getFechaAlta()));
        }
    }
    
    private void comboPensiones()
    {
        JComboBox<Pensiones> combo = (JComboBox) view.cbPensiones;
        ArrayList<Pensiones> arr = Repository.getInstance().getObjectAsc(new Pensiones().getClass(), "nombre");
        combo.removeAllItems();
        combo.addItem(new Pensiones(null,"Elija tipo de Pensión...",null,null));
        for(Pensiones pen : arr) {
            combo.addItem(new Pensiones(pen.getId(), pen.getNombre(), pen.getPeriodo(), pen.getCosto()));
        }
    }

    @Override
    public void table() {
        int x = 1;
        Coches c;
        Clientes cl;
        Pensiones p;
        Auditoria au;
        ArrayList<Object> arr = Repository.getInstance().getPensionesRepository().getPensionesActivas();
        tab = (DefaultTableModel) view.tbPensiones.getModel();
        tab.setRowCount(0);
        for(Object Obj : arr) {
            Object[] obj = (Object[]) Obj;
            servicio = (ServicioPension) obj[0];
            c = (Coches) obj[1];
            cl = (Clientes) obj[2];
            p = (Pensiones) obj[3];
            au = (Auditoria) obj[4];
            tab.addRow(new Object[]{x, au.getIdServicio(), p.getNombre(), cl.getNombre()+" - "+cl.getAlias(), c.getModelo()+" - "+c.getPlacas(), formatoFecha.format(au.getFecha1()), formatoFecha.format(au.getFecha2()), au.getFecha().toLocaleString(), au.getCosto(), servicio.getIdCoche(), servicio.getId()});
            x++;
        }
    }
    
    private void buscarCoche()
    {
        String idc = view.cbClientesPensiones.getItemAt(view.cbClientesPensiones.getSelectedIndex()).getIdCliente();
        ArrayList<Coches> arr = Repository.getInstance().searchObjectLike(new Coches().getClass(), "idCliente", idc);
        JComboBox<Coches> combo = (JComboBox) view.cbAutomovil;
        combo.removeAllItems();
        combo.addItem(new Coches(null,null,"Elija coche...","",null));
        for(Coches c : arr) {
            combo.addItem(new Coches(c.getIdCoche() ,c.getIdCliente(), c.getModelo(), " - "+c.getPlacas(), c.getFechaAlta()));
        }
    }
    
    private void nuevoServicioPesion()
    {
        try {
            Repository.getInstance().openSession();
            Repository.getInstance().openTransaction();
            validator();
            //valida si el coche esta en servicio            
            ServicioPension serv = Repository.getInstance().getPensionesRepository().getCochePension(servicio.getIdCoche());
            if(serv != null) throw new Exception("El coche ya se encuentra en servicio");
            //calcula fecha de salida
            int periodo = view.cbPensiones.getItemAt(view.cbPensiones.getSelectedIndex()).getPeriodo();
            Date fecha_inicio = formatoFecha.parse(fechaInicio);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fecha_inicio);
            
            switch(periodo) {
                case 30 : calendar.add(Calendar.MONTH, 1); calendar.add(Calendar.DATE, -1); break;
                case 15 : calendar.add(Calendar.DATE, 14); break;
                case 7 : calendar.add(Calendar.WEEK_OF_MONTH, 1); calendar.add(Calendar.DATE, -1); break;
            }
            //agrega historial
            Auditoria auditoria = new Auditoria();
            auditoria.setTipoServicio(view.cbPensiones.getItemAt(view.cbPensiones.getSelectedIndex()).getNombre());
            auditoria.setFecha1(fecha_inicio);
            auditoria.setFecha2(calendar.getTime());
            auditoria.setCosto(new BigDecimal(precio));
            auditoria.setFecha(fechaActual());
            auditoria.setTransaccion(true);
            Repository.getInstance().addObject(auditoria);
            //agrega pension
            servicio.setIdServicio(auditoria.getIdServicio());
            servicio.setActiva(true);
            Repository.getInstance().addObject(servicio);            
            //ticket
            Object object = Repository.getInstance().getPensionesRepository().getDatosForTicket(servicio.getIdCoche());
            DatosEstacionamiento datos = (DatosEstacionamiento) Repository.getInstance().getObject(new DatosEstacionamiento().getClass(), "id", 1);
            ticketPensiones ticket = new ticketPensiones();
            ticket.ticket(object, auditoria, datos);
            limpiarFormulario();
            table();            
            Repository.getInstance().commit();
            JOptionPane.showMessageDialog(null, "El servicio de pensión se generó correctamente");
        } catch (Exception ex) {
            Repository.getInstance().rollback();
            JOptionPane.showMessageDialog(null, "Error al crear nueva pensión:\n"+ex.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void validator() throws Exception {
        
        String cliente = view.cbClientesPensiones.getItemAt(view.cbClientesPensiones.getSelectedIndex()).getIdCliente();
        if(cliente == null) {
            view.cbClientesPensiones.requestFocus();
            throw new Exception("Debe seleccionar un cliente");
        }
        
        String coche = view.cbAutomovil.getItemAt(view.cbAutomovil.getSelectedIndex()).getIdCoche();
        if(coche == null) {
            view.cbAutomovil.requestFocus();
            throw new Exception("Debe seleccionar un coche");
        }
        servicio.setIdCoche(coche);
        
        String pension = view.cbPensiones.getItemAt(view.cbPensiones.getSelectedIndex()).getId();
        if(pension == null) {
            view.cbPensiones.requestFocus();
            throw new Exception("Debe seleccionar un tipo de pensión");
        }
        servicio.setIdPension(pension);
        
        precio = view.tfCostoPension.getText().trim();
        if(precio.isEmpty()) {
            view.tfCostoPension.requestFocus();
            throw new Exception("Ingrese un costo al servicio");
        }
        if(!ValidatorTypeInput.getInstance().isNumeric(precio)) {
            view.tfCostoPension.requestFocus();
            throw new Exception("El costo debe ser de tipo numérico");
        }
        
        fechaInicio = view.tfFecha.getText().trim();
        if(fechaInicio.isEmpty()) {
            view.tfFecha.requestFocus();
            throw new Exception("Ingrese una fecha de inicio de servicio");
        }
        if(!ValidatorTypeInput.getInstance().isDate(fechaInicio)) {
            view.tfFecha.requestFocus();
            throw new Exception("La fecha de ser en formato de fecha");
        }
        
    }
    
    private boolean verificadorFila()
    {
        if(view.tbPensiones.getSelectedRow() == -1) return false;
        else return true;
    }
    
    public void cancelacion() {
        if(!verificadorFila()) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un servicio de pensión de la tabla", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            Repository.getInstance().openSession();
            Repository.getInstance().openTransaction();
            //crea objectos para confirm dialog
            JLabel jPassword = new JLabel("Contraseña");
            JTextField password = new JPasswordField();
            Object[] inputObj = {jPassword, password};
            int result = JOptionPane.showConfirmDialog(null, inputObj, "Cancelación de pensiones", JOptionPane.OK_CANCEL_OPTION);
            if(result == JOptionPane.OK_OPTION) {
                String passwd = password.getText().trim();
                if(passwd.isEmpty()) throw new Exception("Debe escribir una contraseña");
                boolean login = Repository.getInstance().getUsuariosRepository().permisoAdministrador(passwd);
                if(!login) throw new Exception("Contraseña incorrecta");
                //actualiza auditoria
                String auditoria = view.tbPensiones.getValueAt(view.tbPensiones.getSelectedRow(), 1).toString();
                String servicio = view.tbPensiones.getValueAt(view.tbPensiones.getSelectedRow(), 10).toString();
                //actualiza auditoria
                Auditoria au = (Auditoria) Repository.getInstance().getObject(new Auditoria().getClass(), "idServicio", new Long(auditoria));
                au.setTransaccion(false);
                au.setCosto(BigDecimal.ZERO);
                //actualiza servicio
                ServicioPension srv = (ServicioPension) Repository.getInstance().getObject(new ServicioPension().getClass(), "id", new Long(servicio));
                srv.setActiva(false);
                table();
                Repository.getInstance().commit();
                JOptionPane.showMessageDialog(null, "El servicio de pensión se canceló correctamente");
            }
        } catch (Exception e) {
            Repository.getInstance().rollback();
            JOptionPane.showMessageDialog(null, "Error al cancelar lote:\n"+e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == view.cbPensiones) setPrecio();
        if(ae.getSource() == view.cbClientesPensiones) buscarCoche();
        if(ae.getSource() == view.btnNuevaPension) nuevoServicioPesion();
        if(ae.getSource() == view.btnResetear) {
            try {
                Repository.getInstance().openSession();
                limpiarFormulario();
                table();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al recetear formulario de pensiones:\n"+e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
        if(ae.getSource() == view.btnCancelar) cancelacion();
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
