
package com.estacionamiento5.views;

import com.estacionamiento5.pojos.Clientes;
import com.estacionamiento5.pojos.Coches;
import com.estacionamiento5.pojos.Pensiones;
import com.estacionamiento5.pojos.ServiciosLote;
import javax.swing.*;


/**
 *
 * @author Rhoades
 */
public class panelprincipal extends javax.swing.JFrame {

    
    public panelprincipal()
    {                   
      
        initComponents();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator9 = new javax.swing.JSeparator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        pnFondo = new javax.swing.JPanel();
        lbTitulo = new javax.swing.JLabel();
        jpanel_barra = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbLotes = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbLotesOcupados = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        tfEntrada = new javax.swing.JTextField();
        jcbservicio = new javax.swing.JComboBox<>();
        btnIniciarLote = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btnFinalizarLote = new javax.swing.JButton();
        tfSalidaLote = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        btnActualizarLote = new javax.swing.JButton();
        btnCancelarLote = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        btnSanitarios = new javax.swing.JButton();
        btnPensiones = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnHistorial1 = new javax.swing.JMenu();
        mnDatos = new javax.swing.JMenuItem();
        mnHistorial = new javax.swing.JMenu();
        mnHistoriaAuditoria = new javax.swing.JMenuItem();
        mnReporte = new javax.swing.JMenuItem();
        mnLotes = new javax.swing.JMenu();
        mnHistorialLotes = new javax.swing.JMenuItem();
        mnPrecios = new javax.swing.JMenuItem();
        mnPensiones = new javax.swing.JMenu();
        mnHisPensiones = new javax.swing.JMenuItem();
        mnClientes = new javax.swing.JMenuItem();
        mnCoches = new javax.swing.JMenuItem();
        mnPreciosPensiones = new javax.swing.JMenuItem();
        mnBanios = new javax.swing.JMenu();
        mnHistorialBanios = new javax.swing.JMenuItem();
        mnPreciosBanios = new javax.swing.JMenuItem();

        jMenuItem2.setText("jMenuItem2");

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(153, 0, 0));
        setIconImage(new ImageIcon(getClass().getResource("/resources/images/icono.png")).getImage());
        setResizable(false);

        pnFondo.setBackground(new java.awt.Color(0, 153, 153));

        lbTitulo.setBackground(new java.awt.Color(0, 153, 153));
        lbTitulo.setFont(new java.awt.Font("Agency FB", 1, 36)); // NOI18N
        lbTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lbTitulo.setText("Control de estacionamiento");

        jpanel_barra.setBackground(new java.awt.Color(0, 153, 153));
        jpanel_barra.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        tbLotes.setFont(new java.awt.Font("Agency FB", 1, 24)); // NOI18N
        tbLotes.setForeground(new java.awt.Color(0, 153, 153));
        tbLotes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "LOTE", "ESTADO"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbLotes.setGridColor(new java.awt.Color(0, 153, 153));
        tbLotes.setRowHeight(30);
        tbLotes.setRowMargin(7);
        tbLotes.setSelectionBackground(new java.awt.Color(0, 153, 153));
        jScrollPane2.setViewportView(tbLotes);

        tbLotesOcupados.setFont(new java.awt.Font("Agency FB", 1, 24)); // NOI18N
        tbLotesOcupados.setForeground(new java.awt.Color(204, 0, 51));
        tbLotesOcupados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "LOTE", "SERVICIO", "ENTRADA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbLotesOcupados.setGridColor(new java.awt.Color(255, 51, 51));
        tbLotesOcupados.setRowHeight(30);
        tbLotesOcupados.setRowMargin(7);
        tbLotesOcupados.setSelectionBackground(new java.awt.Color(204, 0, 51));
        jScrollPane3.setViewportView(tbLotesOcupados);
        if (tbLotesOcupados.getColumnModel().getColumnCount() > 0) {
            tbLotesOcupados.getColumnModel().getColumn(0).setMinWidth(50);
            tbLotesOcupados.getColumnModel().getColumn(0).setPreferredWidth(50);
            tbLotesOcupados.getColumnModel().getColumn(0).setMaxWidth(50);
            tbLotesOcupados.getColumnModel().getColumn(1).setMinWidth(50);
            tbLotesOcupados.getColumnModel().getColumn(1).setPreferredWidth(50);
            tbLotesOcupados.getColumnModel().getColumn(1).setMaxWidth(50);
        }

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Entradas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Agency FB", 1, 16), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel2.setMaximumSize(new java.awt.Dimension(432, 79));

