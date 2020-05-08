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
import com.gyan.parqueadero.entidades.GastosMensuales;
import com.gyan.parqueadero.entidades.TiposGastos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author libardo
 */
public class TiposGastosJpaController implements Serializable {

    public TiposGastosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TiposGastos tiposGastos) {
        if (tiposGastos.getGastosMensualesList() == null) {
            tiposGastos.setGastosMensualesList(new ArrayList<GastosMensuales>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<GastosMensuales> attachedGastosMensualesList = new ArrayList<GastosMensuales>();
            for (GastosMensuales gastosMensualesListGastosMensualesToAttach : tiposGastos.getGastosMensualesList()) {
                gastosMensualesListGastosMensualesToAttach = em.getReference(gastosMensualesListGastosMensualesToAttach.getClass(), gastosMensualesListGastosMensualesToAttach.getId());
                attachedGastosMensualesList.add(gastosMensualesListGastosMensualesToAttach);
            }
            tiposGastos.setGastosMensualesList(attachedGastosMensualesList);
            em.persist(tiposGastos);
            for (GastosMensuales gastosMensualesListGastosMensuales : tiposGastos.getGastosMensualesList()) {
                TiposGastos oldTiposGastosIdOfGastosMensualesListGastosMensuales = gastosMensualesListGastosMensuales.getTiposGastosId();
                gastosMensualesListGastosMensuales.setTiposGastosId(tiposGastos);
                gastosMensualesListGastosMensuales = em.merge(gastosMensualesListGastosMensuales);
                if (oldTiposGastosIdOfGastosMensualesListGastosMensuales != null) {
                    oldTiposGastosIdOfGastosMensualesListGastosMensuales.getGastosMensualesList().remove(gastosMensualesListGastosMensuales);
                    oldTiposGastosIdOfGastosMensualesListGastosMensuales = em.merge(oldTiposGastosIdOfGastosMensualesListGastosMensuales);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TiposGastos tiposGastos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TiposGastos persistentTiposGastos = em.find(TiposGastos.class, tiposGastos.getId());
            List<GastosMensuales> gastosMensualesListOld = persistentTiposGastos.getGastosMensualesList();
            List<GastosMensuales> gastosMensualesListNew = tiposGastos.getGastosMensualesList();
            List<GastosMensuales> attachedGastosMensualesListNew = new ArrayList<GastosMensuales>();
            for (GastosMensuales gastosMensualesListNewGastosMensualesToAttach : gastosMensualesListNew) {
                gastosMensualesListNewGastosMensualesToAttach = em.getReference(gastosMensualesListNewGastosMensualesToAttach.getClass(), gastosMensualesListNewGastosMensualesToAttach.getId());
                attachedGastosMensualesListNew.add(gastosMensualesListNewGastosMensualesToAttach);
            }
            gastosMensualesListNew = attachedGastosMensualesListNew;
            tiposGastos.setGastosMensualesList(gastosMensualesListNew);
            tiposGastos = em.merge(tiposGastos);
            for (GastosMensuales gastosMensualesListOldGastosMensuales : gastosMensualesListOld) {
                if (!gastosMensualesListNew.contains(gastosMensualesListOldGastosMensuales)) {
                    gastosMensualesListOldGastosMensuales.setTiposGastosId(null);
                    gastosMensualesListOldGastosMensuales = em.merge(gastosMensualesListOldGastosMensuales);
                }
            }
            for (GastosMensuales gastosMensualesListNewGastosMensuales : gastosMensualesListNew) {
                if (!gastosMensualesListOld.contains(gastosMensualesListNewGastosMensuales)) {
                    TiposGastos oldTiposGastosIdOfGastosMensualesListNewGastosMensuales = gastosMensualesListNewGastosMensuales.getTiposGastosId();
                    gastosMensualesListNewGastosMensuales.setTiposGastosId(tiposGastos);
                    gastosMensualesListNewGastosMensuales = em.merge(gastosMensualesListNewGastosMensuales);
                    if (oldTiposGastosIdOfGastosMensualesListNewGastosMensuales != null && !oldTiposGastosIdOfGastosMensualesListNewGastosMensuales.equals(tiposGastos)) {
                        oldTiposGastosIdOfGastosMensualesListNewGastosMensuales.getGastosMensualesList().remove(gastosMensualesListNewGastosMensuales);
                        oldTiposGastosIdOfGastosMensualesListNewGastosMensuales = em.merge(oldTiposGastosIdOfGastosMensualesListNewGastosMensuales);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tiposGastos.getId();
                if (findTiposGastos(id) == null) {
                    throw new NonexistentEntityException("The tiposGastos with id " + id + " no longer exists.");
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
            TiposGastos tiposGastos;
            try {
                tiposGastos = em.getReference(TiposGastos.class, id);
                tiposGastos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tiposGastos with id " + id + " no longer exists.", enfe);
            }
            List<GastosMensuales> gastosMensualesList = tiposGastos.getGastosMensualesList();
            for (GastosMensuales gastosMensualesListGastosMensuales : gastosMensualesList) {
                gastosMensualesListGastosMensuales.setTiposGastosId(null);
                gastosMensualesListGastosMensuales = em.merge(gastosMensualesListGastosMensuales);
            }
            em.remove(tiposGastos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TiposGastos> findTiposGastosEntities() {
        return findTiposGastosEntities(true, -1, -1);
    }

    public List<TiposGastos> findTiposGastosEntities(int maxResults, int firstResult) {
        return findTiposGastosEntities(false, maxResults, firstResult);
    }

    private List<TiposGastos> findTiposGastosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TiposGastos.class));
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

    public TiposGastos findTiposGastos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TiposGastos.class, id);
        } finally {
            em.close();
        }
    }

    public int getTiposGastosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TiposGastos> rt = cq.from(TiposGastos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
