/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gyan.parqueadero.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author libar
 */
@Entity
@Table(name = "empleados")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empleados.findAll", query = "SELECT e FROM Empleados e")
    , @NamedQuery(name = "Empleados.findById", query = "SELECT e FROM Empleados e WHERE e.id = :id")
    , @NamedQuery(name = "Empleados.findByNombres", query = "SELECT e FROM Empleados e WHERE e.nombres = :nombres")
    , @NamedQuery(name = "Empleados.findByApellidos", query = "SELECT e FROM Empleados e WHERE e.apellidos = :apellidos")
    , @NamedQuery(name = "Empleados.findByDocumento", query = "SELECT e FROM Empleados e WHERE e.documento = :documento")
    , @NamedQuery(name = "Empleados.findByCelular", query = "SELECT e FROM Empleados e WHERE e.celular = :celular")
    , @NamedQuery(name = "Empleados.findByContrasenia", query = "SELECT e FROM Empleados e WHERE e.contrasenia = :contrasenia")
    , @NamedQuery(name = "Empleados.findByEmail", query = "SELECT e FROM Empleados e WHERE e.email = :email")
    , @NamedQuery(name = "Empleados.findByCargo", query = "SELECT e FROM Empleados e WHERE e.cargo = :cargo")
    , @NamedQuery(name = "Empleados.findByEstado", query = "SELECT e FROM Empleados e WHERE e.estado = :estado")})
public class Empleados implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombres")
    private String nombres;
    @Basic(optional = false)
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "documento")
    private String documento;
    @Column(name = "celular")
    private String celular;
    @Column(name = "contrasenia")
    private String contrasenia;
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "cargo")
    private String cargo;
    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entradaEmpleadosId")
    private List<EntradasSalidas> entradasSalidasList;
    @OneToMany(mappedBy = "salidaEmpleadosId")
    private List<EntradasSalidas> entradasSalidasList1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entradaEmpleadosId")
    private List<EntradasSalidasMensuales> entradasSalidasMensualesList;
    
    @OneToMany(mappedBy = "salidaEmpleadosId")
    private List<EntradasSalidasMensuales> entradasSalidasMensualesList1;
    
    @OneToMany(mappedBy = "empleadosId")
    private List<PagosMensuales> pagosMensualesList;

    public Empleados() {
    }

    public Empleados(Integer id) {
        this.id = id;
    }

    public Empleados(Integer id, String nombres, String apellidos, String cargo, String estado) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.cargo = cargo;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<EntradasSalidas> getEntradasSalidasList() {
        return entradasSalidasList;
    }

    public void setEntradasSalidasList(List<EntradasSalidas> entradasSalidasList) {
        this.entradasSalidasList = entradasSalidasList;
    }

    @XmlTransient
    public List<EntradasSalidas> getEntradasSalidasList1() {
        return entradasSalidasList1;
    }

    public void setEntradasSalidasList1(List<EntradasSalidas> entradasSalidasList1) {
        this.entradasSalidasList1 = entradasSalidasList1;
    }

    @XmlTransient
    public List<EntradasSalidasMensuales> getEntradasSalidasMensualesList() {
        return entradasSalidasMensualesList;
    }

    public void setEntradasSalidasMensualesList(List<EntradasSalidasMensuales> entradasSalidasMensualesList) {
        this.entradasSalidasMensualesList = entradasSalidasMensualesList;
    }

    @XmlTransient
    public List<EntradasSalidasMensuales> getEntradasSalidasMensualesList1() {
        return entradasSalidasMensualesList1;
    }

    public void setEntradasSalidasMensualesList1(List<EntradasSalidasMensuales> entradasSalidasMensualesList1) {
        this.entradasSalidasMensualesList1 = entradasSalidasMensualesList1;
    }

    @XmlTransient
    public List<PagosMensuales> getPagosMensualesList() {
        return pagosMensualesList;
    }

    public void setPagosMensualesList(List<PagosMensuales> pagosMensualesList) {
        this.pagosMensualesList = pagosMensualesList;
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
        if (!(object instanceof Empleados)) {
            return false;
        }
        Empleados other = (Empleados) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gyan.parqueadero.entidades.Empleados[ id=" + id + " ]";
    }
    
}
