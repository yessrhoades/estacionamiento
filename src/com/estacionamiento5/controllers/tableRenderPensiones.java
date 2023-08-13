/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.estacionamiento5.controllers;

import java.awt.Color;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author usuario
 */
public class tableRenderPensiones extends DefaultTableCellRenderer{
    
    Calendar cal;
    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
    Date fechaDate;
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) 
    {
        super.getTableCellRendererComponent(table, value, true, hasFocus, row, column);
        fechaDate = new Date();
        //calcula 2 dias antes
        cal = Calendar.getInstance();
        cal.setTime(fechaDate);
        cal.add(Calendar.DATE, 2);
        Date fecha2 = cal.getTime();
        //calcula 1 dia antes
        cal = Calendar.getInstance();
        cal.setTime(fechaDate);
        cal.add(Calendar.DATE, 1);
        Date fecha1 = cal.getTime();
        //calcula vencimiento
        cal = Calendar.getInstance();
        cal.setTime(fechaDate);
        Date fecha = cal.getTime();
        
        String fecha_ = formato.format(fecha);
        String fecha1_ = formato.format(fecha1);
        String fecha2_ = formato.format(fecha2);        
        String columna = String.valueOf(table.getValueAt(row,6));
        
        if(columna.equals(fecha1_) || columna.equals(fecha2_)) {
            setBackground(new Color(255,204,51));
            setForeground(Color.white);
        } else if(columna.equals(fecha_)) {
            setBackground(new Color(204,0,51));
            setForeground(Color.white);
        } else {
            setBackground(null);
            setForeground(null);
        }
        //si la fila esta seleccionada
        if(isSelected) {
            setBackground(new Color(0,153,153));
            setForeground(Color.white);
        }
        return this;
    }
}
