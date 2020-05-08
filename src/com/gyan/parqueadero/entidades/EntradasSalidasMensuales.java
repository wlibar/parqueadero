/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gyan.parqueadero.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author libar
 */
@Entity
@Table(name = "entradas_salidas_mensuales")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntradasSalidasMensuales.findAll", query = "SELECT e FROM EntradasSalidasMensuales e")
    , @NamedQuery(name = "EntradasSalidasMensuales.findById", query = "SELECT e FROM EntradasSalidasMensuales e WHERE e.id = :id")
    , @NamedQuery(name = "EntradasSalidasMensuales.findByPlaca", query = "SELECT e FROM EntradasSalidasMensuales e WHERE e.pagosMensualesId.placa.placa = :placa and e.fechaHoraSalida = null")
    , @NamedQuery(name = "EntradasSalidasMensuales.findByFichaNro", query = "SELECT e FROM EntradasSalidasMensuales e WHERE e.fichaNro = :fichaNro and e.fechaHoraSalida = null")
    , @NamedQuery(name = "EntradasSalidasMensuales.findVehiculosPorLiquidar", query = "SELECT e FROM EntradasSalidasMensuales e WHERE e.fechaHoraSalida = null")
    })
public class EntradasSalidasMensuales implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "ficha_nro")
    private String fichaNro;
    
    @Basic(optional = false)
    @Column(name = "fecha_hora_entrada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraEntrada;
    
    @Column(name = "fecha_hora_salida")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraSalida;
    
    @Column(name = "cascos_nro")
    private String cascosNro;
    
    @Column(name = "deja_llaves")
    private Boolean dejaLlaves;
    
    @Column(name = "casillero_nro")
    private String casilleroNro;
    
    @Column(name = "extravio_tarjeta")
    private Boolean extravioTarjeta;
    
    @Column(name = "observacion")
    private String observacion;
    
    @JoinColumn(name = "entrada_empleados_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Empleados entradaEmpleadosId;
    
    @JoinColumn(name = "salida_empleados_id", referencedColumnName = "id")
    @ManyToOne
    private Empleados salidaEmpleadosId;
    
    @JoinColumn(name = "pagos_mensuales_id", referencedColumnName = "id")
    @ManyToOne
    private PagosMensuales pagosMensualesId;

    public EntradasSalidasMensuales() {
    }

    public EntradasSalidasMensuales(Integer id) {
        this.id = id;
    }

    public EntradasSalidasMensuales(Integer id, Date fechaHoraEntrada) {
        this.id = id;
        this.fechaHoraEntrada = fechaHoraEntrada;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFichaNro() {
        return fichaNro;
    }

    public void setFichaNro(String fichaNro) {
        this.fichaNro = fichaNro;
    }

    public Date getFechaHoraEntrada() {
        return fechaHoraEntrada;
    }

    public void setFechaHoraEntrada(Date fechaHoraEntrada) {
        this.fechaHoraEntrada = fechaHoraEntrada;
    }

    public Date getFechaHoraSalida() {
        return fechaHoraSalida;
    }

    public void setFechaHoraSalida(Date fechaHoraSalida) {
        this.fechaHoraSalida = fechaHoraSalida;
    }

    public String getCascosNro() {
        return cascosNro;
    }

    public void setCascosNro(String cascosNro) {
        this.cascosNro = cascosNro;
    }

    public Boolean getDejaLlaves() {
        return dejaLlaves;
    }

    public void setDejaLlaves(Boolean dejaLlaves) {
        this.dejaLlaves = dejaLlaves;
    }

    public String getCasilleroNro() {
        return casilleroNro;
    }

    public void setCasilleroNro(String casilleroNro) {
        this.casilleroNro = casilleroNro;
    }

    public Boolean getExtravioTarjeta() {
        return extravioTarjeta;
    }

    public void setExtravioTarjeta(Boolean extravioTarjeta) {
        this.extravioTarjeta = extravioTarjeta;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Empleados getEntradaEmpleadosId() {
        return entradaEmpleadosId;
    }

    public void setEntradaEmpleadosId(Empleados entradaEmpleadosId) {
        this.entradaEmpleadosId = entradaEmpleadosId;
    }

    public Empleados getSalidaEmpleadosId() {
        return salidaEmpleadosId;
    }

    public void setSalidaEmpleadosId(Empleados salidaEmpleadosId) {
        this.salidaEmpleadosId = salidaEmpleadosId;
    }

    public PagosMensuales getPagosMensualesId() {
        return pagosMensualesId;
    }

    public void setPagosMensualesId(PagosMensuales pagosMensualesId) {
        this.pagosMensualesId = pagosMensualesId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntradasSalidasMensuales)) {
            return false;
        }
        EntradasSalidasMensuales other = (EntradasSalidasMensuales) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gyan.parqueadero.entidades.EntradasSalidasMensuales[ id=" + id + " ]";
    }
    
}
