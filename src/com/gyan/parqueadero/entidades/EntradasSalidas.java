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
@Table(name = "entradas_salidas")
@XmlRootElement
@NamedQueries({
    
    @NamedQuery(name = "EntradasSalidas.findAll", query = "SELECT e FROM EntradasSalidas e")
    , @NamedQuery(name = "EntradasSalidas.findById", query = "SELECT e FROM EntradasSalidas e WHERE e.id = :id")
    , @NamedQuery(name = "EntradasSalidas.findByPlaca", query = "SELECT e FROM EntradasSalidas e WHERE e.placa.placa = :placa and e.fechaHoraSalida = null")
    , @NamedQuery(name = "EntradasSalidas.findByFichaNro", query = "SELECT e FROM EntradasSalidas e WHERE e.fichaNro = :fichaNro and e.fechaHoraSalida = null")
    , @NamedQuery(name = "EntradasSalidas.findByEmpleadoFecha", query = "SELECT e FROM EntradasSalidas e WHERE (e.fechaHoraSalida > :fecha1 AND  e.fechaHoraSalida < :fecha2 AND e.entradaEmpleadosId.id = :empleadoId)") 
    , @NamedQuery(name = "EntradasSalidas.findByFecha", query = "SELECT e FROM EntradasSalidas e WHERE (e.fechaHoraSalida > :fecha1 AND  e.fechaHoraSalida < :fecha2)")         
    , @NamedQuery(name = "EntradasSalidas.findVehiculosPorLiquidar", query = "SELECT e FROM EntradasSalidas e WHERE e.fechaHoraSalida = null")                
})
public class EntradasSalidas implements Serializable {

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
    @Basic(optional = false)
    @Column(name = "cascos_nro")
    private String cascosNro;
    @Column(name = "deja_llaves")
    private Boolean dejaLlaves;
    @Basic(optional = false)
    @Column(name = "casillero_nro")
    private String casilleroNro;
    @Column(name = "extravio_tarjeta")
    private Boolean extravioTarjeta;
    @Basic(optional = false)
    @Column(name = "descuento")
    private float descuento;
    @Basic(optional = false)
    @Column(name = "valor_pagar")
    private int valorPagar;
    @Column(name = "observacion")
    private String observacion;
    @JoinColumn(name = "entrada_empleados_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Empleados entradaEmpleadosId;
    @JoinColumn(name = "salida_empleados_id", referencedColumnName = "id")
    @ManyToOne
    private Empleados salidaEmpleadosId;
    @JoinColumn(name = "placa", referencedColumnName = "placa")
    @ManyToOne(optional = false)
    private Vehiculos placa;

    public EntradasSalidas() {
    }

    public EntradasSalidas(Integer id) {
        this.id = id;
    }

    public EntradasSalidas(Integer id, Date fechaHoraEntrada, String cascosNro, String casilleroNro, float descuento, int valorPagar) {
        this.id = id;
        this.fechaHoraEntrada = fechaHoraEntrada;
        this.cascosNro = cascosNro;
        this.casilleroNro = casilleroNro;
        this.descuento = descuento;
        this.valorPagar = valorPagar;
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

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    public int getValorPagar() {
        return valorPagar;
    }

    public void setValorPagar(int valorPagar) {
        this.valorPagar = valorPagar;
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

    public Vehiculos getPlaca() {
        return placa;
    }

    public void setPlaca(Vehiculos placa) {
        this.placa = placa;
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
        if (!(object instanceof EntradasSalidas)) {
            return false;
        }
        EntradasSalidas other = (EntradasSalidas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gyan.parqueadero.entidades.EntradasSalidas[ id=" + id + " ]";
    }
    
}
