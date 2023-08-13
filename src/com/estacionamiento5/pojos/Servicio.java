
package com.estacionamiento5.pojos;

import java.math.BigDecimal;

/**
 *
 * @author Rhoades - pc
 */
public class Servicio {
    
    String id;
    String nombre;
    BigDecimal costo;
    
    public Servicio() {
    }	
    public Servicio(String id) {
        this.id = id;
    }
    public Servicio(String id, String nombre, BigDecimal costo) {
       this.id = id;
       this.nombre = nombre;
       this.costo = costo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
}
