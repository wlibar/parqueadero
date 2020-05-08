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
 * @author libardo
 */
@Entity
@Table(name = "gastos_mensuales")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GastosMensuales.findAll", query = "SELECT g FROM GastosMensuales g")
    , @NamedQuery(name = "GastosMensuales.findById", query = "SELECT g FROM GastosMensuales g WHERE g.id = :id")
    , @NamedQuery(name = "GastosMensuales.findByFechas", query = "SELECT g FROM GastosMensuales g WHERE g.fecha >= :fecha1 AND g.fecha <= :fecha2")
    , @NamedQuery(name = "GastosMensuales.findByValor", query = "SELECT g FROM GastosMensuales g WHERE g.valor = :valor")
    , @NamedQuery(name = "GastosMensuales.findByDescripcion", query = "SELECT g FROM GastosMensuales g WHERE g.descripcion = :descripcion")})
public class GastosMensuales implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "valor")
    private Integer valor;
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "tipos_gastos_id", referencedColumnName = "id")
    @ManyToOne
    private TiposGastos tiposGastosId;

    public GastosMensuales() {
    }

    public GastosMensuales(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TiposGastos getTiposGastosId() {
        return tiposGastosId;
    }

    public void setTiposGastosId(TiposGastos tiposGastosId) {
        this.tiposGastosId = tiposGastosId;
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
        if (!(object instanceof GastosMensuales)) {
            return false;
        }
        GastosMensuales other = (GastosMensuales) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gyan.parqueadero.entidades.GastosMensuales[ id=" + id + " ]";
    }
    
}
