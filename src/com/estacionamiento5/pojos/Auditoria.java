package com.estacionamiento5.pojos;
// Generated 22/02/2018 08:56:57 PM by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import java.util.Date;

/**
 * Auditoria generated by hbm2java
 */
public class Auditoria  implements java.io.Serializable {


     private Long idServicio;
     private String tipoServicio;
     private Date fecha1;
     private Date fecha2;
     private BigDecimal costo;
     private Date fecha;
     private boolean transaccion;

    public Auditoria() {
    }

    public Auditoria(String tipoServicio, Date fecha1, Date fecha2, BigDecimal costo, Date fecha, boolean transaccion) {
       this.tipoServicio = tipoServicio;
       this.fecha1 = fecha1;
       this.fecha2 = fecha2;
       this.costo = costo;
       this.fecha = fecha;
       this.transaccion = transaccion;
    }
   
    public Long getIdServicio() {
        return this.idServicio;
    }
    
    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }
    public String getTipoServicio() {
        return this.tipoServicio;
    }
    
    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }
    public Date getFecha1() {
        return this.fecha1;
    }
    
    public void setFecha1(Date fecha1) {
        this.fecha1 = fecha1;
    }
    public Date getFecha2() {
        return this.fecha2;
    }
    
    public void setFecha2(Date fecha2) {
        this.fecha2 = fecha2;
    }
    public BigDecimal getCosto() {
        return this.costo;
    }
    
    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }
    public Date getFecha() {
        return this.fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean isTransaccion() {
        return transaccion;
    }

    public void setTransaccion(boolean transaccion) {
        this.transaccion = transaccion;
    }

}


