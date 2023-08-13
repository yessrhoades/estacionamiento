
package com.estacionamiento5.controllers;

import com.estacionamiento5.pojos.Auditoria;
import com.estacionamiento5.pojos.DatosEstacionamiento;
import com.estacionamiento5.pojos.HisServLote;
import com.estacionamiento5.pojos.Lotes;
import com.estacionamiento5.pojos.ServiciosLote;
import com.estacionamiento5.reports.TicketsLotes;
import com.estacionamiento5.views.panelprincipal;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import com.estacionamiento5.repository.Repository;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rhoades - pc
 */
public class LotesController extends Controller{
    
    private panelprincipal principalView;
    private Lotes lotesModel;
    private ServiciosLote ServiciosLoteModel;
    private TicketsLotes tickets;
    private DatosEstacionamiento estacionamiento;
    
    public LotesController(panelprincipal principalView){
        this.principalView = principalView;
        lotesModel = new Lotes();
        ServiciosLoteModel = new ServiciosLote();
        tickets = new TicketsLotes();
    }

    @Override
    public void inicio() {
        principalView.btnIniciarLote.addActionListener(this);
        principalView.btnFinalizarLote.addActionListener(this);
        principalView.btnActualizarLote.addActionListener(this);
        principalView.tfEntrada.addKeyListener(this);
        principalView.tfSalidaLote.addKeyListener(this);
        table();
        TablaOcupados();
        ComboServicios();
    }

    @Override
    public void table() {
        ArrayList<Lotes> arr = Repository.getInstance().getObjectAsc(lotesModel.getClass(),"idLote");
        cleanTable(principalView.tbLotes);
        tab = (DefaultTableModel) principalView.tbLotes.getModel();        
        for (Lotes lt : arr) {
            tab.addRow(new Object[] { lt.getIdLote(), lt.getEstado() });
        }
    }
    
    private void TablaOcupados()
    {
        ArrayList<Object> array = Repository.getInstance().getLotesRepository().getLotesOcupados();
        cleanTable(principalView.tbLotesOcupados);
        tab = (DefaultTableModel) principalView.tbLotesOcupados.getModel();
        int x = 1;
        for (Object objs : array) {
            Object[] obj = (Object[]) objs;
            Lotes l = (Lotes) obj[0];
            ServiciosLote sl = (ServiciosLote) obj[1];
            tab.addRow(new Object[] { x, l.getIdLote(), sl.getNombre(), l.getInicio().toLocaleString() });
            x++;
        }
    }
  