        jLabel3.setFont(new java.awt.Font("Agency FB", 1, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Lote:");

        tfEntrada.setBackground(new java.awt.Color(204, 255, 255));
        tfEntrada.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N

        jcbservicio.setFont(new java.awt.Font("Agency FB", 1, 15)); // NOI18N
        jcbservicio.setMaximumSize(new java.awt.Dimension(32, 24));

        btnIniciarLote.setBackground(new java.awt.Color(0, 153, 153));
        btnIniciarLote.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        btnIniciarLote.setForeground(new java.awt.Color(255, 250, 250));
        btnIniciarLote.setText("Iniciar");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbservicio, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnIniciarLote, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnIniciarLote, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3)
                            .addComponent(tfEntrada)
                            .addComponent(jcbservicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(0, 153, 153));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Salidas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Agency FB", 1, 16), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel3.setForeground(new java.awt.Color(153, 153, 153));
        jPanel3.setMaximumSize(new java.awt.Dimension(455, 79));

        btnFinalizarLote.setBackground(new java.awt.Color(204, 0, 51));
        btnFinalizarLote.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        btnFinalizarLote.setForeground(new java.awt.Color(254, 254, 254));
        btnFinalizarLote.setText("Finalizar");
        btnFinalizarLote.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);

        tfSalidaLote.setBackground(new java.awt.Color(255, 204, 204));
        tfSalidaLote.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N

