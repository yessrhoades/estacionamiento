package com.estacionamiento5.reports;

import com.estacionamiento5.pojos.Auditoria;
import com.estacionamiento5.pojos.Clientes;
import com.estacionamiento5.pojos.Coches;
import com.estacionamiento5.pojos.DatosEstacionamiento;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.print.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Rhoades
 */
public class ticketPensiones 
{
    SimpleDateFormat formato  = new SimpleDateFormat("dd 'de' MMMM 'del' yyyy", new Locale("es","MX"));
    SimpleDateFormat formato2 = new SimpleDateFormat("dd 'de' MMMM 'del' yyyy hh:mm:ss a", new Locale("es","MX"));
    private SimpleDateFormat formatoHora12 = new SimpleDateFormat("hh:mm a");
    
    public void ticket(Object obj, Auditoria au, DatosEstacionamiento datos) throws PrintException
    {
        Object[] obj2 = (Object[]) obj;
        Coches c = (Coches) obj2[0];
        Clientes cl = (Clientes) obj2[1];
        
        String fechaFormateada = formato.format(au.getFecha1());
        String fechaFormateada2 = formato.format(au.getFecha2());
        String fechaFormateada3 = formato2.format(au.getFecha());
        
        JOptionPane.showMessageDialog(null, "FOLIO: "+au.getIdServicio()+"\nSERVICIO: "+au.getTipoServicio()+"\nCOCHE: "+c.getModelo()+" - "+c.getPlacas()+"\nCLIENTE: "+cl.getNombre()+"\nENTRADA: "+fechaFormateada+"\nSALIDA: "+fechaFormateada2+"\nTOTAL: "+au.getCosto());
        PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
            DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
            DocPrintJob docPrintJob = printService.createPrintJob();
            String string="*------ "+datos.getNombre()+" ------*\n";
            string+="\n";
            string+=" "+datos.getDireccion()+"\n";
            string+=" "+datos.getColonia()+"\n";
            string+=" C.P. "+datos.getCp()+"\n";
            string+=" "+datos.getMunicipio()+", "+datos.getEstado()+"\n";
            string+=" "+datos.getRegimen()+"\n";
            string+=" RFC: "+datos.getRfc()+"\n";
            if(datos.getTicketSerie() == null) {
                string+="       No. Ticket: "+au.getIdServicio()+"\n";
            } else {
                string+="       No. Ticket: "+datos.getTicketSerie()+"-"+au.getIdServicio()+"\n";
            }
            string+=" ----------------------------------------\n";
            string+=" Tipo de servicio: "+au.getTipoServicio()+"\n";
            string+="            Coche: "+c.getModelo()+"\n";
            string+="           Placas: "+c.getPlacas()+"\n";
            string+="          Cliente: "+cl.getNombre()+"\n";
            string+="  Fecha de inicio: "+fechaFormateada+"\n";
            string+="  Fecha de salida: "+fechaFormateada2+"\n";
            string+="    Fecha de pago: "+fechaFormateada3+"\n";
            string+="            Total: "+au.getCosto()+"\n";
            string+=" ----------------------------------------\n";
            string+=" Horario de servicio:\n";
            if(datos.getHorarioLunesViernesApertura() == null) {
                string += " Lunes a viernes no abrimos\n";
            } else {
                string += " Lunes a viernes de "+formatoHora12.format(datos.getHorarioLunesViernesApertura()).toLowerCase()+" a "+formatoHora12.format(datos.getHorarioLunesViernesCierre()).toLowerCase()+"\n";
            }
            if(datos.getHorarioSabadoApertura() == null) {
                string += " Sabados no abrimos\n";
            } else {
                string += " Sabados de "+formatoHora12.format(datos.getHorarioSabadoApertura()).toLowerCase()+" a "+formatoHora12.format(datos.getHorarioSabadoCierre()).toLowerCase()+"\n";
            }
            if(datos.getHorarioDomingoApertura() == null) {
                string += " Domingo no abrimos\n";
            } else {
                string += " Domingo de "+formatoHora12.format(datos.getHorarioDomingoApertura()).toLowerCase()+" a "+formatoHora12.format(datos.getHorarioDomingoCierre()).toLowerCase()+"\n";
            }
            string+=" *----- GRACIAS POR SU PREFERENCIA -----*\n";
            string+="\n";
            string+="\n";
            string+="\n";
            string+="\n";
            string+="\n";
            string+="\n";
            string+="\n";
            string+="\n";
            string+="\n";
            string+="\n";
            string+="\n";
            string+="\n";
            
            Doc doc = new SimpleDoc(string.getBytes(), flavor, null);

            docPrintJob.print(doc, null);
    }
}
