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
import com.gyan.parqueadero.entidades.Vehiculos;
import com.gyan.parqueadero.entidades.EntradasSalidasMensuales;
import com.gyan.parqueadero.entidades.PagosMensuales;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author libar
 */
public class PagosMensualesJpaController implements Serializable {

    public PagosMensualesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public int create(PagosMensuales pagosMensuales) {
        if (pagosMensuales.getEntradasSalidasMensualesList() == null) {
            pagosMensuales.setEntradasSalidasMensualesList(new ArrayList<EntradasSalidasMensuales>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleados empleadosId = pagosMensuales.getEmpleadosId();
            if (empleadosId != null) {
                empleadosId = em.getReference(empleadosId.getClass(), empleadosId.getId());
                pagosMensuales.setEmpleadosId(empleadosId);
            }
            Vehiculos placa = pagosMensuales.getPlaca();
            if (placa != null) {
                placa = em.getReference(placa.getClass(), placa.getPlaca());
                pagosMensuales.setPlaca(placa);
            }
            List<EntradasSalidasMensuales> attachedEntradasSalidasMensualesList = new ArrayList<EntradasSalidasMensuales>();
            for (EntradasSalidasMensuales entradasSalidasMensualesListEntradasSalidasMensualesToAttach : pagosMensuales.getEntradasSalidasMensualesList()) {
                entradasSalidasMensualesListEntradasSalidasMensualesToAttach = em.getReference(entradasSalidasMensualesListEntradasSalidasMensualesToAttach.getClass(), entradasSalidasMensualesListEntradasSalidasMensualesToAttach.getId());
                attachedEntradasSalidasMensualesList.add(entradasSalidasMensualesListEntradasSalidasMensualesToAttach);
            }
            pagosMensuales.setEntradasSalidasMensualesList(attachedEntradasSalidasMensualesList);
            em.persist(pagosMensuales);
            if (empleadosId != null) {
                empleadosId.getPagosMensualesList().add(pagosMensuales);
                empleadosId = em.merge(empleadosId);
            }
            if (placa != null) {
                placa.getPagosMensualesList().add(pagosMensuales);
                placa = em.merge(placa);
            }
            for (EntradasSalidasMensuales entradasSalidasMensualesListEntradasSalidasMensuales : pagosMensuales.getEntradasSalidasMensualesList()) {
                PagosMensuales oldPagosMensualesIdOfEntradasSalidasMensualesListEntradasSalidasMensuales = entradasSalidasMensualesListEntradasSalidasMensuales.getPagosMensualesId();
                entradasSalidasMensualesListEntradasSalidasMensuales.setPagosMensualesId(pagosMensuales);
                entradasSalidasMensualesListEntradasSalidasMensuales = em.merge(entradasSalidasMensualesListEntradasSalidasMensuales);
                if (oldPagosMensualesIdOfEntradasSalidasMensualesListEntradasSalidasMensuales != null) {
                    oldPagosMensualesIdOfEntradasSalidasMensualesListEntradasSalidasMensuales.getEntradasSalidasMensualesList().remove(entradasSalidasMensualesListEntradasSalidasMensuales);
                    oldPagosMensualesIdOfEntradasSalidasMensualesListEntradasSalidasMensuales = em.merge(oldPagosMensualesIdOfEntradasSalidasMensualesListEntradasSalidasMensuales);
                }
            }
            em.getTransaction().commit();
            //System.out.println("Id: " + pagosMensuales.getId());
            return pagosMensuales.getId();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PagosMensuales pagosMensuales) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PagosMensuales persistentPagosMensuales = em.find(PagosMensuales.class, pagosMensuales.getId());
            Empleados empleadosIdOld = persistentPagosMensuales.getEmpleadosId();
            Empleados empleadosIdNew = pagosMensuales.getEmpleadosId();
            Vehiculos placaOld = persistentPagosMensuales.getPlaca();
            Vehiculos placaNew = pagosMensuales.getPlaca();
            List<EntradasSalidasMensuales> entradasSalidasMensualesListOld = persistentPagosMensuales.getEntradasSalidasMensualesList();
            List<EntradasSalidasMensuales> entradasSalidasMensualesListNew = pagosMensuales.getEntradasSalidasMensualesList();
            if (empleadosIdNew != null) {
                empleadosIdNew = em.getReference(empleadosIdNew.getClass(), empleadosIdNew.getId());
                pagosMensuales.setEmpleadosId(empleadosIdNew);
            }
            if (placaNew != null) {
                placaNew = em.getReference(placaNew.getClass(), placaNew.getPlaca());
                pagosMensuales.setPlaca(placaNew);
            }
            List<EntradasSalidasMensuales> attachedEntradasSalidasMensualesListNew = new ArrayList<EntradasSalidasMensuales>();
            for (EntradasSalidasMensuales entradasSalidasMensualesListNewEntradasSalidasMensualesToAttach : entradasSalidasMensualesListNew) {
                entradasSalidasMensualesListNewEntradasSalidasMensualesToAttach = em.getReference(entradasSalidasMensualesListNewEntradasSalidasMensualesToAttach.getClass(), entradasSalidasMensualesListNewEntradasSalidasMensualesToAttach.getId());
                attachedEntradasSalidasMensualesListNew.add(entradasSalidasMensualesListNewEntradasSalidasMensualesToAttach);
            }
            entradasSalidasMensualesListNew = attachedEntradasSalidasMensualesListNew;
            pagosMensuales.setEntradasSalidasMensualesList(entradasSalidasMensualesListNew);
            pagosMensuales = em.merge(pagosMensuales);
            if (empleadosIdOld != null && !empleadosIdOld.equals(empleadosIdNew)) {
                empleadosIdOld.getPagosMensualesList().remove(pagosMensuales);
                empleadosIdOld = em.merge(empleadosIdOld);
            }
            if (empleadosIdNew != null && !empleadosIdNew.equals(empleadosIdOld)) {
                empleadosIdNew.getPagosMensualesList().add(pagosMensuales);
                empleadosIdNew = em.merge(empleadosIdNew);
            }
            if (placaOld != null && !placaOld.equals(placaNew)) {
                placaOld.getPagosMensualesList().remove(pagosMensuales);
                placaOld = em.merge(placaOld);
            }
            if (placaNew != null && !placaNew.equals(placaOld)) {
                placaNew.getPagosMensualesList().add(pagosMensuales);
                placaNew = em.merge(placaNew);
            }
            for (EntradasSalidasMensuales entradasSalidasMensualesListOldEntradasSalidasMensuales : entradasSalidasMensualesListOld) {
                if (!entradasSalidasMensualesListNew.contains(entradasSalidasMensualesListOldEntradasSalidasMensuales)) {
                    entradasSalidasMensualesListOldEntradasSalidasMensuales.setPagosMensualesId(null);
                    entradasSalidasMensualesListOldEntradasSalidasMensuales = em.merge(entradasSalidasMensualesListOldEntradasSalidasMensuales);
                }
            }
            for (EntradasSalidasMensuales entradasSalidasMensualesListNewEntradasSalidasMensuales : entradasSalidasMensualesListNew) {
                if (!entradasSalidasMensualesListOld.contains(entradasSalidasMensualesListNewEntradasSalidasMensuales)) {
                    PagosMensuales oldPagosMensualesIdOfEntradasSalidasMensualesListNewEntradasSalidasMensuales = entradasSalidasMensualesListNewEntradasSalidasMensuales.getPagosMensualesId();
                    entradasSalidasMensualesListNewEntradasSalidasMensuales.setPagosMensualesId(pagosMensuales);
                    entradasSalidasMensualesListNewEntradasSalidasMensuales = em.merge(entradasSalidasMensualesListNewEntradasSalidasMensuales);
                    if (oldPagosMensualesIdOfEntradasSalidasMensualesListNewEntradasSalidasMensuales != null && !oldPagosMensualesIdOfEntradasSalidasMensualesListNewEntradasSalidasMensuales.equals(pagosMensuales)) {
                        oldPagosMensualesIdOfEntradasSalidasMensualesListNewEntradasSalidasMensuales.getEntradasSalidasMensualesList().remove(entradasSalidasMensualesListNewEntradasSalidasMensuales);
                        oldPagosMensualesIdOfEntradasSalidasMensualesListNewEntradasSalidasMensuales = em.merge(oldPagosMensualesIdOfEntradasSalidasMensualesListNewEntradasSalidasMensuales);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pagosMensuales.getId();
                if (findPagosMensuales(id) == null) {
                    throw new NonexistentEntityException("The pagosMensuales with id " + id + " no longer exists.");
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
            PagosMensuales pagosMensuales;
            try {
                pagosMensuales = em.getReference(PagosMensuales.class, id);
                pagosMensuales.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pagosMensuales with id " + id + " no longer exists.", enfe);
            }
            Empleados empleadosId = pagosMensuales.getEmpleadosId();
            if (empleadosId != null) {
                empleadosId.getPagosMensualesList().remove(pagosMensuales);
                empleadosId = em.merge(empleadosId);
            }
            Vehiculos placa = pagosMensuales.getPlaca();
            if (placa != null) {
                placa.getPagosMensualesList().remove(pagosMensuales);
                placa = em.merge(placa);
            }
            List<EntradasSalidasMensuales> entradasSalidasMensualesList = pagosMensuales.getEntradasSalidasMensualesList();
            for (EntradasSalidasMensuales entradasSalidasMensualesListEntradasSalidasMensuales : entradasSalidasMensualesList) {
                entradasSalidasMensualesListEntradasSalidasMensuales.setPagosMensualesId(null);
                entradasSalidasMensualesListEntradasSalidasMensuales = em.merge(entradasSalidasMensualesListEntradasSalidasMensuales);
            }
            em.remove(pagosMensuales);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PagosMensuales> findPagosMensualesEntities() {
        return findPagosMensualesEntities(true, -1, -1);
    }

    public List<PagosMensuales> findPagosMensualesEntities(int maxResults, int firstResult) {
        return findPagosMensualesEntities(false, maxResults, firstResult);
    }

    private List<PagosMensuales> findPagosMensualesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PagosMensuales.class));
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

    public PagosMensuales findPagosMensuales(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PagosMensuales.class, id);
        } finally {
            em.close();
        }
    }

    public int getPagosMensualesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PagosMensuales> rt = cq.from(PagosMensuales.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
