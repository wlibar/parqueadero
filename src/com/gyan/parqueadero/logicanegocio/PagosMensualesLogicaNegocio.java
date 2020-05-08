/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gyan.parqueadero.logicanegocio;

import com.gyan.parqueadero.dao.PagosMensualesJpaController;
import com.gyan.parqueadero.dao.VehiculosJpaController;
import com.gyan.parqueadero.dao.exceptions.NonexistentEntityException;
import com.gyan.parqueadero.dao.exceptions.PreexistingEntityException;
import com.gyan.parqueadero.entidades.Empleados;
import com.gyan.parqueadero.entidades.PagosMensuales;
import com.gyan.parqueadero.entidades.Vehiculos;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * Esta clase contiene la logica de los pagos mensuales
 * @author libardo
 */
public class PagosMensualesLogicaNegocio {
    /**
     * atributo que accede a la fabrica de entidades
     */

    private EntityManagerFactory emf;

    public PagosMensualesLogicaNegocio() {
        emf = Persistence.createEntityManagerFactory("ParqueaderoPU");
    }
    /**
     * Consulta los pagos mensuales de un determinado vehiculo
     * @param placa placa cel vehiculo
     * @return Listad de pagos mensuales
     */
    public List<PagosMensuales> getPagosMensualesPorPlaca(String placa) {
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("PagosMensuales.findByPlaca");
        query.setParameter("placa", placa);
        return query.getResultList();
    }
    /**
     * Obtiene un pago mensual
     * @param id identificador del pago
     * @return  un objeto del pago mensual
     */
    public PagosMensuales getPagoMensual(String id) {
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("PagosMensuales.findById");
        query.setParameter("id", Integer.parseInt(id));
        List<PagosMensuales> resultado = query.getResultList();
        if (resultado.size() == 0) {
            return null;
        } else {
            return resultado.get(0);
        }
    }

    public List<PagosMensuales> getPagosMensuales() {
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("PagosMensuales.findAll");
        query.setMaxResults(100);
        return query.getResultList();
    }

    public int agregarPagoMensual(String placa, String tipoVehiculo, String propietario, Date fechaInicial, Date fechaFin, int descuento, int valorPagar, String observacion, int empleadoEntradaId) throws NonexistentEntityException, Exception {

        //Recuperar el empleado
        Empleados empleado = this.getEmpleado(empleadoEntradaId);

        //Guardar el vehiculo
        Vehiculos vehiculo = getVehiculo(placa);
        if (vehiculo == null) {
            //No existe, se debe crear
            vehiculo = new Vehiculos();
            vehiculo.setPlaca(placa);
            vehiculo.setTipo(tipoVehiculo.substring(0, 1)); // Toma la primera letra: M: Moto, C: Carro
            vehiculo.setPropietario(propietario);
            vehiculo.setFechaCreacion(new Date());

            VehiculosJpaController controllerVehiculo = new VehiculosJpaController(emf);
            try {
                controllerVehiculo.create(vehiculo);
            } catch (PreexistingEntityException ex) {
                Logger.getLogger(EntradasSalidasLogicaNegocio.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(EntradasSalidasLogicaNegocio.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            // Modificar vehiculo
            vehiculo.setTipo(tipoVehiculo.substring(0, 1));
            vehiculo.setPropietario(propietario);
            VehiculosJpaController controllerVehiculo = new VehiculosJpaController(emf);
            controllerVehiculo.edit(vehiculo);
        }

        PagosMensuales pagoMen = new PagosMensuales();
        pagoMen.setPlaca(vehiculo);
        pagoMen.setFechaInicio(fechaInicial);
        pagoMen.setFechaFin(fechaFin);
        pagoMen.setDescuento(descuento);
        pagoMen.setValorPagar(valorPagar);
        pagoMen.setObservacion(observacion);
        pagoMen.setEmpleadosId(empleado);

        //Persistir
        PagosMensualesJpaController controller = new PagosMensualesJpaController(emf);
        return controller.create(pagoMen);
    }

    public void editarPagoMensual(int id, String placa, String tipoVehiculo, String propietario, Date fechaInicial, Date fechaFin, int descuento, int valorPagar, String observacion, int empleadoId) throws Exception {

        //Recuperar el empleado
        Empleados empleado = this.getEmpleado(empleadoId);

        //Guardar el vehiculo
        Vehiculos vehiculo = getVehiculo(placa);
        if (vehiculo == null) {
            //No existe, se debe crear
            vehiculo = new Vehiculos();
            vehiculo.setPlaca(placa);
            vehiculo.setTipo(tipoVehiculo.substring(0, 1)); // Toma la primera letra: M: Moto, C: Carro
            vehiculo.setPropietario(propietario);
            vehiculo.setFechaCreacion(new Date());

            VehiculosJpaController controllerVehiculo = new VehiculosJpaController(emf);
            try {
                controllerVehiculo.create(vehiculo);
            } catch (PreexistingEntityException ex) {
                Logger.getLogger(EntradasSalidasLogicaNegocio.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(EntradasSalidasLogicaNegocio.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            // Modificar vehiculo
            vehiculo.setTipo(tipoVehiculo.substring(0, 1));
            vehiculo.setPropietario(propietario);
            VehiculosJpaController controllerVehiculo = new VehiculosJpaController(emf);
            controllerVehiculo.edit(vehiculo);
        }

        PagosMensualesJpaController controller = new PagosMensualesJpaController(emf);
        PagosMensuales pagoMen = controller.findPagosMensuales(id);

        //Edita los campos que cambiaron
        pagoMen.setEmpleadosId(empleado);
        pagoMen.setPlaca(vehiculo);
        pagoMen.setFechaInicio(fechaInicial);
        pagoMen.setFechaFin(fechaFin);
        pagoMen.setDescuento(descuento);
        pagoMen.setValorPagar(valorPagar);
        pagoMen.setObservacion(observacion);        

        controller.edit(pagoMen);

    }

    /**
     * Recupera un empleado a partir de su id
     *
     * @param id
     * @return Objeto tipo Empleados o null si no lo encuentra
     */
    public Empleados getEmpleado(int id) {
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("Empleados.findById");
        query.setParameter("id", id);
        List<Empleados> resultList = query.getResultList();
        if (!resultList.isEmpty()) {
            return resultList.get(0);
        }
        return null;
    }

    public Vehiculos getVehiculo(String placa) {
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("Vehiculos.findByPlaca");
        query.setParameter("placa", placa);
        List<Vehiculos> resultList = query.getResultList();
        if (!resultList.isEmpty()) {
            return resultList.get(0);
        }
        return null;
    }
}
