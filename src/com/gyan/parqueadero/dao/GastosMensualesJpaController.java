/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gyan.parqueadero.dao;

import com.gyan.parqueadero.dao.exceptions.NonexistentEntityException;
import com.gyan.parqueadero.entidades.GastosMensuales;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.gyan.parqueadero.entidades.TiposGastos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author libardo
 */
public class GastosMensualesJpaController implements Serializable {

    public GastosMensualesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public int create(GastosMensuales gastosMensuales) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TiposGastos tiposGastosId = gastosMensuales.getTiposGastosId();
            if (tiposGastosId != null) {
                tiposGastosId = em.getReference(tiposGastosId.getClass(), tiposGastosId.getId());
                gastosMensuales.setTiposGastosId(tiposGastosId);
            }
            em.persist(gastosMensuales);
            if (tiposGastosId != null) {
                tiposGastosId.getGastosMensualesList().add(gastosMensuales);
                tiposGastosId = em.merge(tiposGastosId);
            }
            em.getTransaction().commit();
            return gastosMensuales.getId();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GastosMensuales gastosMensuales) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GastosMensuales persistentGastosMensuales = em.find(GastosMensuales.class, gastosMensuales.getId());
            TiposGastos tiposGastosIdOld = persistentGastosMensuales.getTiposGastosId();
            TiposGastos tiposGastosIdNew = gastosMensuales.getTiposGastosId();
            if (tiposGastosIdNew != null) {
                tiposGastosIdNew = em.getReference(tiposGastosIdNew.getClass(), tiposGastosIdNew.getId());
                gastosMensuales.setTiposGastosId(tiposGastosIdNew);
            }
            gastosMensuales = em.merge(gastosMensuales);
            if (tiposGastosIdOld != null && !tiposGastosIdOld.equals(tiposGastosIdNew)) {
                tiposGastosIdOld.getGastosMensualesList().remove(gastosMensuales);
                tiposGastosIdOld = em.merge(tiposGastosIdOld);
            }
            if (tiposGastosIdNew != null && !tiposGastosIdNew.equals(tiposGastosIdOld)) {
                tiposGastosIdNew.getGastosMensualesList().add(gastosMensuales);
                tiposGastosIdNew = em.merge(tiposGastosIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = gastosMensuales.getId();
                if (findGastosMensuales(id) == null) {
                    throw new NonexistentEntityException("The gastosMensuales with id " + id + " no longer exists.");
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
            GastosMensuales gastosMensuales;
            try {
                gastosMensuales = em.getReference(GastosMensuales.class, id);
                gastosMensuales.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gastosMensuales with id " + id + " no longer exists.", enfe);
            }
            TiposGastos tiposGastosId = gastosMensuales.getTiposGastosId();
            if (tiposGastosId != null) {
                tiposGastosId.getGastosMensualesList().remove(gastosMensuales);
                tiposGastosId = em.merge(tiposGastosId);
            }
            em.remove(gastosMensuales);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GastosMensuales> findGastosMensualesEntities() {
        return findGastosMensualesEntities(true, -1, -1);
    }

    public List<GastosMensuales> findGastosMensualesEntities(int maxResults, int firstResult) {
        return findGastosMensualesEntities(false, maxResults, firstResult);
    }

    private List<GastosMensuales> findGastosMensualesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GastosMensuales.class));
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

    public GastosMensuales findGastosMensuales(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GastosMensuales.class, id);
        } finally {
            em.close();
        }
    }

    public int getGastosMensualesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GastosMensuales> rt = cq.from(GastosMensuales.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
