
package com.estacionamiento5.pojos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Rhoades - pc
 */
public class DatosEstacionamiento implements Serializable {

    private int id;
    private String nombre;
    private String direccion;
    private String colonia;
    private int cp;
    private String municipio;
    private String estado;
    private String regimen;
    private String rfc;
    private String ticketSerie;
    private Date horarioLunesViernesApertura;
    private Date horarioLunesViernesCierre;
    private Date horarioSabadoApertura;
    private Date horarioSabadoCierre;
    private Date horarioDomingoApertura;
    private Date horarioDomingoCierre;
    private BigDecimal montoPerdidaTicket;

    public DatosEstacionamiento() {
    }

    public DatosEstacionamiento(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public Integer getCp() {
        return cp;
    }

    public void setCp(Integer cp) {
        this.cp = cp;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getRegimen() {
        return regimen;
    }

    public void setRegimen(String regimen) {
        this.regimen = regimen;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getTicketSerie() {
        return ticketSerie;
    }

    public void setTicketSerie(String ticketSerie) {
        this.ticketSerie = ticketSerie;
    }

    public Date getHorarioLunesViernesApertura() {
        return horarioLunesViernesApertura;
    }

    public void setHorarioLunesViernesApertura(Date horarioLunesViernesApertura) {
        this.horarioLunesViernesApertura = horarioLunesViernesApertura;
    }

    public Date getHorarioLunesViernesCierre() {
        return horarioLunesViernesCierre;
    }

    public void setHorarioLunesViernesCierre(Date horarioLunesViernesCierre) {
        this.horarioLunesViernesCierre = horarioLunesViernesCierre;
    }

    public Date getHorarioSabadoApertura() {
        return horarioSabadoApertura;
    }

    public void setHorarioSabadoApertura(Date horarioSabadoApertura) {
        this.horarioSabadoApertura = horarioSabadoApertura;
    }

    public Date getHorarioSabadoCierre() {
        return horarioSabadoCierre;
    }

    public void setHorarioSabadoCierre(Date horarioSabadoCierre) {
        this.horarioSabadoCierre = horarioSabadoCierre;
    }

    public Date getHorarioDomingoApertura() {
        return horarioDomingoApertura;
    }

    public void setHorarioDomingoApertura(Date horarioDomingoApertura) {
        this.horarioDomingoApertura = horarioDomingoApertura;
    }

    public Date getHorarioDomingoCierre() {
        return horarioDomingoCierre;
    }

    public void setHorarioDomingoCierre(Date horarioDomingoCierre) {
        this.horarioDomingoCierre = horarioDomingoCierre;
    }

    public BigDecimal getMontoPerdidaTicket() {
        return montoPerdidaTicket;
    }

    public void setMontoPerdidaTicket(BigDecimal montoPerdidaTicket) {
        this.montoPerdidaTicket = montoPerdidaTicket;
    }

    @Override
    public String toString() {
        return "DatosEstacionamiento{" + "id=" + id + ", nombre=" + nombre + ", direccion=" + direccion + ", colonia=" + colonia + ", cp=" + cp + ", municipio=" + municipio + ", estado=" + estado + ", regimen=" + regimen + ", rfc=" + rfc + ", ticketSerie=" + ticketSerie + ", horarioLunesViernesApertura=" + horarioLunesViernesApertura + ", horarioLunesViernesCierre=" + horarioLunesViernesCierre + ", horarioSabadoApertura=" + horarioSabadoApertura + ", horarioSabadoCierre=" + horarioSabadoCierre + ", horarioDomingoApertura=" + horarioDomingoApertura + ", horarioDomingoCierre=" + horarioDomingoCierre + ", montoPerdidaTicket=" + montoPerdidaTicket + '}';
    }
       
}
