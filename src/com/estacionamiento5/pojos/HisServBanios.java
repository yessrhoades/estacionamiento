
package com.estacionamiento5.pojos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Rhoades - pc
 */

public class HisServBanios implements Serializable {
    
    private Long id;
    private String tipoServicio;
    private BigDecimal costo;
    private Date fecha;

    public HisServBanios() {
    }

    public HisServBanios(Long id) {
        this.id = id;
    }

    public HisServBanios(String tipoServicio, BigDecimal costo, Date fecha) {
        this.tipoServicio = tipoServicio;
        this.costo = costo;
        this.fecha = fecha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
}
