/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gyan.parqueadero.dao;

import com.gyan.parqueadero.dao.exceptions.IllegalOrphanException;
import com.gyan.parqueadero.dao.exceptions.NonexistentEntityException;
import com.gyan.parqueadero.dao.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.gyan.parqueadero.entidades.PagosMensuales;
import java.util.ArrayList;
import java.util.List;
import com.gyan.parqueadero.entidades.EntradasSalidas;
import com.gyan.parqueadero.entidades.Vehiculos;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author libar
 */
public class VehiculosJpaController implements Serializable {

    public VehiculosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vehiculos vehiculos) throws PreexistingEntityException, Exception {
        if (vehiculos.getPagosMensualesList() == null) {
            vehiculos.setPagosMensualesList(new ArrayList<PagosMensuales>());
        }
        if (vehiculos.getEntradasSalidasList() == null) {
            vehiculos.setEntradasSalidasList(new ArrayList<EntradasSalidas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<PagosMensuales> attachedPagosMensualesList = new ArrayList<PagosMensuales>();
            for (PagosMensuales pagosMensualesListPagosMensualesToAttach : vehiculos.getPagosMensualesList()) {
                pagosMensualesListPagosMensualesToAttach = em.getReference(pagosMensualesListPagosMensualesToAttach.getClass(), pagosMensualesListPagosMensualesToAttach.getId());
                attachedPagosMensualesList.add(pagosMensualesListPagosMensualesToAttach);
            }
            vehiculos.setPagosMensualesList(attachedPagosMensualesList);
            List<EntradasSalidas> attachedEntradasSalidasList = new ArrayList<EntradasSalidas>();
            for (EntradasSalidas entradasSalidasListEntradasSalidasToAttach : vehiculos.getEntradasSalidasList()) {
                entradasSalidasListEntradasSalidasToAttach = em.getReference(entradasSalidasListEntradasSalidasToAttach.getClass(), entradasSalidasListEntradasSalidasToAttach.getId());
                attachedEntradasSalidasList.add(entradasSalidasListEntradasSalidasToAttach);
            }
            vehiculos.setEntradasSalidasList(attachedEntradasSalidasList);
            em.persist(vehiculos);
            for (PagosMensuales pagosMensualesListPagosMensuales : vehiculos.getPagosMensualesList()) {
                Vehiculos oldPlacaOfPagosMensualesListPagosMensuales = pagosMensualesListPagosMensuales.getPlaca();
                pagosMensualesListPagosMensuales.setPlaca(vehiculos);
                pagosMensualesListPagosMensuales = em.merge(pagosMensualesListPagosMensuales);
                if (oldPlacaOfPagosMensualesListPagosMensuales != null) {
                    oldPlacaOfPagosMensualesListPagosMensuales.getPagosMensualesList().remove(pagosMensualesListPagosMensuales);
                    oldPlacaOfPagosMensualesListPagosMensuales = em.merge(oldPlacaOfPagosMensualesListPagosMensuales);
                }
            }
            for (EntradasSalidas entradasSalidasListEntradasSalidas : vehiculos.getEntradasSalidasList()) {
                Vehiculos oldPlacaOfEntradasSalidasListEntradasSalidas = entradasSalidasListEntradasSalidas.getPlaca();
                entradasSalidasListEntradasSalidas.setPlaca(vehiculos);
                entradasSalidasListEntradasSalidas = em.merge(entradasSalidasListEntradasSalidas);
                if (oldPlacaOfEntradasSalidasListEntradasSalidas != null) {
                    oldPlacaOfEntradasSalidasListEntradasSalidas.getEntradasSalidasList().remove(entradasSalidasListEntradasSalidas);
                    oldPlacaOfEntradasSalidasListEntradasSalidas = em.merge(oldPlacaOfEntradasSalidasListEntradasSalidas);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVehiculos(vehiculos.getPlaca()) != null) {
                throw new PreexistingEntityException("Vehiculos " + vehiculos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vehiculos vehiculos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vehiculos persistentVehiculos = em.find(Vehiculos.class, vehiculos.getPlaca());
            List<PagosMensuales> pagosMensualesListOld = persistentVehiculos.getPagosMensualesList();
            List<PagosMensuales> pagosMensualesListNew = vehiculos.getPagosMensualesList();
            List<EntradasSalidas> entradasSalidasListOld = persistentVehiculos.getEntradasSalidasList();
            List<EntradasSalidas> entradasSalidasListNew = vehiculos.getEntradasSalidasList();
            List<String> illegalOrphanMessages = null;
            for (PagosMensuales pagosMensualesListOldPagosMensuales : pagosMensualesListOld) {
                if (!pagosMensualesListNew.contains(pagosMensualesListOldPagosMensuales)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PagosMensuales " + pagosMensualesListOldPagosMensuales + " since its placa field is not nullable.");
                }
            }
            for (EntradasSalidas entradasSalidasListOldEntradasSalidas : entradasSalidasListOld) {
                if (!entradasSalidasListNew.contains(entradasSalidasListOldEntradasSalidas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EntradasSalidas " + entradasSalidasListOldEntradasSalidas + " since its placa field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<PagosMensuales> attachedPagosMensualesListNew = new ArrayList<PagosMensuales>();
            for (PagosMensuales pagosMensualesListNewPagosMensualesToAttach : pagosMensualesListNew) {
                pagosMensualesListNewPagosMensualesToAttach = em.getReference(pagosMensualesListNewPagosMensualesToAttach.getClass(), pagosMensualesListNewPagosMensualesToAttach.getId());
                attachedPagosMensualesListNew.add(pagosMensualesListNewPagosMensualesToAttach);
            }
            pagosMensualesListNew = attachedPagosMensualesListNew;
            vehiculos.setPagosMensualesList(pagosMensualesListNew);
            List<EntradasSalidas> attachedEntradasSalidasListNew = new ArrayList<EntradasSalidas>();
            for (EntradasSalidas entradasSalidasListNewEntradasSalidasToAttach : entradasSalidasListNew) {
                entradasSalidasListNewEntradasSalidasToAttach = em.getReference(entradasSalidasListNewEntradasSalidasToAttach.getClass(), entradasSalidasListNewEntradasSalidasToAttach.getId());
                attachedEntradasSalidasListNew.add(entradasSalidasListNewEntradasSalidasToAttach);
            }
            entradasSalidasListNew = attachedEntradasSalidasListNew;
            vehiculos.setEntradasSalidasList(entradasSalidasListNew);
            vehiculos = em.merge(vehiculos);
            for (PagosMensuales pagosMensualesListNewPagosMensuales : pagosMensualesListNew) {
                if (!pagosMensualesListOld.contains(pagosMensualesListNewPagosMensuales)) {
                    Vehiculos oldPlacaOfPagosMensualesListNewPagosMensuales = pagosMensualesListNewPagosMensuales.getPlaca();
                    pagosMensualesListNewPagosMensuales.setPlaca(vehiculos);
                    pagosMensualesListNewPagosMensuales = em.merge(pagosMensualesListNewPagosMensuales);
                    if (oldPlacaOfPagosMensualesListNewPagosMensuales != null && !oldPlacaOfPagosMensualesListNewPagosMensuales.equals(vehiculos)) {
                        oldPlacaOfPagosMensualesListNewPagosMensuales.getPagosMensualesList().remove(pagosMensualesListNewPagosMensuales);
                        oldPlacaOfPagosMensualesListNewPagosMensuales = em.merge(oldPlacaOfPagosMensualesListNewPagosMensuales);
                    }
                }
            }
            for (EntradasSalidas entradasSalidasListNewEntradasSalidas : entradasSalidasListNew) {
                if (!entradasSalidasListOld.contains(entradasSalidasListNewEntradasSalidas)) {
                    Vehiculos oldPlacaOfEntradasSalidasListNewEntradasSalidas = entradasSalidasListNewEntradasSalidas.getPlaca();
                    entradasSalidasListNewEntradasSalidas.setPlaca(vehiculos);
                    entradasSalidasListNewEntradasSalidas = em.merge(entradasSalidasListNewEntradasSalidas);
                    if (oldPlacaOfEntradasSalidasListNewEntradasSalidas != null && !oldPlacaOfEntradasSalidasListNewEntradasSalidas.equals(vehiculos)) {
                        oldPlacaOfEntradasSalidasListNewEntradasSalidas.getEntradasSalidasList().remove(entradasSalidasListNewEntradasSalidas);
                        oldPlacaOfEntradasSalidasListNewEntradasSalidas = em.merge(oldPlacaOfEntradasSalidasListNewEntradasSalidas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = vehiculos.getPlaca();
                if (findVehiculos(id) == null) {
                    throw new NonexistentEntityException("The vehiculos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vehiculos vehiculos;
            try {
                vehiculos = em.getReference(Vehiculos.class, id);
                vehiculos.getPlaca();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vehiculos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<PagosMensuales> pagosMensualesListOrphanCheck = vehiculos.getPagosMensualesList();
            for (PagosMensuales pagosMensualesListOrphanCheckPagosMensuales : pagosMensualesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Vehiculos (" + vehiculos + ") cannot be destroyed since the PagosMensuales " + pagosMensualesListOrphanCheckPagosMensuales + " in its pagosMensualesList field has a non-nullable placa field.");
            }
            List<EntradasSalidas> entradasSalidasListOrphanCheck = vehiculos.getEntradasSalidasList();
            for (EntradasSalidas entradasSalidasListOrphanCheckEntradasSalidas : entradasSalidasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Vehiculos (" + vehiculos + ") cannot be destroyed since the EntradasSalidas " + entradasSalidasListOrphanCheckEntradasSalidas + " in its entradasSalidasList field has a non-nullable placa field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(vehiculos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vehiculos> findVehiculosEntities() {
        return findVehiculosEntities(true, -1, -1);
    }

    public List<Vehiculos> findVehiculosEntities(int maxResults, int firstResult) {
        return findVehiculosEntities(false, maxResults, firstResult);
    }

    private List<Vehiculos> findVehiculosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vehiculos.class));
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

    public Vehiculos findVehiculos(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vehiculos.class, id);
        } finally {
            em.close();
        }
    }

    public int getVehiculosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vehiculos> rt = cq.from(Vehiculos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
