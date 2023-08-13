
package com.estacionamiento5.reports;

import com.estacionamiento5.pojos.Auditoria;
import com.estacionamiento5.pojos.DatosEstacionamiento;
import com.estacionamiento5.pojos.HisServLote;
import com.estacionamiento5.pojos.Lotes;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.swing.JOptionPane;

/**
 *
 * @author Rhoades - pc
 */
public class TicketsLotes {
    
    private SimpleDateFormat formato  = new SimpleDateFormat("dd 'de' MMMM 'del' yyyy hh:mm:ss a", new Locale("es","MX"));
    private SimpleDateFormat formato2 = new SimpleDateFormat("HH:mm:ss");
    private SimpleDateFormat formatoHora12 = new SimpleDateFormat("hh:mm a");
    
    public TicketsLotes(){}
    
    public void entrada(Lotes l, BigDecimal costo, DatosEstacionamiento datos) throws PrintException
    {
        String fechaFormateada = formato.format(l.getInicio());
        //JOptionPane.showMessageDialog(null, fechaFormateada+", "+l.getIdLote()+", "+ l.getServicio(),"Imprime Ticket",JOptionPane.INFORMATION_MESSAGE);
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
        string+=" ----------------------------------------\n";
        string += " Servicio: " + l.getServicio() + "\n";
        string += "     Lote: " + l.getIdLote() + "\n";
        string += "    Fecha: " + fechaFormateada + "\n";
        string +=" ----------------------------------------\n";
        if(datos.getMontoPerdidaTicket() != null) {
            string += " .-Favor de NO perder este ticket\n";
            string += " .-En caso de extravio se cobrara un\n";
            string += "   monto adicional de $"+datos.getMontoPerdidaTicket()+"\n";
            string += " ----------------------------------------\n";
        }
        string += " Costo por servicio: \n hora o fraccion $"+costo+"\n";
        string += " Horario de servicio:\n";
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
        string += "\n";
        string += "\n";
        string += "\n";
        string += "\n";
        string += "\n";
        string += "\n";
        string += "\n";
        string += "\n";
        string += "\n";
        string += "\n";
        string += "\n";
        string += "\n";

        Doc doc = new SimpleDoc(string.getBytes(), flavor, null);
        docPrintJob.print(doc, null);
    }
  
    
    public void salida(HisServLote hsl, Auditoria au, BigDecimal costo, DatosEstacionamiento datos) throws PrintException
    {
        String fechaFormateada  = formato.format(au.getFecha1());
        String fechaFormateada2 = formato.format(au.getFecha2());
        
        JOptionPane.showMessageDialog(null, 
                "FOLIO        : "+au.getIdServicio()+
                "\nSERVICIO : "+au.getTipoServicio()+
                "\nLOTE         : "+hsl.getIdLote()+
                "\nENTRADA : "+fechaFormateada+
                "\nSALIDA      : "+fechaFormateada2+
                "\nTIEMPO     : "+hsl.getTiempo()+
                "\n-----------------------------------------------------"+
                "\nTOTAL       : "+au.getCosto()+
                "\n-----------------------------------------------------");
        
            int result = JOptionPane.showConfirmDialog(null, "Desea imprimir el ticket?","Impresión de ticket", JOptionPane.YES_NO_OPTION);
            if(result == 0)
            {
                //JOptionPane.showMessageDialog(null, au.getIdServicio()+"\n"+au.getTipoServicio()+"\n"+hsl.getIdLote()+"\n"+fechaFormateada+"\n"+fechaFormateada2+"\n"+hsl.getTiempo()+"\n"+au.getCosto());
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
                string+="             Lote: "+hsl.getIdLote()+"\n";
                string+="  Fecha de inicio: "+fechaFormateada+"\n";
                string+="  Fecha de salida: "+fechaFormateada2+"\n";
                string+="           tiempo: "+hsl.getTiempo()+"\n";
                string+="            Total: "+au.getCosto()+"\n";
                string+=" ----------------------------------------\n";
                string += " Costo por servicio: \n hora o fraccion $"+costo+"\n";
                string += " Horario de servicio:\n";
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

                Doc doc = new SimpleDoc(string.getBytes(), flavor, null);
                docPrintJob.print(doc, null);
            }     
    }
    
    
    public void pensionCorta(HisServLote hsl, Auditoria au, DatosEstacionamiento datos) throws PrintException
    {
        String fechaFormateada  = formato.format(au.getFecha1());
        String fechaFormateada2 = formato.format(au.getFecha2());
                
        JOptionPane.showMessageDialog(null, 
                "FOLIO        : "+au.getIdServicio()+
                "\nSERVICIO : "+au.getTipoServicio()+
                "\nLOTE         : "+hsl.getIdLote()+
                "\nENTRADA : "+fechaFormateada+
                "\nSALIDA      : "+fechaFormateada2+
                "\n-----------------------------------------------------"+
                "\nTOTAL       : "+au.getCosto()+
                "\n-----------------------------------------------------");
        
            //JOptionPane.showMessageDialog(null, au.getIdServicio()+"\n"+au.getTipoServicio()+"\n"+hsl.getIdLote()+"\n"+fechaFormateada+"\n"+fechaFormateada2+"\n"+au.getCosto());
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
            string+="             Lote: "+hsl.getIdLote()+"\n";
            string+="  Fecha de inicio: "+fechaFormateada+"\n";
            string+="  Fecha de salida: "+fechaFormateada2+"\n";
            string+="            Total: "+au.getCosto()+"\n";
            string+=" ----------------------------------------\n";
            string += " Horario de servicio:\n";
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
