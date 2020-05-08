/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gyan.parqueadero.logicanegocio;

import com.gyan.parqueadero.dao.GastosMensualesJpaController;
import com.gyan.parqueadero.dao.PagosMensualesJpaController;
import com.gyan.parqueadero.entidades.GastosMensuales;
import com.gyan.parqueadero.entidades.PagosMensuales;
import com.gyan.parqueadero.entidades.TiposGastos;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author libardo
 */
public class GastosMensualesLogicaNegocio {

    private EntityManagerFactory emf;

    public GastosMensualesLogicaNegocio() {
        emf = Persistence.createEntityManagerFactory("ParqueaderoPU");
    }

    public GastosMensuales getGastoMensual(int id) {
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("GastosMensuales.findById");
        query.setParameter("id", id);
        List<GastosMensuales> lista = query.getResultList();
        if (lista.size() > 0) {
            return lista.get(0);
        }
        return null;
    }

    public List<GastosMensuales> getGastosMensualesPorFecha(Date fecha1, Date fecha2) {
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("GastosMensuales.findByFechas");
        query.setParameter("fecha1", fecha1);
        query.setParameter("fecha2", fecha2);
        //query.setMaxResults(100);
        return query.getResultList();
    }

    public List<TiposGastos> getTiposGastos() {
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("TiposGastos.findAll");
        return query.getResultList();
    }
    
    public int agregarGasto(int tipoGastoId, Date fecha, int valor, String descripcion){
        TiposGastos tipoGasto = getTipoGasto(tipoGastoId);
        GastosMensuales gasMen = new GastosMensuales();
        gasMen.setTiposGastosId(tipoGasto);
        gasMen.setFecha(fecha);
        gasMen.setValor(valor);
        gasMen.setDescripcion(descripcion);
        GastosMensualesJpaController controller = new GastosMensualesJpaController(emf);
        return controller.create(gasMen);
        
    }
    
    public void editarGasto(int id, int tipoGastoId, Date fecha, int valor, String descripcion) throws Exception{
        TiposGastos tipoGasto = getTipoGasto(tipoGastoId);
        
        GastosMensualesJpaController controller = new GastosMensualesJpaController(emf);
        GastosMensuales gasMen = controller.findGastosMensuales(id);
        
        gasMen.setTiposGastosId(tipoGasto);
        gasMen.setFecha(fecha);
        gasMen.setValor(valor);
        gasMen.setDescripcion(descripcion);
        controller.edit(gasMen);
        
    }    
    
    public TiposGastos getTipoGasto(int id) {
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("TiposGastos.findById");
        query.setParameter("id", id);
        List<TiposGastos> lista = query.getResultList();
        if (lista.size() > 0) {
            return lista.get(0);
        }
        return null;
    }    
}