    private void poblarTablas()
    {
        try {
            Repository.getInstance().openSession();
            Repository.getInstance().openTransaction();
            table();
            TablaOcupados();
            Repository.getInstance().commit();
        } catch (Exception ex) {
            Repository.getInstance().rollback();
            JOptionPane.showMessageDialog(null, "Error al poblar tablas:\n"+ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void ComboServicios()
    {
        ArrayList<ServiciosLote> arr = Repository.getInstance().getObjectAsc(ServiciosLoteModel.getClass(),"nombre");
        JComboBox combo = (JComboBox) principalView.jcbservicio;
        combo.removeAllItems();
        for (ServiciosLote sl : arr) {
            combo.addItem(new ServiciosLote(sl.getId(), sl.getNombre(), sl.getCosto()));
        }
    }

    @Override
    public void validator() throws Exception {        
        String numLote = principalView.tfEntrada.getText().trim();
        //valida si viene vacio
        if(numLote.isEmpty()) {
            principalView.tfEntrada.requestFocus();
            throw new Exception("Ingrese un numero de lote en el campo de inicio");
        }
        Long lt = new Long(numLote);
        lotesModel = (Lotes) Repository.getInstance().getObject(lotesModel.getClass(), "idLote", lt);
        //valida si el lote existe
        if(lotesModel == null) {
            principalView.tfEntrada.setText("");
            principalView.tfEntrada.requestFocus();
            throw new Exception("El lote especificado en el campo de inicio no existe, revise con atención");
        }
        //valida si el lote esta disponible
        if(lotesModel.getEstado().equals("OCUPADO")) {
            principalView.tfEntrada.setText("");
            principalView.tfEntrada.requestFocus();
            throw new Exception("El lote especificado en el campo de inicio se encuentra ocupado, revise con atención");
        }        
    }
    
    private void iniciarServicio() {
        try {
            Repository.getInstance().openSession();
            Repository.getInstance().openTransaction();
            //valida campos y existencias
            validator();
            String servicio = principalView.jcbservicio.getItemAt(principalView.jcbservicio.getSelectedIndex()).getNombre();
            String idServ = principalView.jcbservicio.getItemAt(principalView.jcbservicio.getSelectedIndex()).getId();
            BigDecimal costo = principalView.jcbservicio.getItemAt(principalView.jcbservicio.getSelectedIndex()).getCosto();
            //ocupa lote
            System.out.println(costo);
            lotesModel.setEstado("OCUPADO");
            lotesModel.setServicio(idServ);
            lotesModel.setInicio(fechaActual());
            //selecciona datos del estacionamiento
            estacionamiento = (DatosEstacionamiento) Repository.getInstance().getObject(new DatosEstacionamiento().getClass(), "id", 1);
            if("NORMAL".equals(servicio)) {
                lotesModel = Repository.getInstance().getLotesRepository().getLoteOcupado(lotesModel.getIdLote());
                tickets.entrada(lotesModel, costo, estacionamiento);
            } else {
                
                Date fechaFin = null;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(fechaActual());
            
                switch(servicio) 
                {
                    case "PENSION POR 1 DIA" : case "PENSION 1 DIA CAMION" :
                        calendar.add(Calendar.DATE, 1);
                        fechaFin = calendar.getTime();
                    break;

                    case "PENSION POR 8 HORAS" :
                        calendar.add(Calendar.HOUR, 8);
                        fechaFin = calendar.getTime();
                    break;

                    case "PENSION POR 1 NOCHE" : case "PENSION NOCHE CAMION" :
                        calendar.add(Calendar.DATE, 1);
                        Date fechaA = calendar.getTime();
                        String fechaB = formatoFecha.format(fechaA);
                        String fechaC = fechaB + " " + "09:30:00";
                        fechaFin = formatoTimeStamp.parse(fechaC);
                    break;
                }
                //inserta en auditoria
                Auditoria auditoria = new Auditoria(servicio, fechaActual(), fechaFin, costo, fechaActual(), true);
                Repository.getInstance().addObject(auditoria);
                //inserta en historial
                HisServLote historial = new HisServLote();
                historial.setIdServicio(auditoria.getIdServicio());
                historial.setIdLote(lotesModel.getIdLote());
                historial.setIdServicioL(idServ);
                historial.setTiempo("N/A");
                Repository.getInstance().addObject(historial);                
                tickets.pensionCorta(historial, auditoria, estacionamiento);
            }
            
            principalView.jcbservicio.setSelectedIndex(0);
            principalView.tfEntrada.setText("");
            
            Repository.getInstance().commit();
            
            poblarTablas();
        } catch (Exception ex) {
            Repository.getInstance().rollback();
            JOptionPane.showMessageDialog(null, "Error al iniciar servicio:\n"+ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
        } finally {            
            principalView.tfEntrada.requestFocus();
        }
    }

    public void validatorFin() throws Exception {
        String numLote = principalView.tfSalidaLote.getText().trim();
        //valida si viene vacio
        if(numLote.isEmpty()) {
            principalView.tfSalidaLote.requestFocus();
            throw new Exception("Ingrese un numero de lote en el campo de salida");
        }
        Long lt = new Long(numLote);        
        lotesModel = (Lotes) Repository.getInstance().getObject(lotesModel.getClass(), "idLote", lt);
        //valida si el lote existe
        if(lotesModel == null) {
            principalView.tfSalidaLote.setText("");
            principalView.tfSalidaLote.requestFocus();
            throw new Exception("El lote especificado en el campo de salida no existe, revise con atención");
        }
        //valida si el lote esta en servicio
        if(lotesModel.getEstado().equals("DISPONIBLE")) {
            principalView.tfSalidaLote.setText("");
            principalView.tfSalidaLote.requestFocus();
            throw new Exception("El lote especificado el el campo de salida se encuentra sin servicio, revise con atención");
        }
    }
    
    private void terminarServicio() {
        try {
            Repository.getInstance().openSession();
            Repository.getInstance().openTransaction();
            //valida campos y existencias
            validatorFin();            
            //selecciona servicio de lote
            ServiciosLoteModel = (ServiciosLote) Repository.getInstance().getObject(ServiciosLoteModel.getClass(), "id", lotesModel.getServicio());            
            String servicio = ServiciosLoteModel.getNombre();
            Long lote = lotesModel.getIdLote();
            if(servicio.equals("NORMAL")) {
                BigDecimal total;
                //fecha de inicio
                Date inicio = lotesModel.getInicio();
                //costo de servicio normal
                BigDecimal costo = ServiciosLoteModel.getCosto();
                //calculo de horas y minutos
                Date fechaFin = fechaActual();
                //restando fecha incial a fecha actual convertidas en milisegundos
                long diferencia = fechaFin.getTime() - inicio.getTime();
                //calcula las horas totales
                long hh = diferencia / (60 * 60 * 1000);
                //calcula los minutos totales
                long mm = diferencia / (60 * 1000) % 60;
                //calcula los segundos totales
                long ss = diferencia / 1000 % 60;
                //---------calculo de costo----------
                //valida si el tiempo es nemos de una hora
                if (hh < 1) total = costo;
                else {
                    //valida si es hora completa
                    if (mm == 0) total = costo.multiply(new BigDecimal(hh));
                    //valida si esta entre los 15 minutos de tolerancia para media hora
                    else if (mm >= 1 && mm <= 15) {
                        BigDecimal hora = costo.multiply(new BigDecimal(hh));
                        BigDecimal media = costo.multiply(new BigDecimal(.5));
                        total = hora.add(media).setScale(0, RoundingMode.FLOOR);
                    }
                    //suma el resto para completar la otra hora
                    else total = costo.multiply(new BigDecimal(hh + 1));
                }
                
                total = total.setScale(2, RoundingMode.HALF_UP);//redondea a 2 decimales
                
                String horas = String.valueOf(hh);
                String minutos = String.valueOf(mm);
                String segundos = String.valueOf(ss);
                
                if(hh < 10) horas = "0"+hh;
                if(mm < 10) minutos = "0"+mm;
                if(ss < 10) segundos = "0"+ss;
                
                String tiempoTotal = horas + ":" + minutos + ":" + segundos;
                
                //inserta en auditoria
                Auditoria auditoria = new Auditoria(servicio, inicio, fechaFin, total, fechaFin, true);
                Repository.getInstance().addObject(auditoria);
                
                //inserta en historial
                HisServLote historial = new HisServLote(auditoria.getIdServicio(), lote, ServiciosLoteModel.getId(), tiempoTotal);
                Repository.getInstance().addObject(historial);
                
                //selecciona datos del estacionamiento para el ticket
                estacionamiento = (DatosEstacionamiento) Repository.getInstance().getObject(new DatosEstacionamiento().getClass(), "id", 1);
                tickets.salida(historial, auditoria, costo, estacionamiento);
            }
            //desocupa lote
            lotesModel.setEstado("DISPONIBLE");
            lotesModel.setServicio(null);
            lotesModel.setInicio(null);
            principalView.tfSalidaLote.setText("");
            Repository.getInstance().commit();
            poblarTablas();
            JOptionPane.showMessageDialog(null, "Se ha finalizado servicio " + servicio + " en el lote " + lote);
        } catch (Exception ex) {
            Repository.getInstance().rollback();
            JOptionPane.showMessageDialog(null, "Error al terminar servicio:\n"+ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
        } finally {
            principalView.tfSalidaLote.requestFocus();
        }
    }
    
    private void cambiarLote()
    {
        try {
            Repository.getInstance().openSession();
            Repository.getInstance().openTransaction();
            validator();            
            Lotes ltDisponible = lotesModel;
            validatorFin();            
            Lotes ltOcupado = lotesModel;
            //actualiza lote disponible a ocupado
            ltDisponible.setEstado("OCUPADO");
            ltDisponible.setServicio(ltOcupado.getServicio());
            ltDisponible.setInicio(ltOcupado.getInicio());            
            //actualiza lote ocupado a disponible
            ltOcupado.setEstado("DISPONIBLE");
            ltOcupado.setServicio(null);
            ltOcupado.setInicio(null);   
            
            principalView.tfEntrada.setText("");
            principalView.tfSalidaLote.setText("");
            principalView.tfEntrada.requestFocus();
            
            Repository.getInstance().commit();
            poblarTablas();
            JOptionPane.showMessageDialog(null, "El lote se cambio correctamente");
        } catch (Exception ex) {
            Repository.getInstance().rollback();
            JOptionPane.showMessageDialog(null, "Error al cambiar lote:\n"+ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void cancelacion() {
        try {
            Repository.getInstance().openSession();
            Repository.getInstance().openTransaction();
            validatorFin();
            //crea objectos para confirm dialog
            JLabel jPassword = new JLabel("Contraseña");
            JTextField password = new JPasswordField();
            Object[] inputObj = {jPassword, password};
            int result = JOptionPane.showConfirmDialog(null, inputObj, "Cancelación de lotes", JOptionPane.OK_CANCEL_OPTION);            
            if(result == JOptionPane.OK_OPTION) {
                String passwd = password.getText().trim();
                if(passwd.isEmpty()) throw new Exception("Debe escribir una contraseña");
                boolean login = Repository.getInstance().getUsuariosRepository().permisoAdministrador(passwd);
                if(!login) throw new Exception("Contraseña incorrecta");
                String servicio = lotesModel.getServicio();
                //si es pensión corta...
                if(!servicio.equals("1lotnorm")) {
                    ArrayList<Object> objects = Repository.getInstance().getHisLotesRepository().getHisServLoteParaCancelacion(servicio, lotesModel.getIdLote(), lotesModel.getInicio());
                    for(Object objs : objects) {
                        Object[] Obj = (Object[]) objs;
                        Auditoria au = (Auditoria) Obj[2];
                        if(au == null) throw new Exception("No se encontro la auditoria, reportelo al administrador");
                        au.setTransaccion(false);
                        au.setCosto(BigDecimal.ZERO);
                    }
                }
                //desocupa lote
                lotesModel.setEstado("DISPONIBLE");
                lotesModel.setServicio(null);
                lotesModel.setInicio(null);
                Repository.getInstance().commit();
                poblarTablas();
                principalView.tfEntrada.setText("");
                principalView.tfSalidaLote.setText("");
                principalView.tfEntrada.requestFocus();
                JOptionPane.showMessageDialog(null, "El servicio de lote se canceló correctamente");
            }
        } catch (Exception e) {
            Repository.getInstance().rollback();
            JOptionPane.showMessageDialog(null, "Error al cancelar lote:\n"+e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        
        if(ae.getSource() == principalView.btnIniciarLote) {
            iniciarServicio();
        }
        
        if(ae.getSource() == principalView.btnFinalizarLote) {
            terminarServicio();
        }
        
        if (ae.getSource() == principalView.btnActualizarLote) {
            cambiarLote();
        }
        
    }
    
    @Override
    public void keyPressed(KeyEvent e) 
    {
        if(e.getSource() == principalView.tfEntrada) {
            if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                iniciarServicio();
            }
        }
        
        if(e.getSource() == principalView.tfSalidaLote) {
            if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                terminarServicio();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) 
    {
        if(e.getKeyCode() == KeyEvent.VK_F1) {
            principalView.tfEntrada.requestFocus();
        }
        if(e.getKeyCode() == KeyEvent.VK_F2) {
            principalView.tfSalidaLote.requestFocus();
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e)
    {
        if(e.getSource() == principalView.tfEntrada) {
            // aceptamos solo numeros
            char c = e.getKeyChar();
            if(c < '0' || c > '9') e.consume();
            // aceptamos solo dos digitos
            if(principalView.tfEntrada.getText().length() == 2) e.consume();
        }
        
        if(e.getSource() == principalView.tfSalidaLote) {
            // aceptamos solo numeros
            char c = e.getKeyChar();
            if(c < '0' || c > '9') e.consume();
            // aceptamos solo dos digitos
            if(principalView.tfSalidaLote.getText().length() == 2) e.consume();
        }
    }
    
}
