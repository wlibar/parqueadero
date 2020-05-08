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
import com.gyan.parqueadero.entidades.EntradasSalidas;
import com.gyan.parqueadero.entidades.Vehiculos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author libar
 */
public class EntradasSalidasJpaController implements Serializable {

    public EntradasSalidasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EntradasSalidas entradasSalidas) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleados entradaEmpleadosId = entradasSalidas.getEntradaEmpleadosId();
            if (entradaEmpleadosId != null) {
                entradaEmpleadosId = em.getReference(entradaEmpleadosId.getClass(), entradaEmpleadosId.getId());
                entradasSalidas.setEntradaEmpleadosId(entradaEmpleadosId);
            }
            Empleados salidaEmpleadosId = entradasSalidas.getSalidaEmpleadosId();
            if (salidaEmpleadosId != null) {
                salidaEmpleadosId = em.getReference(salidaEmpleadosId.getClass(), salidaEmpleadosId.getId());
                entradasSalidas.setSalidaEmpleadosId(salidaEmpleadosId);
            }
            Vehiculos placa = entradasSalidas.getPlaca();
            if (placa != null) {
                placa = em.getReference(placa.getClass(), placa.getPlaca());
                entradasSalidas.setPlaca(placa);
            }
            em.persist(entradasSalidas);
            if (entradaEmpleadosId != null) {
                entradaEmpleadosId.getEntradasSalidasList().add(entradasSalidas);
                entradaEmpleadosId = em.merge(entradaEmpleadosId);
            }
            if (salidaEmpleadosId != null) {
                salidaEmpleadosId.getEntradasSalidasList().add(entradasSalidas);
                salidaEmpleadosId = em.merge(salidaEmpleadosId);
            }
            if (placa != null) {
                placa.getEntradasSalidasList().add(entradasSalidas);
                placa = em.merge(placa);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EntradasSalidas entradasSalidas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EntradasSalidas persistentEntradasSalidas = em.find(EntradasSalidas.class, entradasSalidas.getId());
            Empleados entradaEmpleadosIdOld = persistentEntradasSalidas.getEntradaEmpleadosId();
            Empleados entradaEmpleadosIdNew = entradasSalidas.getEntradaEmpleadosId();
            Empleados salidaEmpleadosIdOld = persistentEntradasSalidas.getSalidaEmpleadosId();
            Empleados salidaEmpleadosIdNew = entradasSalidas.getSalidaEmpleadosId();
            Vehiculos placaOld = persistentEntradasSalidas.getPlaca();
            Vehiculos placaNew = entradasSalidas.getPlaca();
            if (entradaEmpleadosIdNew != null) {
                entradaEmpleadosIdNew = em.getReference(entradaEmpleadosIdNew.getClass(), entradaEmpleadosIdNew.getId());
                entradasSalidas.setEntradaEmpleadosId(entradaEmpleadosIdNew);
            }
            if (salidaEmpleadosIdNew != null) {
                salidaEmpleadosIdNew = em.getReference(salidaEmpleadosIdNew.getClass(), salidaEmpleadosIdNew.getId());
                entradasSalidas.setSalidaEmpleadosId(salidaEmpleadosIdNew);
            }
            if (placaNew != null) {
                placaNew = em.getReference(placaNew.getClass(), placaNew.getPlaca());
                entradasSalidas.setPlaca(placaNew);
            }
            entradasSalidas = em.merge(entradasSalidas);
            if (entradaEmpleadosIdOld != null && !entradaEmpleadosIdOld.equals(entradaEmpleadosIdNew)) {
                entradaEmpleadosIdOld.getEntradasSalidasList().remove(entradasSalidas);
                entradaEmpleadosIdOld = em.merge(entradaEmpleadosIdOld);
            }
            if (entradaEmpleadosIdNew != null && !entradaEmpleadosIdNew.equals(entradaEmpleadosIdOld)) {
                entradaEmpleadosIdNew.getEntradasSalidasList().add(entradasSalidas);
                entradaEmpleadosIdNew = em.merge(entradaEmpleadosIdNew);
            }
            if (salidaEmpleadosIdOld != null && !salidaEmpleadosIdOld.equals(salidaEmpleadosIdNew)) {
                salidaEmpleadosIdOld.getEntradasSalidasList().remove(entradasSalidas);
                salidaEmpleadosIdOld = em.merge(salidaEmpleadosIdOld);
            }
            if (salidaEmpleadosIdNew != null && !salidaEmpleadosIdNew.equals(salidaEmpleadosIdOld)) {
                salidaEmpleadosIdNew.getEntradasSalidasList().add(entradasSalidas);
                salidaEmpleadosIdNew = em.merge(salidaEmpleadosIdNew);
            }
            if (placaOld != null && !placaOld.equals(placaNew)) {
                placaOld.getEntradasSalidasList().remove(entradasSalidas);
                placaOld = em.merge(placaOld);
            }
            if (placaNew != null && !placaNew.equals(placaOld)) {
                placaNew.getEntradasSalidasList().add(entradasSalidas);
                placaNew = em.merge(placaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = entradasSalidas.getId();
                if (findEntradasSalidas(id) == null) {
                    throw new NonexistentEntityException("The entradasSalidas with id " + id + " no longer exists.");
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
            EntradasSalidas entradasSalidas;
            try {
                entradasSalidas = em.getReference(EntradasSalidas.class, id);
                entradasSalidas.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The entradasSalidas with id " + id + " no longer exists.", enfe);
            }
            Empleados entradaEmpleadosId = entradasSalidas.getEntradaEmpleadosId();
            if (entradaEmpleadosId != null) {
                entradaEmpleadosId.getEntradasSalidasList().remove(entradasSalidas);
                entradaEmpleadosId = em.merge(entradaEmpleadosId);
            }
            Empleados salidaEmpleadosId = entradasSalidas.getSalidaEmpleadosId();
            if (salidaEmpleadosId != null) {
                salidaEmpleadosId.getEntradasSalidasList().remove(entradasSalidas);
                salidaEmpleadosId = em.merge(salidaEmpleadosId);
            }
            Vehiculos placa = entradasSalidas.getPlaca();
            if (placa != null) {
                placa.getEntradasSalidasList().remove(entradasSalidas);
                placa = em.merge(placa);
            }
            em.remove(entradasSalidas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EntradasSalidas> findEntradasSalidasEntities() {
        return findEntradasSalidasEntities(true, -1, -1);
    }

    public List<EntradasSalidas> findEntradasSalidasEntities(int maxResults, int firstResult) {
        return findEntradasSalidasEntities(false, maxResults, firstResult);
    }

    private List<EntradasSalidas> findEntradasSalidasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EntradasSalidas.class));
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

    public EntradasSalidas findEntradasSalidas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EntradasSalidas.class, id);
        } finally {
            em.close();
        }
    }

    public int getEntradasSalidasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EntradasSalidas> rt = cq.from(EntradasSalidas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
