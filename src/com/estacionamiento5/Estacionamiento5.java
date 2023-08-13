package com.estacionamiento5;

import com.estacionamiento5.controllers.PanelPrincipalController;
import javax.swing.*;

/**
 *
 * @author Rhoades
 */
public class Estacionamiento5 
{
    public static void main(String[] args) 
    {
        //----------------- cargar look and feel------------------------
        try { 
            //UIManager.setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch(ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) { 
            JOptionPane.showMessageDialog(null,"Error al cargar lookAndFeel:\n" + ex.getMessage(), "Error!",JOptionPane.ERROR_MESSAGE);
        }
        
        PanelPrincipalController principalController = new PanelPrincipalController();
        
    }
    
}
