package com.estacionamiento5.controllers;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rhoades - pc
 */
public abstract class Controller extends MouseAdapter implements ActionListener, KeyListener {
    
    SimpleDateFormat formatoTimeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
    SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
    
    DefaultTableModel tab;
    
    public abstract void inicio();
    
    public abstract void table();
    
    public abstract void validator() throws Exception;
    
    public Date fechaActual()
    {
        Date fecha = new Date();
        return fecha;
    }
    
    public void cleanTable(JTable table)
    {
        DefaultTableModel tab1e = (DefaultTableModel) table.getModel();
        tab1e.setRowCount(0);
    }
    
}
