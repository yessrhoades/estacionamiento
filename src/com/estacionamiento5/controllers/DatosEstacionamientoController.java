
package com.estacionamiento5.controllers;

import com.estacionamiento5.repository.Repository;
import com.estacionamiento5.repository.ValidatorTypeInput;
import com.estacionamiento5.views.Datos;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import javax.swing.JOptionPane;
import com.estacionamiento5.pojos.DatosEstacionamiento;

/**
 *
 * @author Rhoades - pc
 */
public class DatosEstacionamientoController extends Controller {
    
    private Datos datosView;
    private DatosEstacionamiento datos;
    
    public DatosEstacionamientoController() {
        this.datosView = new Datos(null, true);
        this.datos = new DatosEstacionamiento();
    }

    @Override
    public void inicio() {        
        try {
            this.datosView.setTitle("Datos del estacionamiento");
            this.datosView.setLocationRelativeTo(null);
            this.datosView.setResizable(false);
            this.datosView.jbtnGuardarDatos.removeActionListener(this);
            Repository.getInstance().openSession();
            datos = (DatosEstacionamiento) Repository.getInstance().getObject(new DatosEstacionamiento().getClass(), "id", 1);
            llenarFormulario();
            this.datosView.jbtnGuardarDatos.addActionListener(this);
            this.datosView.show();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Error al cargar datos del estacionamiento:\n" + ex.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);
        }        
    }
    
    private void llenarFormulario()
    {
            datosView.tdDatosNombre.setText(datos.getNombre());
            datosView.tdDatosDireccion.setText(datos.getDireccion());
            datosView.tdDatosColonia.setText(datos.getColonia());
            datosView.tdDatosCp.setText(String.valueOf(datos.getCp()));
            datosView.tdDatosMunicipio.setText(datos.getMunicipio());
            datosView.tdDatosEstado.setText(datos.getEstado());
            datosView.tdDatosRegimen.setText(datos.getRegimen());
            datosView.tdDatosRfc.setText(datos.getRfc());
            datosView.tdDatosSerie.setText(datos.getTicketSerie());
            datosView.tdDatosLVapertura.setText(formatoHora.format(datos.getHorarioLunesViernesApertura()));
            datosView.tdDatosLVcierre.setText(formatoHora.format(datos.getHorarioLunesViernesCierre()));
            if(datos.getHorarioSabadoApertura() != null)
                datosView.tdDatosSapertura.setText(formatoHora.format(datos.getHorarioSabadoApertura()));
            if(datos.getHorarioSabadoCierre() != null)
                datosView.tdDatosScierre.setText(formatoHora.format(datos.getHorarioSabadoCierre()));
            if(datos.getHorarioDomingoApertura() != null)
                datosView.tdDatosDapertura.setText(formatoHora.format(datos.getHorarioDomingoApertura()));
            if(datos.getHorarioDomingoCierre() != null)
                datosView.tdDatosDcierre.setText(formatoHora.format(datos.getHorarioDomingoCierre()));
            if(datos.getMontoPerdidaTicket() != null)
                datosView.tdDatosExtravio.setText(datos.getMontoPerdidaTicket().toString());
    }

