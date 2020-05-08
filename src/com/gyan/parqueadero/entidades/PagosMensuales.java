/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gyan.parqueadero.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author libar
 */
@Entity
@Table(name = "pagos_mensuales")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PagosMensuales.findAll", query = "SELECT p FROM PagosMensuales p")
    , @NamedQuery(name = "PagosMensuales.findById", query = "SELECT p FROM PagosMensuales p WHERE p.id = :id")
    , @NamedQuery(name = "PagosMensuales.findByPlaca", query = "SELECT p FROM PagosMensuales p WHERE p.placa.placa = :placa ORDER BY p.fechaInicio DESC")                
    , @NamedQuery(name = "PagosMensuales.findByFechaInicio", query = "SELECT p FROM PagosMensuales p WHERE p.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "PagosMensuales.findByFechaFin", query = "SELECT p FROM PagosMensuales p WHERE p.fechaFin = :fechaFin")
    , @NamedQuery(name = "PagosMensuales.findByDescuento", query = "SELECT p FROM PagosMensuales p WHERE p.descuento = :descuento")
    , @NamedQuery(name = "PagosMensuales.findByEmpleadoFecha", query = "SELECT p FROM PagosMensuales p WHERE (p.fechaInicio > :fecha1 AND  p.fechaInicio < :fecha2 AND  p.empleadosId.id = :empleadoId)") 
    , @NamedQuery(name = "PagosMensuales.findByFecha", query = "SELECT p FROM PagosMensuales p WHERE (p.fechaInicio > :fecha1 AND  p.fechaInicio < :fecha2)")                 
    , @NamedQuery(name = "PagosMensuales.findByValorPagar", query = "SELECT p FROM PagosMensuales p WHERE p.valorPagar = :valorPagar")})
public class PagosMensuales implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    
    @Column(name = "descuento")
    private Integer descuento;
    
    @Basic(optional = false)
    @Column(name = "valor_pagar")
    private int valorPagar;
    
    @OneToMany(mappedBy = "pagosMensualesId")
    private List<EntradasSalidasMensuales> entradasSalidasMensualesList;
        
    
    @JoinColumn(name = "empleados_id", referencedColumnName = "id")
    @ManyToOne
    private Empleados empleadosId;
    
    @JoinColumn(name = "placa", referencedColumnName = "placa")
    @ManyToOne(optional = false)
    private Vehiculos placa;
    
    @Column(name = "observacion")
    private String observacion;    

    public PagosMensuales() {
    }

    public PagosMensuales(Integer id) {
        this.id = id;
    }

    public PagosMensuales(Integer id, Date fechaInicio, int valorPagar) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.valorPagar = valorPagar;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getDescuento() {
        return descuento;
    }

    public void setDescuento(Integer descuento) {
        this.descuento = descuento;
    }

    public int getValorPagar() {
        return valorPagar;
    }

    public void setValorPagar(int valorPagar) {
        this.valorPagar = valorPagar;
    }

    @XmlTransient
    public List<EntradasSalidasMensuales> getEntradasSalidasMensualesList() {
        return entradasSalidasMensualesList;
    }

    public void setEntradasSalidasMensualesList(List<EntradasSalidasMensuales> entradasSalidasMensualesList) {
        this.entradasSalidasMensualesList = entradasSalidasMensualesList;
    }

    public Empleados getEmpleadosId() {
        return empleadosId;
    }

    public void setEmpleadosId(Empleados empleadosId) {
        this.empleadosId = empleadosId;
    }

    public Vehiculos getPlaca() {
        return placa;
    }

    public void setPlaca(Vehiculos placa) {
        this.placa = placa;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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
        if (!(object instanceof PagosMensuales)) {
            return false;
        }
        PagosMensuales other = (PagosMensuales) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gyan.parqueadero.entidades.PagosMensuales[ id=" + id + " ]";
    }
    
}
