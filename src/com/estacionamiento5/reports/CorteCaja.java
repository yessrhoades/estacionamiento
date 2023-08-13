
package com.estacionamiento5.reports;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import com.estacionamiento5.pojos.Auditoria;
import com.estacionamiento5.pojos.Banios;
import com.estacionamiento5.pojos.Clientes;
import com.estacionamiento5.pojos.Coches;
import com.estacionamiento5.pojos.HisServBanios;
import com.estacionamiento5.pojos.HisServLote;
import com.estacionamiento5.pojos.Pensiones;
import com.estacionamiento5.pojos.ServiciosLote;

/**
 *
 * @author Rhoades - pc
 */
public class CorteCaja {
    
    SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
    
    public void reporte(String fecha, ArrayList<Object> banios, ArrayList<Object> lotes, ArrayList<Object> pensiones) throws FileNotFoundException, IOException, Exception {
        
        if(lotes == null && pensiones == null && banios == null) throw new Exception("No puede crear un archivo sin datos");
        
        HSSFWorkbook book = new HSSFWorkbook();
        //estilo de encabezado
        CellStyle headerStyle = book.createCellStyle();        
        HSSFFont font = book.createFont();
        font.setBold(true);
        headerStyle.setFont(font);
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        //estilo de total
        CellStyle totalStyle = book.createCellStyle();
        totalStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        totalStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        totalStyle.setBorderBottom(BorderStyle.THIN);
        totalStyle.setBorderLeft(BorderStyle.THIN);
        totalStyle.setBorderRight(BorderStyle.THIN);
        //estilo de moneda
        CellStyle styleCurrencyFormat = book.createCellStyle();
        styleCurrencyFormat.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        styleCurrencyFormat.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleCurrencyFormat.setBorderBottom(BorderStyle.THIN);
        styleCurrencyFormat.setBorderLeft(BorderStyle.THIN);
        styleCurrencyFormat.setBorderRight(BorderStyle.THIN);
        HSSFDataFormat df = book.createDataFormat();
        styleCurrencyFormat.setDataFormat(df.getFormat("$#,##0.00"));
        //estilo de datos
        CellStyle datosStyle = book.createCellStyle();
        datosStyle.setBorderBottom(BorderStyle.THIN);
        datosStyle.setBorderLeft(BorderStyle.THIN);
        datosStyle.setBorderRight(BorderStyle.THIN);
        //estilo de moneda cancelado
        CellStyle styleCurrencyFormat2 = book.createCellStyle();
        styleCurrencyFormat2.setFillForegroundColor(IndexedColors.RED.getIndex());
        styleCurrencyFormat2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleCurrencyFormat2.setBorderBottom(BorderStyle.THIN);
        styleCurrencyFormat2.setBorderLeft(BorderStyle.THIN);
        styleCurrencyFormat2.setBorderRight(BorderStyle.THIN);
        HSSFDataFormat df2 = book.createDataFormat();
        styleCurrencyFormat2.setDataFormat(df2.getFormat("$#,##0.00"));
        //estilo de datos cancelados
        CellStyle datosStyleCancel = book.createCellStyle();
        datosStyleCancel.setBorderBottom(BorderStyle.THIN);
        datosStyleCancel.setBorderLeft(BorderStyle.THIN);
        datosStyleCancel.setBorderRight(BorderStyle.THIN);
        datosStyleCancel.setFillForegroundColor(IndexedColors.RED.getIndex());
        datosStyleCancel.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        int countSheet = 0;
        
        if(lotes != null) {
            HSSFSheet hoja_lotes = book.createSheet("Lotes");
        
            String[] headers = new String[]{
                "FOLIO",
                "LOTE",
                "SERVICIO",
                "ENTRADA",
                "SALIDA",
                "FECHA PAGO",
                "TIEMPO",
                "TOTAL"
            };            
            //crea encabezado
            HSSFRow headerRow = hoja_lotes.createRow(0);
            for (int i = 0; i < headers.length; ++i) {
                String header = headers[i];
                HSSFCell cell = headerRow.createCell(i);
                cell.setCellStyle(headerStyle);
                cell.setCellValue(header);
            }
            //crea body
            int i = 1;
            float total = 0;
            for (Object objs : lotes) {
                HSSFRow dataRow = hoja_lotes.createRow(i);
                Object[] obj = (Object[]) objs;
                HisServLote hsl = (HisServLote) obj[0];
                ServiciosLote sl = (ServiciosLote) obj[1];
                Auditoria au = (Auditoria) obj[2];
                //si el servicio esta cancelado
                if(!au.isTransaccion()) {
                    HSSFCell celldata = dataRow.createCell(0);
                    celldata.setCellValue(hsl.getIdServicio());
                    celldata.setCellStyle(datosStyleCancel);

                    celldata = dataRow.createCell(1);
                    celldata.setCellValue(hsl.getIdLote());
                    celldata.setCellStyle(datosStyleCancel);

                    celldata = dataRow.createCell(2);
                    celldata.setCellValue(sl.getNombre());
                    celldata.setCellStyle(datosStyleCancel);

                    celldata = dataRow.createCell(3);
                    celldata.setCellValue(au.getFecha1().toLocaleString());
                    celldata.setCellStyle(datosStyleCancel);

                    celldata = dataRow.createCell(4);
                    celldata.setCellValue(au.getFecha2().toLocaleString());
                    celldata.setCellStyle(datosStyleCancel);

                    celldata = dataRow.createCell(5);
                    celldata.setCellValue(au.getFecha().toLocaleString());
                    celldata.setCellStyle(datosStyleCancel);

                    celldata = dataRow.createCell(6);
                    celldata.setCellValue(hsl.getTiempo());
                    celldata.setCellStyle(datosStyleCancel);

                    HSSFCell celltt = dataRow.createCell(7);
                    celltt.setCellValue(au.getCosto().doubleValue());
                    celltt.setCellStyle(styleCurrencyFormat2);
                } else {
                    HSSFCell celldata = dataRow.createCell(0);
                    celldata.setCellValue(hsl.getIdServicio());
                    celldata.setCellStyle(datosStyle);

                    celldata = dataRow.createCell(1);
                    celldata.setCellValue(hsl.getIdLote());
                    celldata.setCellStyle(datosStyle);

                    celldata = dataRow.createCell(2);
                    celldata.setCellValue(sl.getNombre());
                    celldata.setCellStyle(datosStyle);

                    celldata = dataRow.createCell(3);
                    celldata.setCellValue(au.getFecha1().toLocaleString());
                    celldata.setCellStyle(datosStyle);

                    celldata = dataRow.createCell(4);
                    celldata.setCellValue(au.getFecha2().toLocaleString());
                    celldata.setCellStyle(datosStyle);

                    celldata = dataRow.createCell(5);
                    celldata.setCellValue(au.getFecha().toLocaleString());
                    celldata.setCellStyle(datosStyle);

                    celldata = dataRow.createCell(6);
                    celldata.setCellValue(hsl.getTiempo());
                    celldata.setCellStyle(datosStyle);

                    HSSFCell celltt = dataRow.createCell(7);
                    celltt.setCellValue(au.getCosto().doubleValue());
                    celltt.setCellStyle(styleCurrencyFormat);
                }
                i++;
            }
            //fila de total
            HSSFRow dataRow = hoja_lotes.createRow(i);
            HSSFCell cell = dataRow.createCell(6);
            cell.setCellStyle(totalStyle);
            cell.setCellValue("TOTAL:");
            
            HSSFCell cell2 = dataRow.createCell(7);
            cell2.setCellType(CellType.FORMULA);            
            cell2.setCellFormula(String.format("SUM(H2:H%d)",1 + lotes.size()));
            cell2.setCellStyle(styleCurrencyFormat);
            
            HSSFRow row = book.getSheetAt(countSheet).getRow(0);
            for(int colNum = 0; colNum < row.getLastCellNum(); colNum++) {
                book.getSheetAt(countSheet).autoSizeColumn(colNum);
            }
            countSheet++;
        }
        
        
        if(pensiones != null) {
            HSSFSheet hoja_pensiones = book.createSheet("Pensiones");
        
            String[] headers = new String[]{
                "FOLIO",
                "SERVICIO",
                "CLIENTE",
                "COCHE",
                "ENTRADA",
                "SALIDA",
                "FECHA PAGO",
                "TOTAL"                
            };            
            //crea encabezado
            HSSFRow headerRow = hoja_pensiones.createRow(0);
            for (int i = 0; i < headers.length; ++i) {
                String header = headers[i];
                HSSFCell cell = headerRow.createCell(i);
                cell.setCellStyle(headerStyle);
                cell.setCellValue(header);
            }
            //crea body
            int i = 1;
            float total = 0;
            for (Object objs : pensiones) {
                HSSFRow dataRow = hoja_pensiones.createRow(i);
                Object[] obj = (Object[]) objs;
                Coches c = (Coches) obj[1];
                Clientes cl = (Clientes) obj[2];
                Pensiones p = (Pensiones) obj[3];
                Auditoria au = (Auditoria) obj[4];
                //si el servicio esta cancelado
                if(!au.isTransaccion()) {
                    HSSFCell celldata = dataRow.createCell(0);
                    celldata.setCellValue(au.getIdServicio());
                    celldata.setCellStyle(datosStyleCancel);

                    celldata = dataRow.createCell(1);
                    celldata.setCellValue(p.getNombre());
                    celldata.setCellStyle(datosStyleCancel);

                    celldata = dataRow.createCell(2);
                    celldata.setCellValue(cl.getNombre()+" - "+cl.getAlias());
                    celldata.setCellStyle(datosStyleCancel);

                    celldata = dataRow.createCell(3);
                    celldata.setCellValue(c.getModelo()+" - "+c.getPlacas());
                    celldata.setCellStyle(datosStyleCancel);

                    celldata = dataRow.createCell(4);
                    celldata.setCellValue(formatoFecha.format(au.getFecha1()));
                    celldata.setCellStyle(datosStyleCancel);

                    celldata = dataRow.createCell(5);
                    celldata.setCellValue(formatoFecha.format(au.getFecha2()));
                    celldata.setCellStyle(datosStyleCancel);

                    celldata = dataRow.createCell(6);
                    celldata.setCellValue(au.getFecha().toLocaleString());
                    celldata.setCellStyle(datosStyleCancel);

                    HSSFCell celltt = dataRow.createCell(7);
                    celltt.setCellValue(au.getCosto().doubleValue());
                    celltt.setCellStyle(styleCurrencyFormat2);
                } else {                
                    HSSFCell celldata = dataRow.createCell(0);
                    celldata.setCellValue(au.getIdServicio());
                    celldata.setCellStyle(datosStyle);

                    celldata = dataRow.createCell(1);
                    celldata.setCellValue(p.getNombre());
                    celldata.setCellStyle(datosStyle);

                    celldata = dataRow.createCell(2);
                    celldata.setCellValue(cl.getNombre()+" - "+cl.getAlias());
                    celldata.setCellStyle(datosStyle);

                    celldata = dataRow.createCell(3);
                    celldata.setCellValue(c.getModelo()+" - "+c.getPlacas());
                    celldata.setCellStyle(datosStyle);

                    celldata = dataRow.createCell(4);
                    celldata.setCellValue(formatoFecha.format(au.getFecha1()));
                    celldata.setCellStyle(datosStyle);

                    celldata = dataRow.createCell(5);
                    celldata.setCellValue(formatoFecha.format(au.getFecha2()));
                    celldata.setCellStyle(datosStyle);

                    celldata = dataRow.createCell(6);
                    celldata.setCellValue(au.getFecha().toLocaleString());
                    celldata.setCellStyle(datosStyle);

                    HSSFCell celltt = dataRow.createCell(7);
                    celltt.setCellValue(au.getCosto().doubleValue());
                    celltt.setCellStyle(styleCurrencyFormat);
                }
                i++;
            }
            //fila de total
            HSSFRow dataRow = hoja_pensiones.createRow(i);
            HSSFCell cell = dataRow.createCell(6);
            cell.setCellStyle(totalStyle);
            cell.setCellValue("TOTAL:");
            
            HSSFCell cell2 = dataRow.createCell(7);
            cell2.setCellType(CellType.FORMULA);
            cell2.setCellFormula(String.format("SUM(H2:H%d)",1 + pensiones.size()));
            cell2.setCellStyle(styleCurrencyFormat);
            
            HSSFRow row = book.getSheetAt(countSheet).getRow(0);
            for(int colNum = 0; colNum < row.getLastCellNum(); colNum++) {
                book.getSheetAt(countSheet).autoSizeColumn(colNum);
            }
            countSheet++;
        }
        
        
        if(banios != null) {
            HSSFSheet hoja_baños = book.createSheet("Baños");
        
            String[] headers = new String[]{
                "FOLIO",
                "SERVICIO",
                "FECHA",
                "TOTAL"                
            };            
            //crea encabezado
            HSSFRow headerRow = hoja_baños.createRow(0);
            for (int i = 0; i < headers.length; ++i) {
                String header = headers[i];
                HSSFCell cell = headerRow.createCell(i);
                cell.setCellStyle(headerStyle);
                cell.setCellValue(header);
            }
            //crea body
            int i = 1;
            float total = 0;
            for (Object objs : banios) {
                HSSFRow dataRow = hoja_baños.createRow(i);
                Object[] obj = (Object[]) objs;
                HisServBanios his = (HisServBanios) obj[0];
                Banios ba = (Banios) obj[1];
                
                HSSFCell celldata = dataRow.createCell(0);
                celldata.setCellValue(his.getId());
                celldata.setCellStyle(datosStyle);
                
                celldata = dataRow.createCell(1);
                celldata.setCellValue(ba.getNombre());
                celldata.setCellStyle(datosStyle);
                
                celldata = dataRow.createCell(2);
                celldata.setCellValue(his.getFecha().toLocaleString());
                celldata.setCellStyle(datosStyle);
                
                HSSFCell celltt = dataRow.createCell(3);
                celltt.setCellValue(his.getCosto().doubleValue());
                celltt.setCellStyle(styleCurrencyFormat);
                i++;
            }
            //fila de total
            HSSFRow dataRow = hoja_baños.createRow(i);
            HSSFCell cell = dataRow.createCell(2);
            cell.setCellStyle(totalStyle);
            cell.setCellValue("TOTAL:");
            
            HSSFCell cell2 = dataRow.createCell(3);
            cell2.setCellType(CellType.FORMULA);
            cell2.setCellFormula(String.format("SUM(D2:D%d)",1 + banios.size()));
            cell2.setCellStyle(styleCurrencyFormat);
            
            HSSFRow row = book.getSheetAt(countSheet).getRow(0);
            for(int colNum = 0; colNum < row.getLastCellNum(); colNum++) {
                book.getSheetAt(countSheet).autoSizeColumn(colNum);
            }
            countSheet++;
        }
        
        File path = new File("c:/reportes_estacionamiento");
        //crea carpeta si no existe
        if(!path.exists()) {
            if(path.mkdirs()) JOptionPane.showMessageDialog(null, "El directorio se creó correctamente");
            else JOptionPane.showMessageDialog(null, "Error al crear directorio","Error!",JOptionPane.ERROR_MESSAGE);
        }
        String pathFinal = path+"/corte_"+fecha+".xls";
        //crea archivo
        FileOutputStream file = new FileOutputStream(pathFinal);
        book.write(file);
        file.close();
        //abre con aplicacion predeterminada
        File path_open = new File(pathFinal);
        Desktop.getDesktop().open(path_open);
    }
    
}