    @Override
    public void validator() throws Exception {
        String nombre = datosView.tdDatosNombre.getText().trim();
        if(nombre.isEmpty()) {
            datosView.tdDatosNombre.requestFocus();
            throw new Exception("El campo nombre es obligatorio");
        }
        datos.setNombre(nombre);
        
        String direccion = datosView.tdDatosDireccion.getText().trim();
        if(direccion.isEmpty()) {
            datosView.tdDatosDireccion.requestFocus();
            throw new Exception("El campo dirección es obligatorio");
        }
        datos.setDireccion(direccion);
        
        String colonia = datosView.tdDatosColonia.getText().trim();
        if(colonia.isEmpty()) {
            datosView.tdDatosColonia.requestFocus();
            throw new Exception("El campo colonia es obligatorio");
        }
        datos.setColonia(colonia);
        
        String cpS =  datosView.tdDatosCp.getText().trim();
        if(cpS.isEmpty()) {
            datosView.tdDatosCp.requestFocus();
            throw new Exception("El campo cp es obligatorio");
        }
        if(!ValidatorTypeInput.getInstance().isInteger(cpS)) {
            datosView.tdDatosCp.requestFocus();
            throw new Exception("El campo cp debe ser de tipo entero");
        }
        datos.setCp(Integer.parseInt(cpS));
        
        String municipio = datosView.tdDatosMunicipio.getText().trim();
        if(municipio.isEmpty()) {
            datosView.tdDatosMunicipio.requestFocus();
            throw new Exception("El campo municipio es obligatorio");
        }
        datos.setMunicipio(municipio);
        
        String estado = datosView.tdDatosEstado.getText().trim();
        if(estado.isEmpty()) {
            datosView.tdDatosEstado.requestFocus();
            throw new Exception("El campo estado es obligatorio");
        }
        datos.setEstado(estado);
        
        String regimen = datosView.tdDatosRegimen.getText().trim();
        if(regimen.isEmpty()) {
            datosView.tdDatosRegimen.requestFocus();
            throw new Exception("El campo régimen es obligatorio");
        }
        datos.setRegimen(regimen);
        
        String rfc = datosView.tdDatosRfc.getText().trim();
        if(rfc.isEmpty()) {
            datosView.tdDatosRfc.requestFocus();
            throw new Exception("El campo rfc es obligatorio");
        }
        datos.setRfc(rfc);
        
        String ticketSerie = datosView.tdDatosSerie.getText().trim();
        if(!ticketSerie.isEmpty()) datos.setTicketSerie(ticketSerie);
        else datos.setTicketSerie(null);
        
        String hLVApertura = datosView.tdDatosLVapertura.getText().trim();
        if(hLVApertura.isEmpty()) {
            datosView.tdDatosLVapertura.requestFocus();
            throw new Exception("El campo horario de apertura de lunes a viernes es obligatorio");
        }
        if(!ValidatorTypeInput.getInstance().isHour(hLVApertura)) {
            datosView.tdDatosLVapertura.requestFocus();
            throw new Exception("El campo horario de apertura de lunes a viernes debe ser en formato de hora");
        }
        datos.setHorarioLunesViernesApertura(formatoHora.parse(hLVApertura));
        
        String hLVCierre = datosView.tdDatosLVcierre.getText().trim();
        if(hLVCierre.isEmpty()) {
            datosView.tdDatosLVcierre.requestFocus();
            throw new Exception("El campo horario de cierre de lunes a viernes es obligatorio");
        }
        if(!ValidatorTypeInput.getInstance().isHour(hLVCierre)) {
            datosView.tdDatosLVcierre.requestFocus();
            throw new Exception("El campo horario de cierre de lunes a viernes debe ser en formato de hora");
        }
        datos.setHorarioLunesViernesCierre(formatoHora.parse(hLVCierre));
        
        if(!ValidatorTypeInput.getInstance().validateHoursString(hLVApertura, hLVCierre)) {
            datosView.tdDatosLVapertura.requestFocus();
            throw new Exception("El campo horario de apertura de lunes a viernes debe ser menor al de cierre");
        }
               
        //validaciones sabados
        String hSApertura = datosView.tdDatosSapertura.getText().trim(); 
        if(!hSApertura.isEmpty()) {
            if(!ValidatorTypeInput.getInstance().isHour(hSApertura)) {
                datosView.tdDatosSapertura.requestFocus();
                throw new Exception("El campo horario de apertura de sabados debe ser en formato de hora");
            }
            datos.setHorarioSabadoApertura(formatoHora.parse(hSApertura));
        } else {
            datos.setHorarioSabadoApertura(null);
        }
        
        String hSCierre = datosView.tdDatosScierre.getText().trim();
        if(!hSCierre.isEmpty()) {
            if(!ValidatorTypeInput.getInstance().isHour(hSCierre)) {
                datosView.tdDatosScierre.requestFocus();
                throw new Exception("El campo horario de cierre de sabados debe ser en formato de hora");
            }
            datos.setHorarioSabadoCierre(formatoHora.parse(hSCierre));
        } else {
            datos.setHorarioSabadoCierre(null);
        }
        
        if(!hSApertura.isEmpty() && hSCierre.isEmpty() || !hSCierre.isEmpty() && hSApertura.isEmpty()) {
            datosView.tdDatosSapertura.requestFocus();
            throw new Exception("Asigne valor a ambos campos en horario de cierre y apertura del dia sábado");
        }
        
        if(!hSApertura.isEmpty() && !hSCierre.isEmpty()) {
            if(!ValidatorTypeInput.getInstance().validateHoursString(hSApertura, hSCierre)) {
                datosView.tdDatosSapertura.requestFocus();
                throw new Exception("El campo horario de apertura de sábado debe ser menor al de cierre");
            }
        }
                
        //validaciones domingos
        String hDApertura = datosView.tdDatosDapertura.getText().trim();
        if(!hDApertura.isEmpty()) {
            if(!ValidatorTypeInput.getInstance().isHour(hDApertura)) {
                datosView.tdDatosDapertura.requestFocus();
                throw new Exception("El campo horario de apertura de domingos debe ser en formato de hora");
            }
            datos.setHorarioDomingoApertura(formatoHora.parse(hDApertura));
        } else {
            datos.setHorarioDomingoApertura(null);
        }
        
        String hDCierre = datosView.tdDatosDcierre.getText().trim();
        if(!hDCierre.isEmpty()) {
            if(!ValidatorTypeInput.getInstance().isHour(hDCierre)) {
                datosView.tdDatosDcierre.requestFocus();
                throw new Exception("El campo horario de cierre de domingos debe ser en formato de hora");
            }
            datos.setHorarioDomingoCierre(formatoHora.parse(hDCierre));
        } else {
            datos.setHorarioDomingoCierre(null);
        }
        
        if(!hDApertura.isEmpty() && hDCierre.isEmpty() || !hDCierre.isEmpty() && hDApertura.isEmpty()) {
            datosView.tdDatosDapertura.requestFocus();
            throw new Exception("Asigne valor a ambos campos en horario de cierre y apertura del dia domingo");
        }
        
        if(!hDApertura.isEmpty() && !hDCierre.isEmpty()) {
            if(!ValidatorTypeInput.getInstance().validateHoursString(hDApertura, hDCierre)) {
                datosView.tdDatosDapertura.requestFocus();
                throw new Exception("El campo horario de apertura de domingo debe ser menor al de cierre");
            }
        }
        
        String mPTicket = datosView.tdDatosExtravio.getText().trim();
        if(!mPTicket.isEmpty()) {
            if(!ValidatorTypeInput.getInstance().isNumeric(mPTicket)) {
                datosView.tdDatosExtravio.requestFocus();
                throw new Exception("El campo monto por extravio de ticket debe ser en formato numérico");
            }
            BigDecimal monto = new BigDecimal(mPTicket);
            if (monto.compareTo(BigDecimal.ZERO) <= 0) {
                datosView.tdDatosExtravio.requestFocus();
                throw new Exception("El campo monto por extravio de ticket debe ser mayor a 0");
            }
            datos.setMontoPerdidaTicket(new BigDecimal(mPTicket));
        } else {
            datos.setMontoPerdidaTicket(null);
        }
    }

    @Override
    public void table() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == datosView.jbtnGuardarDatos) {            
            try {
                Repository.getInstance().openSession();
                Repository.getInstance().openTransaction(); 
                validator();                               
                Repository.getInstance().updateObject(datos);
                Repository.getInstance().commit();
                JOptionPane.showMessageDialog(null,"Los datos del estacionamiento se actualizaron correctamente");                
            } catch (Exception e) {
                Repository.getInstance().rollback();
                JOptionPane.showMessageDialog(null,"Error al actualizar datos del estacionamiento:\n" + e.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);
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
