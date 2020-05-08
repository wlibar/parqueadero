/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gyan.parqueadero.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author libardo
 */
@Entity
@Table(name = "balance")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Balance.findAll", query = "SELECT b FROM Balance b")
    , @NamedQuery(name = "Balance.findById", query = "SELECT b FROM Balance b WHERE b.id = :id")
    , @NamedQuery(name = "Balance.findByPeriodo", query = "SELECT b FROM Balance b WHERE b.periodo = :periodo")
    , @NamedQuery(name = "Balance.findByTotalEntradas", query = "SELECT b FROM Balance b WHERE b.totalEntradas = :totalEntradas")
    , @NamedQuery(name = "Balance.findByTotalSalidas", query = "SELECT b FROM Balance b WHERE b.totalSalidas = :totalSalidas")})
public class Balance implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "periodo")
    private String periodo;
    @Column(name = "total_entradas")
    private String totalEntradas;
    @Column(name = "total_salidas")
    private String totalSalidas;

    public Balance() {
    }

    public Balance(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getTotalEntradas() {
        return totalEntradas;
    }

    public void setTotalEntradas(String totalEntradas) {
        this.totalEntradas = totalEntradas;
    }

    public String getTotalSalidas() {
        return totalSalidas;
    }

    public void setTotalSalidas(String totalSalidas) {
        this.totalSalidas = totalSalidas;
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
        if (!(object instanceof Balance)) {
            return false;
        }
        Balance other = (Balance) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gyan.parqueadero.entidades.Balance[ id=" + id + " ]";
    }
    
}
