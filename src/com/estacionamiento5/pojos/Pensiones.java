package com.estacionamiento5.pojos;
// Generated 22/02/2018 08:56:57 PM by Hibernate Tools 4.3.1


import java.math.BigDecimal;

/**
 * Pensiones generated by hbm2java
 */
public class Pensiones extends Servicio implements java.io.Serializable {

    private Integer periodo;
    
    public Pensiones() {
    }

    public Pensiones(Integer periodo) {
        this.periodo = periodo;
    }

    public Pensiones(Integer periodo, String id) {
        super(id);
        this.periodo = periodo;
    }

    public Pensiones(String id, String nombre, Integer periodo, BigDecimal costo) {
        super(id, nombre, costo);
        this.periodo = periodo;
    }

    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }

}


