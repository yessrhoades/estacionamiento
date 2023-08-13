
package com.estacionamiento5.controllers;

import com.estacionamiento5.pojos.DatosEstacionamiento;
import com.estacionamiento5.views.panelprincipal;
import java.awt.event.*;
import com.estacionamiento5.repository.Repository;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

/**
 *
 * @author Rhoades
 */
public class PanelPrincipalController extends Controller
{
    //0,153,153 color
    private panelprincipal principalView;
    private LotesController lotesController;
    private ClientesController clientesController;
    private DatosEstacionamientoController datos;
    private AuditoriaController auditoriaController;
    private BaniosController baniosController;
    private HisLotesController hisLotesController;
    private PreciosLoteController preciosLoteController;
    private CochesController cochesController;
    private PreciosPensionesController preciosPensionesController;
    private PensionesController pensionesController;
    private PreciosBaniosController preciosBaniosController;
    private ReporteController reporteController;
    private HisPensionesController hisPensionesController;
    
    public PanelPrincipalController ()
    {
        this.principalView = new panelprincipal();
        this.lotesController = new LotesController(principalView);
        this.clientesController = new ClientesController();
        this.datos = new DatosEstacionamientoController();
        this.auditoriaController = new AuditoriaController();
        this.baniosController = new BaniosController();
        this.hisLotesController = new HisLotesController();
        this.preciosLoteController = new PreciosLoteController(principalView, lotesController);
        this.cochesController = new CochesController();
        this.preciosPensionesController = new PreciosPensionesController();
        this.pensionesController = new PensionesController();
        this.preciosBaniosController = new PreciosBaniosController();
        this.reporteController = new ReporteController();
        this.hisPensionesController = new HisPensionesController();
        inicio();
    }
    
    public void inicio()
    {
        try {
            Repository.getInstance().openSession();
            Repository.getInstance().openTransaction();
            DatosEstacionamiento estacionamiento = (DatosEstacionamiento) Repository.getInstance().getObject(new DatosEstacionamiento().getClass(), "id", 1);
            String fecha = formatoFecha.format(fechaActual());
            Date fecha_ = formatoTimeStamp.parse(fecha+" 00:00:00");
            Repository.getInstance().getPensionesRepository().FinalizarPensiones(fecha_);
            
            this.principalView.setTitle("Control de estacionamiento");
            this.principalView.setLocationRelativeTo(null);
            this.principalView.setResizable(false);
            this.principalView.lbTitulo.setText(estacionamiento.getNombre());
            this.principalView.lbTitulo.setHorizontalAlignment(SwingConstants.CENTER);
            
            tableRenderLotes tr = new tableRenderLotes();
            lotesController.inicio();            
            this.principalView.tbLotes.setDefaultRenderer(Object.class,tr);
            
            this.principalView.btnSanitarios.addActionListener(this);
            this.principalView.mnHistoriaAuditoria.addActionListener(this);
            this.principalView.mnHistorialLotes.addActionListener(this);
            this.principalView.mnPrecios.addActionListener(this);
            this.principalView.mnClientes.addActionListener(this);
            this.principalView.mnCoches.addActionListener(this);
            this.principalView.btnPensiones.addActionListener(this);
            this.principalView.mnPreciosPensiones.addActionListener(this);
            this.principalView.mnHistorialBanios.addActionListener(this); 
            this.principalView.mnDatos.addActionListener(this);
            this.principalView.mnPreciosBanios.addActionListener(this);
            this.principalView.mnReporte.addActionListener(this);
            this.principalView.mnHisPensiones.addActionListener(this);
            this.principalView.btnCancelarLote.addActionListener(this);
            
            Repository.getInstance().commit();
            this.principalView.show();
        } catch (Exception ex) {
            Repository.getInstance().rollback();
            JOptionPane.showMessageDialog(null, "Error al cargar ventana principal:\n"+ex.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);
        }
    }
        
    @Override
    public void actionPerformed(ActionEvent av)
    {
        if(av.getSource() == principalView.mnClientes) clientesController.inicio();
        if(av.getSource() == principalView.mnDatos) datos.inicio();
        if(av.getSource() == principalView.mnHistoriaAuditoria) auditoriaController.inicio();
        if(av.getSource() == principalView.mnHistorialBanios) baniosController.inicio();
        if(av.getSource() == principalView.mnHistorialLotes) hisLotesController.inicio();
        if(av.getSource() == principalView.btnSanitarios) baniosController.addServicio("SANITARIOS");
        if(av.getSource() == principalView.mnPrecios) preciosLoteController.inicio();
        if(av.getSource() == principalView.mnCoches) cochesController.inicio();
        if(av.getSource() == principalView.mnPreciosPensiones) preciosPensionesController.inicio();
        if(av.getSource() == principalView.btnPensiones) pensionesController.inicio();
        if(av.getSource() == principalView.mnPreciosBanios) preciosBaniosController.inicio();
        if(av.getSource() == principalView.mnReporte) reporteController.inicio();
        if(av.getSource() == principalView.mnHisPensiones) hisPensionesController.inicio();
        if(av.getSource() == principalView.btnCancelarLote) lotesController.cancelacion();
    }

    @Override
    public void table() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validator() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
