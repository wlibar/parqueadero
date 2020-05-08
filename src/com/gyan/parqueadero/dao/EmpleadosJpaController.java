/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gyan.parqueadero.dao;

import com.gyan.parqueadero.dao.exceptions.IllegalOrphanException;
import com.gyan.parqueadero.dao.exceptions.NonexistentEntityException;
import com.gyan.parqueadero.entidades.Empleados;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.gyan.parqueadero.entidades.PagosMensuales;
import java.util.ArrayList;
import java.util.List;
import com.gyan.parqueadero.entidades.EntradasSalidas;
import com.gyan.parqueadero.entidades.EntradasSalidasMensuales;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author libar
 */
public class EmpleadosJpaController implements Serializable {

    public EmpleadosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empleados empleados) {
        if (empleados.getPagosMensualesList() == null) {
            empleados.setPagosMensualesList(new ArrayList<PagosMensuales>());
        }
        if (empleados.getEntradasSalidasList() == null) {
            empleados.setEntradasSalidasList(new ArrayList<EntradasSalidas>());
        }
        if (empleados.getEntradasSalidasList1() == null) {
            empleados.setEntradasSalidasList1(new ArrayList<EntradasSalidas>());
        }
        if (empleados.getEntradasSalidasMensualesList() == null) {
            empleados.setEntradasSalidasMensualesList(new ArrayList<EntradasSalidasMensuales>());
        }
        if (empleados.getEntradasSalidasMensualesList1() == null) {
            empleados.setEntradasSalidasMensualesList1(new ArrayList<EntradasSalidasMensuales>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<PagosMensuales> attachedPagosMensualesList = new ArrayList<PagosMensuales>();
            for (PagosMensuales pagosMensualesListPagosMensualesToAttach : empleados.getPagosMensualesList()) {
                pagosMensualesListPagosMensualesToAttach = em.getReference(pagosMensualesListPagosMensualesToAttach.getClass(), pagosMensualesListPagosMensualesToAttach.getId());
                attachedPagosMensualesList.add(pagosMensualesListPagosMensualesToAttach);
            }
            empleados.setPagosMensualesList(attachedPagosMensualesList);
            List<EntradasSalidas> attachedEntradasSalidasList = new ArrayList<EntradasSalidas>();
            for (EntradasSalidas entradasSalidasListEntradasSalidasToAttach : empleados.getEntradasSalidasList()) {
                entradasSalidasListEntradasSalidasToAttach = em.getReference(entradasSalidasListEntradasSalidasToAttach.getClass(), entradasSalidasListEntradasSalidasToAttach.getId());
                attachedEntradasSalidasList.add(entradasSalidasListEntradasSalidasToAttach);
            }
            empleados.setEntradasSalidasList(attachedEntradasSalidasList);
            List<EntradasSalidas> attachedEntradasSalidasList1 = new ArrayList<EntradasSalidas>();
            for (EntradasSalidas entradasSalidasList1EntradasSalidasToAttach : empleados.getEntradasSalidasList1()) {
                entradasSalidasList1EntradasSalidasToAttach = em.getReference(entradasSalidasList1EntradasSalidasToAttach.getClass(), entradasSalidasList1EntradasSalidasToAttach.getId());
                attachedEntradasSalidasList1.add(entradasSalidasList1EntradasSalidasToAttach);
            }
            empleados.setEntradasSalidasList1(attachedEntradasSalidasList1);
            List<EntradasSalidasMensuales> attachedEntradasSalidasMensualesList = new ArrayList<EntradasSalidasMensuales>();
            for (EntradasSalidasMensuales entradasSalidasMensualesListEntradasSalidasMensualesToAttach : empleados.getEntradasSalidasMensualesList()) {
                entradasSalidasMensualesListEntradasSalidasMensualesToAttach = em.getReference(entradasSalidasMensualesListEntradasSalidasMensualesToAttach.getClass(), entradasSalidasMensualesListEntradasSalidasMensualesToAttach.getId());
                attachedEntradasSalidasMensualesList.add(entradasSalidasMensualesListEntradasSalidasMensualesToAttach);
            }
            empleados.setEntradasSalidasMensualesList(attachedEntradasSalidasMensualesList);
            List<EntradasSalidasMensuales> attachedEntradasSalidasMensualesList1 = new ArrayList<EntradasSalidasMensuales>();
            for (EntradasSalidasMensuales entradasSalidasMensualesList1EntradasSalidasMensualesToAttach : empleados.getEntradasSalidasMensualesList1()) {
                entradasSalidasMensualesList1EntradasSalidasMensualesToAttach = em.getReference(entradasSalidasMensualesList1EntradasSalidasMensualesToAttach.getClass(), entradasSalidasMensualesList1EntradasSalidasMensualesToAttach.getId());
                attachedEntradasSalidasMensualesList1.add(entradasSalidasMensualesList1EntradasSalidasMensualesToAttach);
            }
            empleados.setEntradasSalidasMensualesList1(attachedEntradasSalidasMensualesList1);
            em.persist(empleados);
            for (PagosMensuales pagosMensualesListPagosMensuales : empleados.getPagosMensualesList()) {
                Empleados oldEmpleadosIdOfPagosMensualesListPagosMensuales = pagosMensualesListPagosMensuales.getEmpleadosId();
                pagosMensualesListPagosMensuales.setEmpleadosId(empleados);
                pagosMensualesListPagosMensuales = em.merge(pagosMensualesListPagosMensuales);
                if (oldEmpleadosIdOfPagosMensualesListPagosMensuales != null) {
                    oldEmpleadosIdOfPagosMensualesListPagosMensuales.getPagosMensualesList().remove(pagosMensualesListPagosMensuales);
                    oldEmpleadosIdOfPagosMensualesListPagosMensuales = em.merge(oldEmpleadosIdOfPagosMensualesListPagosMensuales);
                }
            }
            for (EntradasSalidas entradasSalidasListEntradasSalidas : empleados.getEntradasSalidasList()) {
                Empleados oldEntradaEmpleadosIdOfEntradasSalidasListEntradasSalidas = entradasSalidasListEntradasSalidas.getEntradaEmpleadosId();
                entradasSalidasListEntradasSalidas.setEntradaEmpleadosId(empleados);
                entradasSalidasListEntradasSalidas = em.merge(entradasSalidasListEntradasSalidas);
                if (oldEntradaEmpleadosIdOfEntradasSalidasListEntradasSalidas != null) {
                    oldEntradaEmpleadosIdOfEntradasSalidasListEntradasSalidas.getEntradasSalidasList().remove(entradasSalidasListEntradasSalidas);
                    oldEntradaEmpleadosIdOfEntradasSalidasListEntradasSalidas = em.merge(oldEntradaEmpleadosIdOfEntradasSalidasListEntradasSalidas);
                }
            }
            for (EntradasSalidas entradasSalidasList1EntradasSalidas : empleados.getEntradasSalidasList1()) {
                Empleados oldSalidaEmpleadosIdOfEntradasSalidasList1EntradasSalidas = entradasSalidasList1EntradasSalidas.getSalidaEmpleadosId();
                entradasSalidasList1EntradasSalidas.setSalidaEmpleadosId(empleados);
                entradasSalidasList1EntradasSalidas = em.merge(entradasSalidasList1EntradasSalidas);
                if (oldSalidaEmpleadosIdOfEntradasSalidasList1EntradasSalidas != null) {
                    oldSalidaEmpleadosIdOfEntradasSalidasList1EntradasSalidas.getEntradasSalidasList1().remove(entradasSalidasList1EntradasSalidas);
                    oldSalidaEmpleadosIdOfEntradasSalidasList1EntradasSalidas = em.merge(oldSalidaEmpleadosIdOfEntradasSalidasList1EntradasSalidas);
                }
            }
            for (EntradasSalidasMensuales entradasSalidasMensualesListEntradasSalidasMensuales : empleados.getEntradasSalidasMensualesList()) {
                Empleados oldEntradaEmpleadosIdOfEntradasSalidasMensualesListEntradasSalidasMensuales = entradasSalidasMensualesListEntradasSalidasMensuales.getEntradaEmpleadosId();
                entradasSalidasMensualesListEntradasSalidasMensuales.setEntradaEmpleadosId(empleados);
                entradasSalidasMensualesListEntradasSalidasMensuales = em.merge(entradasSalidasMensualesListEntradasSalidasMensuales);
                if (oldEntradaEmpleadosIdOfEntradasSalidasMensualesListEntradasSalidasMensuales != null) {
                    oldEntradaEmpleadosIdOfEntradasSalidasMensualesListEntradasSalidasMensuales.getEntradasSalidasMensualesList().remove(entradasSalidasMensualesListEntradasSalidasMensuales);
                    oldEntradaEmpleadosIdOfEntradasSalidasMensualesListEntradasSalidasMensuales = em.merge(oldEntradaEmpleadosIdOfEntradasSalidasMensualesListEntradasSalidasMensuales);
                }
            }
            for (EntradasSalidasMensuales entradasSalidasMensualesList1EntradasSalidasMensuales : empleados.getEntradasSalidasMensualesList1()) {
                Empleados oldSalidaEmpleadosIdOfEntradasSalidasMensualesList1EntradasSalidasMensuales = entradasSalidasMensualesList1EntradasSalidasMensuales.getSalidaEmpleadosId();
                entradasSalidasMensualesList1EntradasSalidasMensuales.setSalidaEmpleadosId(empleados);
                entradasSalidasMensualesList1EntradasSalidasMensuales = em.merge(entradasSalidasMensualesList1EntradasSalidasMensuales);
                if (oldSalidaEmpleadosIdOfEntradasSalidasMensualesList1EntradasSalidasMensuales != null) {
                    oldSalidaEmpleadosIdOfEntradasSalidasMensualesList1EntradasSalidasMensuales.getEntradasSalidasMensualesList1().remove(entradasSalidasMensualesList1EntradasSalidasMensuales);
                    oldSalidaEmpleadosIdOfEntradasSalidasMensualesList1EntradasSalidasMensuales = em.merge(oldSalidaEmpleadosIdOfEntradasSalidasMensualesList1EntradasSalidasMensuales);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empleados empleados) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleados persistentEmpleados = em.find(Empleados.class, empleados.getId());
            List<PagosMensuales> pagosMensualesListOld = persistentEmpleados.getPagosMensualesList();
            List<PagosMensuales> pagosMensualesListNew = empleados.getPagosMensualesList();
            List<EntradasSalidas> entradasSalidasListOld = persistentEmpleados.getEntradasSalidasList();
            List<EntradasSalidas> entradasSalidasListNew = empleados.getEntradasSalidasList();
            List<EntradasSalidas> entradasSalidasList1Old = persistentEmpleados.getEntradasSalidasList1();
            List<EntradasSalidas> entradasSalidasList1New = empleados.getEntradasSalidasList1();
            List<EntradasSalidasMensuales> entradasSalidasMensualesListOld = persistentEmpleados.getEntradasSalidasMensualesList();
            List<EntradasSalidasMensuales> entradasSalidasMensualesListNew = empleados.getEntradasSalidasMensualesList();
            List<EntradasSalidasMensuales> entradasSalidasMensualesList1Old = persistentEmpleados.getEntradasSalidasMensualesList1();
            List<EntradasSalidasMensuales> entradasSalidasMensualesList1New = empleados.getEntradasSalidasMensualesList1();
            List<String> illegalOrphanMessages = null;
            for (EntradasSalidas entradasSalidasListOldEntradasSalidas : entradasSalidasListOld) {
                if (!entradasSalidasListNew.contains(entradasSalidasListOldEntradasSalidas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EntradasSalidas " + entradasSalidasListOldEntradasSalidas + " since its entradaEmpleadosId field is not nullable.");
                }
            }
            for (EntradasSalidas entradasSalidasList1OldEntradasSalidas : entradasSalidasList1Old) {
                if (!entradasSalidasList1New.contains(entradasSalidasList1OldEntradasSalidas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EntradasSalidas " + entradasSalidasList1OldEntradasSalidas + " since its salidaEmpleadosId field is not nullable.");
                }
            }
            for (EntradasSalidasMensuales entradasSalidasMensualesListOldEntradasSalidasMensuales : entradasSalidasMensualesListOld) {
                if (!entradasSalidasMensualesListNew.contains(entradasSalidasMensualesListOldEntradasSalidasMensuales)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EntradasSalidasMensuales " + entradasSalidasMensualesListOldEntradasSalidasMensuales + " since its entradaEmpleadosId field is not nullable.");
                }
            }
            for (EntradasSalidasMensuales entradasSalidasMensualesList1OldEntradasSalidasMensuales : entradasSalidasMensualesList1Old) {
                if (!entradasSalidasMensualesList1New.contains(entradasSalidasMensualesList1OldEntradasSalidasMensuales)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EntradasSalidasMensuales " + entradasSalidasMensualesList1OldEntradasSalidasMensuales + " since its salidaEmpleadosId field is not nullable.");
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
            empleados.setPagosMensualesList(pagosMensualesListNew);
            List<EntradasSalidas> attachedEntradasSalidasListNew = new ArrayList<EntradasSalidas>();
            for (EntradasSalidas entradasSalidasListNewEntradasSalidasToAttach : entradasSalidasListNew) {
                entradasSalidasListNewEntradasSalidasToAttach = em.getReference(entradasSalidasListNewEntradasSalidasToAttach.getClass(), entradasSalidasListNewEntradasSalidasToAttach.getId());
                attachedEntradasSalidasListNew.add(entradasSalidasListNewEntradasSalidasToAttach);
            }
            entradasSalidasListNew = attachedEntradasSalidasListNew;
            empleados.setEntradasSalidasList(entradasSalidasListNew);
            List<EntradasSalidas> attachedEntradasSalidasList1New = new ArrayList<EntradasSalidas>();
            for (EntradasSalidas entradasSalidasList1NewEntradasSalidasToAttach : entradasSalidasList1New) {
                entradasSalidasList1NewEntradasSalidasToAttach = em.getReference(entradasSalidasList1NewEntradasSalidasToAttach.getClass(), entradasSalidasList1NewEntradasSalidasToAttach.getId());
                attachedEntradasSalidasList1New.add(entradasSalidasList1NewEntradasSalidasToAttach);
            }
            entradasSalidasList1New = attachedEntradasSalidasList1New;
            empleados.setEntradasSalidasList1(entradasSalidasList1New);
            List<EntradasSalidasMensuales> attachedEntradasSalidasMensualesListNew = new ArrayList<EntradasSalidasMensuales>();
            for (EntradasSalidasMensuales entradasSalidasMensualesListNewEntradasSalidasMensualesToAttach : entradasSalidasMensualesListNew) {
                entradasSalidasMensualesListNewEntradasSalidasMensualesToAttach = em.getReference(entradasSalidasMensualesListNewEntradasSalidasMensualesToAttach.getClass(), entradasSalidasMensualesListNewEntradasSalidasMensualesToAttach.getId());
                attachedEntradasSalidasMensualesListNew.add(entradasSalidasMensualesListNewEntradasSalidasMensualesToAttach);
            }
            entradasSalidasMensualesListNew = attachedEntradasSalidasMensualesListNew;
            empleados.setEntradasSalidasMensualesList(entradasSalidasMensualesListNew);
            List<EntradasSalidasMensuales> attachedEntradasSalidasMensualesList1New = new ArrayList<EntradasSalidasMensuales>();
            for (EntradasSalidasMensuales entradasSalidasMensualesList1NewEntradasSalidasMensualesToAttach : entradasSalidasMensualesList1New) {
                entradasSalidasMensualesList1NewEntradasSalidasMensualesToAttach = em.getReference(entradasSalidasMensualesList1NewEntradasSalidasMensualesToAttach.getClass(), entradasSalidasMensualesList1NewEntradasSalidasMensualesToAttach.getId());
                attachedEntradasSalidasMensualesList1New.add(entradasSalidasMensualesList1NewEntradasSalidasMensualesToAttach);
            }
            entradasSalidasMensualesList1New = attachedEntradasSalidasMensualesList1New;
            empleados.setEntradasSalidasMensualesList1(entradasSalidasMensualesList1New);
            empleados = em.merge(empleados);
            for (PagosMensuales pagosMensualesListOldPagosMensuales : pagosMensualesListOld) {
                if (!pagosMensualesListNew.contains(pagosMensualesListOldPagosMensuales)) {
                    pagosMensualesListOldPagosMensuales.setEmpleadosId(null);
                    pagosMensualesListOldPagosMensuales = em.merge(pagosMensualesListOldPagosMensuales);
                }
            }
            for (PagosMensuales pagosMensualesListNewPagosMensuales : pagosMensualesListNew) {
                if (!pagosMensualesListOld.contains(pagosMensualesListNewPagosMensuales)) {
                    Empleados oldEmpleadosIdOfPagosMensualesListNewPagosMensuales = pagosMensualesListNewPagosMensuales.getEmpleadosId();
                    pagosMensualesListNewPagosMensuales.setEmpleadosId(empleados);
                    pagosMensualesListNewPagosMensuales = em.merge(pagosMensualesListNewPagosMensuales);
                    if (oldEmpleadosIdOfPagosMensualesListNewPagosMensuales != null && !oldEmpleadosIdOfPagosMensualesListNewPagosMensuales.equals(empleados)) {
                        oldEmpleadosIdOfPagosMensualesListNewPagosMensuales.getPagosMensualesList().remove(pagosMensualesListNewPagosMensuales);
                        oldEmpleadosIdOfPagosMensualesListNewPagosMensuales = em.merge(oldEmpleadosIdOfPagosMensualesListNewPagosMensuales);
                    }
                }
            }
            for (EntradasSalidas entradasSalidasListNewEntradasSalidas : entradasSalidasListNew) {
                if (!entradasSalidasListOld.contains(entradasSalidasListNewEntradasSalidas)) {
                    Empleados oldEntradaEmpleadosIdOfEntradasSalidasListNewEntradasSalidas = entradasSalidasListNewEntradasSalidas.getEntradaEmpleadosId();
                    entradasSalidasListNewEntradasSalidas.setEntradaEmpleadosId(empleados);
                    entradasSalidasListNewEntradasSalidas = em.merge(entradasSalidasListNewEntradasSalidas);
                    if (oldEntradaEmpleadosIdOfEntradasSalidasListNewEntradasSalidas != null && !oldEntradaEmpleadosIdOfEntradasSalidasListNewEntradasSalidas.equals(empleados)) {
                        oldEntradaEmpleadosIdOfEntradasSalidasListNewEntradasSalidas.getEntradasSalidasList().remove(entradasSalidasListNewEntradasSalidas);
                        oldEntradaEmpleadosIdOfEntradasSalidasListNewEntradasSalidas = em.merge(oldEntradaEmpleadosIdOfEntradasSalidasListNewEntradasSalidas);
                    }
                }
            }
            for (EntradasSalidas entradasSalidasList1NewEntradasSalidas : entradasSalidasList1New) {
                if (!entradasSalidasList1Old.contains(entradasSalidasList1NewEntradasSalidas)) {
                    Empleados oldSalidaEmpleadosIdOfEntradasSalidasList1NewEntradasSalidas = entradasSalidasList1NewEntradasSalidas.getSalidaEmpleadosId();
                    entradasSalidasList1NewEntradasSalidas.setSalidaEmpleadosId(empleados);
                    entradasSalidasList1NewEntradasSalidas = em.merge(entradasSalidasList1NewEntradasSalidas);
                    if (oldSalidaEmpleadosIdOfEntradasSalidasList1NewEntradasSalidas != null && !oldSalidaEmpleadosIdOfEntradasSalidasList1NewEntradasSalidas.equals(empleados)) {
                        oldSalidaEmpleadosIdOfEntradasSalidasList1NewEntradasSalidas.getEntradasSalidasList1().remove(entradasSalidasList1NewEntradasSalidas);
                        oldSalidaEmpleadosIdOfEntradasSalidasList1NewEntradasSalidas = em.merge(oldSalidaEmpleadosIdOfEntradasSalidasList1NewEntradasSalidas);
                    }
                }
            }
            for (EntradasSalidasMensuales entradasSalidasMensualesListNewEntradasSalidasMensuales : entradasSalidasMensualesListNew) {
                if (!entradasSalidasMensualesListOld.contains(entradasSalidasMensualesListNewEntradasSalidasMensuales)) {
                    Empleados oldEntradaEmpleadosIdOfEntradasSalidasMensualesListNewEntradasSalidasMensuales = entradasSalidasMensualesListNewEntradasSalidasMensuales.getEntradaEmpleadosId();
                    entradasSalidasMensualesListNewEntradasSalidasMensuales.setEntradaEmpleadosId(empleados);
                    entradasSalidasMensualesListNewEntradasSalidasMensuales = em.merge(entradasSalidasMensualesListNewEntradasSalidasMensuales);
                    if (oldEntradaEmpleadosIdOfEntradasSalidasMensualesListNewEntradasSalidasMensuales != null && !oldEntradaEmpleadosIdOfEntradasSalidasMensualesListNewEntradasSalidasMensuales.equals(empleados)) {
                        oldEntradaEmpleadosIdOfEntradasSalidasMensualesListNewEntradasSalidasMensuales.getEntradasSalidasMensualesList().remove(entradasSalidasMensualesListNewEntradasSalidasMensuales);
                        oldEntradaEmpleadosIdOfEntradasSalidasMensualesListNewEntradasSalidasMensuales = em.merge(oldEntradaEmpleadosIdOfEntradasSalidasMensualesListNewEntradasSalidasMensuales);
                    }
                }
            }
            for (EntradasSalidasMensuales entradasSalidasMensualesList1NewEntradasSalidasMensuales : entradasSalidasMensualesList1New) {
                if (!entradasSalidasMensualesList1Old.contains(entradasSalidasMensualesList1NewEntradasSalidasMensuales)) {
                    Empleados oldSalidaEmpleadosIdOfEntradasSalidasMensualesList1NewEntradasSalidasMensuales = entradasSalidasMensualesList1NewEntradasSalidasMensuales.getSalidaEmpleadosId();
                    entradasSalidasMensualesList1NewEntradasSalidasMensuales.setSalidaEmpleadosId(empleados);
                    entradasSalidasMensualesList1NewEntradasSalidasMensuales = em.merge(entradasSalidasMensualesList1NewEntradasSalidasMensuales);
                    if (oldSalidaEmpleadosIdOfEntradasSalidasMensualesList1NewEntradasSalidasMensuales != null && !oldSalidaEmpleadosIdOfEntradasSalidasMensualesList1NewEntradasSalidasMensuales.equals(empleados)) {
                        oldSalidaEmpleadosIdOfEntradasSalidasMensualesList1NewEntradasSalidasMensuales.getEntradasSalidasMensualesList1().remove(entradasSalidasMensualesList1NewEntradasSalidasMensuales);
                        oldSalidaEmpleadosIdOfEntradasSalidasMensualesList1NewEntradasSalidasMensuales = em.merge(oldSalidaEmpleadosIdOfEntradasSalidasMensualesList1NewEntradasSalidasMensuales);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = empleados.getId();
                if (findEmpleados(id) == null) {
                    throw new NonexistentEntityException("The empleados with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleados empleados;
            try {
                empleados = em.getReference(Empleados.class, id);
                empleados.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleados with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<EntradasSalidas> entradasSalidasListOrphanCheck = empleados.getEntradasSalidasList();
            for (EntradasSalidas entradasSalidasListOrphanCheckEntradasSalidas : entradasSalidasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empleados (" + empleados + ") cannot be destroyed since the EntradasSalidas " + entradasSalidasListOrphanCheckEntradasSalidas + " in its entradasSalidasList field has a non-nullable entradaEmpleadosId field.");
            }
            List<EntradasSalidas> entradasSalidasList1OrphanCheck = empleados.getEntradasSalidasList1();
            for (EntradasSalidas entradasSalidasList1OrphanCheckEntradasSalidas : entradasSalidasList1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empleados (" + empleados + ") cannot be destroyed since the EntradasSalidas " + entradasSalidasList1OrphanCheckEntradasSalidas + " in its entradasSalidasList1 field has a non-nullable salidaEmpleadosId field.");
            }
            List<EntradasSalidasMensuales> entradasSalidasMensualesListOrphanCheck = empleados.getEntradasSalidasMensualesList();
            for (EntradasSalidasMensuales entradasSalidasMensualesListOrphanCheckEntradasSalidasMensuales : entradasSalidasMensualesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empleados (" + empleados + ") cannot be destroyed since the EntradasSalidasMensuales " + entradasSalidasMensualesListOrphanCheckEntradasSalidasMensuales + " in its entradasSalidasMensualesList field has a non-nullable entradaEmpleadosId field.");
            }
            List<EntradasSalidasMensuales> entradasSalidasMensualesList1OrphanCheck = empleados.getEntradasSalidasMensualesList1();
            for (EntradasSalidasMensuales entradasSalidasMensualesList1OrphanCheckEntradasSalidasMensuales : entradasSalidasMensualesList1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empleados (" + empleados + ") cannot be destroyed since the EntradasSalidasMensuales " + entradasSalidasMensualesList1OrphanCheckEntradasSalidasMensuales + " in its entradasSalidasMensualesList1 field has a non-nullable salidaEmpleadosId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<PagosMensuales> pagosMensualesList = empleados.getPagosMensualesList();
            for (PagosMensuales pagosMensualesListPagosMensuales : pagosMensualesList) {
                pagosMensualesListPagosMensuales.setEmpleadosId(null);
                pagosMensualesListPagosMensuales = em.merge(pagosMensualesListPagosMensuales);
            }
            em.remove(empleados);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empleados> findEmpleadosEntities() {
        return findEmpleadosEntities(true, -1, -1);
    }

    public List<Empleados> findEmpleadosEntities(int maxResults, int firstResult) {
        return findEmpleadosEntities(false, maxResults, firstResult);
    }

    private List<Empleados> findEmpleadosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleados.class));
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

    public Empleados findEmpleados(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleados.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleados> rt = cq.from(Empleados.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
