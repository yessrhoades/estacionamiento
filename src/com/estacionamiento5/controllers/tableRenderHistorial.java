package com.estacionamiento5.controllers;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Rhoades
 */
public class tableRenderHistorial extends DefaultTableCellRenderer{
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) 
    {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        column = 0;
        if(String.valueOf(table.getValueAt(row,column)).equals("false")) {
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