        jLabel21.setFont(new java.awt.Font("Agency FB", 1, 20)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Lote:");

        btnActualizarLote.setBackground(new java.awt.Color(0, 153, 204));
        btnActualizarLote.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        btnActualizarLote.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizarLote.setText("Cambiar");

        btnCancelarLote.setBackground(new java.awt.Color(52, 52, 52));
        btnCancelarLote.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        btnCancelarLote.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelarLote.setText("Cancelar");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfSalidaLote, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnFinalizarLote, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnActualizarLote, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCancelarLote, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(92, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnFinalizarLote, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tfSalidaLote, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel21)
                        .addComponent(btnActualizarLote, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelarLote, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(0, 153, 153));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Más servicios", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Agency FB", 1, 16), new java.awt.Color(255, 255, 255))); // NOI18N

        btnSanitarios.setBackground(new java.awt.Color(204, 0, 51));
        btnSanitarios.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        btnSanitarios.setForeground(new java.awt.Color(255, 250, 250));
        btnSanitarios.setText("Sanitarios");

        btnPensiones.setBackground(new java.awt.Color(0, 153, 204));
        btnPensiones.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        btnPensiones.setForeground(new java.awt.Color(255, 250, 250));
        btnPensiones.setText("Pensiones");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSanitarios, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPensiones, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSanitarios, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnPensiones, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jpanel_barraLayout = new javax.swing.GroupLayout(jpanel_barra);
        jpanel_barra.setLayout(jpanel_barraLayout);
        jpanel_barraLayout.setHorizontalGroup(
            jpanel_barraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanel_barraLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpanel_barraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jpanel_barraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpanel_barraLayout.setVerticalGroup(
            jpanel_barraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanel_barraLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpanel_barraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpanel_barraLayout.createSequentialGroup()
                        .addGroup(jpanel_barraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jpanel_barraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
                            .addComponent(jScrollPane2))
                        .addGap(188, 188, 188))
                    .addGroup(jpanel_barraLayout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout pnFondoLayout = new javax.swing.GroupLayout(pnFondo);
        pnFondo.setLayout(pnFondoLayout);
        pnFondoLayout.setHorizontalGroup(
            pnFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnFondoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jpanel_barra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnFondoLayout.setVerticalGroup(
            pnFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnFondoLayout.createSequentialGroup()
                .addComponent(lbTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpanel_barra, javax.swing.GroupLayout.PREFERRED_SIZE, 615, Short.MAX_VALUE)
                .addContainerGap())
        );

        mnHistorial1.setText("Estacionamiento");
        mnHistorial1.setFont(new java.awt.Font("Agency FB", 1, 14)); // NOI18N

        mnDatos.setFont(new java.awt.Font("Agency FB", 1, 14)); // NOI18N
        mnDatos.setText("Datos");
        mnHistorial1.add(mnDatos);

        jMenuBar1.add(mnHistorial1);

        mnHistorial.setText("Auditoria");
        mnHistorial.setFont(new java.awt.Font("Agency FB", 1, 14)); // NOI18N

        mnHistoriaAuditoria.setFont(new java.awt.Font("Agency FB", 1, 14)); // NOI18N
        mnHistoriaAuditoria.setText("Historial");
        mnHistorial.add(mnHistoriaAuditoria);

        mnReporte.setFont(new java.awt.Font("Agency FB", 1, 14)); // NOI18N
        mnReporte.setText("Reporte");
        mnHistorial.add(mnReporte);

        jMenuBar1.add(mnHistorial);

        mnLotes.setText("Lotes");
        mnLotes.setFont(new java.awt.Font("Agency FB", 1, 14)); // NOI18N

        mnHistorialLotes.setFont(new java.awt.Font("Agency FB", 1, 14)); // NOI18N
        mnHistorialLotes.setText("Historial");
        mnLotes.add(mnHistorialLotes);

        mnPrecios.setFont(new java.awt.Font("Agency FB", 1, 14)); // NOI18N
        mnPrecios.setText("Precios");
        mnLotes.add(mnPrecios);

        jMenuBar1.add(mnLotes);

        mnPensiones.setText("Pensiones");
        mnPensiones.setFont(new java.awt.Font("Agency FB", 1, 14)); // NOI18N

        mnHisPensiones.setFont(new java.awt.Font("Agency FB", 1, 14)); // NOI18N
        mnHisPensiones.setText("Historial");
        mnPensiones.add(mnHisPensiones);

        mnClientes.setFont(new java.awt.Font("Agency FB", 1, 14)); // NOI18N
        mnClientes.setText("Clientes");
        mnPensiones.add(mnClientes);

        mnCoches.setFont(new java.awt.Font("Agency FB", 1, 14)); // NOI18N
        mnCoches.setText("Coches");
        mnPensiones.add(mnCoches);

        mnPreciosPensiones.setFont(new java.awt.Font("Agency FB", 1, 14)); // NOI18N
        mnPreciosPensiones.setText("Precios");
        mnPensiones.add(mnPreciosPensiones);

        jMenuBar1.add(mnPensiones);

        mnBanios.setText("Baños");
        mnBanios.setFont(new java.awt.Font("Agency FB", 1, 14)); // NOI18N

        mnHistorialBanios.setFont(new java.awt.Font("Agency FB", 1, 14)); // NOI18N
        mnHistorialBanios.setText("Historial");
        mnBanios.add(mnHistorialBanios);

        mnPreciosBanios.setFont(new java.awt.Font("Agency FB", 1, 14)); // NOI18N
        mnPreciosBanios.setText("Precios");
        mnBanios.add(mnPreciosBanios);

        jMenuBar1.add(mnBanios);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnFondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(panelprincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(panelprincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(panelprincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(panelprincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new panelprincipal().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnActualizarLote;
    public javax.swing.JButton btnCancelarLote;
    public javax.swing.JButton btnFinalizarLote;
    public javax.swing.JButton btnIniciarLote;
    public javax.swing.JButton btnPensiones;
    public javax.swing.JButton btnSanitarios;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator9;
    public javax.swing.JComboBox<ServiciosLote> jcbservicio;
    private javax.swing.JPanel jpanel_barra;
    public javax.swing.JLabel lbTitulo;
    private javax.swing.JMenu mnBanios;
    public javax.swing.JMenuItem mnClientes;
    public javax.swing.JMenuItem mnCoches;
    public javax.swing.JMenuItem mnDatos;
    public javax.swing.JMenuItem mnHisPensiones;
    public javax.swing.JMenuItem mnHistoriaAuditoria;
    private javax.swing.JMenu mnHistorial;
    private javax.swing.JMenu mnHistorial1;
    public javax.swing.JMenuItem mnHistorialBanios;
    public javax.swing.JMenuItem mnHistorialLotes;
    private javax.swing.JMenu mnLotes;
    private javax.swing.JMenu mnPensiones;
    public javax.swing.JMenuItem mnPrecios;
    public javax.swing.JMenuItem mnPreciosBanios;
    public javax.swing.JMenuItem mnPreciosPensiones;
    public javax.swing.JMenuItem mnReporte;
    private javax.swing.JPanel pnFondo;
    public javax.swing.JTable tbLotes;
    public javax.swing.JTable tbLotesOcupados;
    public javax.swing.JTextField tfEntrada;
    public javax.swing.JTextField tfSalidaLote;
    // End of variables declaration//GEN-END:variables

    void setMaximizedBounds(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
