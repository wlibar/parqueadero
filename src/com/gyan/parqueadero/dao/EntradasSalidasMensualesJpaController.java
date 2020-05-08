/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gyan.parqueadero.dao;

import com.gyan.parqueadero.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.gyan.parqueadero.entidades.Empleados;
import com.gyan.parqueadero.entidades.EntradasSalidasMensuales;
import com.gyan.parqueadero.entidades.PagosMensuales;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author libar
 */
public class EntradasSalidasMensualesJpaController implements Serializable {

    public EntradasSalidasMensualesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EntradasSalidasMensuales entradasSalidasMensuales) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleados entradaEmpleadosId = entradasSalidasMensuales.getEntradaEmpleadosId();
            if (entradaEmpleadosId != null) {
                entradaEmpleadosId = em.getReference(entradaEmpleadosId.getClass(), entradaEmpleadosId.getId());
                entradasSalidasMensuales.setEntradaEmpleadosId(entradaEmpleadosId);
            }
            Empleados salidaEmpleadosId = entradasSalidasMensuales.getSalidaEmpleadosId();
            if (salidaEmpleadosId != null) {
                salidaEmpleadosId = em.getReference(salidaEmpleadosId.getClass(), salidaEmpleadosId.getId());
                entradasSalidasMensuales.setSalidaEmpleadosId(salidaEmpleadosId);
            }
            PagosMensuales pagosMensualesId = entradasSalidasMensuales.getPagosMensualesId();
            if (pagosMensualesId != null) {
                pagosMensualesId = em.getReference(pagosMensualesId.getClass(), pagosMensualesId.getId());
                entradasSalidasMensuales.setPagosMensualesId(pagosMensualesId);
            }
            em.persist(entradasSalidasMensuales);
            if (entradaEmpleadosId != null) {
                entradaEmpleadosId.getEntradasSalidasMensualesList().add(entradasSalidasMensuales);
                entradaEmpleadosId = em.merge(entradaEmpleadosId);
            }
            if (salidaEmpleadosId != null) {
                salidaEmpleadosId.getEntradasSalidasMensualesList().add(entradasSalidasMensuales);
                salidaEmpleadosId = em.merge(salidaEmpleadosId);
            }
            if (pagosMensualesId != null) {
                pagosMensualesId.getEntradasSalidasMensualesList().add(entradasSalidasMensuales);
                pagosMensualesId = em.merge(pagosMensualesId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EntradasSalidasMensuales entradasSalidasMensuales) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EntradasSalidasMensuales persistentEntradasSalidasMensuales = em.find(EntradasSalidasMensuales.class, entradasSalidasMensuales.getId());
            Empleados entradaEmpleadosIdOld = persistentEntradasSalidasMensuales.getEntradaEmpleadosId();
            Empleados entradaEmpleadosIdNew = entradasSalidasMensuales.getEntradaEmpleadosId();
            Empleados salidaEmpleadosIdOld = persistentEntradasSalidasMensuales.getSalidaEmpleadosId();
            Empleados salidaEmpleadosIdNew = entradasSalidasMensuales.getSalidaEmpleadosId();
            PagosMensuales pagosMensualesIdOld = persistentEntradasSalidasMensuales.getPagosMensualesId();
            PagosMensuales pagosMensualesIdNew = entradasSalidasMensuales.getPagosMensualesId();
            if (entradaEmpleadosIdNew != null) {
                entradaEmpleadosIdNew = em.getReference(entradaEmpleadosIdNew.getClass(), entradaEmpleadosIdNew.getId());
                entradasSalidasMensuales.setEntradaEmpleadosId(entradaEmpleadosIdNew);
            }
            if (salidaEmpleadosIdNew != null) {
                salidaEmpleadosIdNew = em.getReference(salidaEmpleadosIdNew.getClass(), salidaEmpleadosIdNew.getId());
                entradasSalidasMensuales.setSalidaEmpleadosId(salidaEmpleadosIdNew);
            }
            if (pagosMensualesIdNew != null) {
                pagosMensualesIdNew = em.getReference(pagosMensualesIdNew.getClass(), pagosMensualesIdNew.getId());
                entradasSalidasMensuales.setPagosMensualesId(pagosMensualesIdNew);
            }
            entradasSalidasMensuales = em.merge(entradasSalidasMensuales);
            if (entradaEmpleadosIdOld != null && !entradaEmpleadosIdOld.equals(entradaEmpleadosIdNew)) {
                entradaEmpleadosIdOld.getEntradasSalidasMensualesList().remove(entradasSalidasMensuales);
                entradaEmpleadosIdOld = em.merge(entradaEmpleadosIdOld);
            }
            if (entradaEmpleadosIdNew != null && !entradaEmpleadosIdNew.equals(entradaEmpleadosIdOld)) {
                entradaEmpleadosIdNew.getEntradasSalidasMensualesList().add(entradasSalidasMensuales);
                entradaEmpleadosIdNew = em.merge(entradaEmpleadosIdNew);
            }
            if (salidaEmpleadosIdOld != null && !salidaEmpleadosIdOld.equals(salidaEmpleadosIdNew)) {
                salidaEmpleadosIdOld.getEntradasSalidasMensualesList().remove(entradasSalidasMensuales);
                salidaEmpleadosIdOld = em.merge(salidaEmpleadosIdOld);
            }
            if (salidaEmpleadosIdNew != null && !salidaEmpleadosIdNew.equals(salidaEmpleadosIdOld)) {
                salidaEmpleadosIdNew.getEntradasSalidasMensualesList().add(entradasSalidasMensuales);
                salidaEmpleadosIdNew = em.merge(salidaEmpleadosIdNew);
            }
            if (pagosMensualesIdOld != null && !pagosMensualesIdOld.equals(pagosMensualesIdNew)) {
                pagosMensualesIdOld.getEntradasSalidasMensualesList().remove(entradasSalidasMensuales);
                pagosMensualesIdOld = em.merge(pagosMensualesIdOld);
            }
            if (pagosMensualesIdNew != null && !pagosMensualesIdNew.equals(pagosMensualesIdOld)) {
                pagosMensualesIdNew.getEntradasSalidasMensualesList().add(entradasSalidasMensuales);
                pagosMensualesIdNew = em.merge(pagosMensualesIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = entradasSalidasMensuales.getId();
                if (findEntradasSalidasMensuales(id) == null) {
                    throw new NonexistentEntityException("The entradasSalidasMensuales with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EntradasSalidasMensuales entradasSalidasMensuales;
            try {
                entradasSalidasMensuales = em.getReference(EntradasSalidasMensuales.class, id);
                entradasSalidasMensuales.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The entradasSalidasMensuales with id " + id + " no longer exists.", enfe);
            }
            Empleados entradaEmpleadosId = entradasSalidasMensuales.getEntradaEmpleadosId();
            if (entradaEmpleadosId != null) {
                entradaEmpleadosId.getEntradasSalidasMensualesList().remove(entradasSalidasMensuales);
                entradaEmpleadosId = em.merge(entradaEmpleadosId);
            }
            Empleados salidaEmpleadosId = entradasSalidasMensuales.getSalidaEmpleadosId();
            if (salidaEmpleadosId != null) {
                salidaEmpleadosId.getEntradasSalidasMensualesList().remove(entradasSalidasMensuales);
                salidaEmpleadosId = em.merge(salidaEmpleadosId);
            }
            PagosMensuales pagosMensualesId = entradasSalidasMensuales.getPagosMensualesId();
            if (pagosMensualesId != null) {
                pagosMensualesId.getEntradasSalidasMensualesList().remove(entradasSalidasMensuales);
                pagosMensualesId = em.merge(pagosMensualesId);
            }
            em.remove(entradasSalidasMensuales);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EntradasSalidasMensuales> findEntradasSalidasMensualesEntities() {
        return findEntradasSalidasMensualesEntities(true, -1, -1);
    }

    public List<EntradasSalidasMensuales> findEntradasSalidasMensualesEntities(int maxResults, int firstResult) {
        return findEntradasSalidasMensualesEntities(false, maxResults, firstResult);
    }

    private List<EntradasSalidasMensuales> findEntradasSalidasMensualesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EntradasSalidasMensuales.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public EntradasSalidasMensuales findEntradasSalidasMensuales(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EntradasSalidasMensuales.class, id);
        } finally {
            em.close();
        }
    }

    public int getEntradasSalidasMensualesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EntradasSalidasMensuales> rt = cq.from(EntradasSalidasMensuales.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
