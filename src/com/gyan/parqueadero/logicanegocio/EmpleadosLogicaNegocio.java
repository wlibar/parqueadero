/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gyan.parqueadero.logicanegocio;

import com.gyan.parqueadero.dao.EmpleadosJpaController;
import com.gyan.parqueadero.dao.exceptions.NonexistentEntityException;
import com.gyan.parqueadero.entidades.Empleados;
import com.gyan.parqueadero.utilidades.Utilidades;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author libardo
 */
public class EmpleadosLogicaNegocio {

    private EntityManagerFactory emf;

    public EmpleadosLogicaNegocio() {
        emf = Persistence.createEntityManagerFactory("ParqueaderoPU");
    }

    public List<Empleados> getEmpleados() {
        EntityManager em = emf.createEntityManager();
        EmpleadosJpaController controller = new EmpleadosJpaController(emf);
        em.close();
        return controller.findEmpleadosEntities();
    }

    public boolean esEmpleadoValido(int id, String contrasenia) {
        EntityManager em = emf.createEntityManager();
        EmpleadosJpaController controller = new EmpleadosJpaController(emf);
        Empleados empleado = controller.findEmpleados(id);
        if (empleado.getContrasenia().equals(contrasenia) && empleado.getEstado().equals("A")) {
            Utilidades.empleadoSesion = empleado;
            return true;
        } else {
            return false;
        }
    }

    public void grabar(String nombres, String apellidos, String documento, String celular, String contrasena, String email, String cargo, String estado) throws Exception {
        EntityManager em = emf.createEntityManager();
        EmpleadosJpaController controller = new EmpleadosJpaController(emf);
        Empleados inst = new Empleados();
        inst.setNombres(nombres);
        inst.setApellidos(apellidos);
        inst.setDocumento(documento);
        inst.setCelular(celular);
        inst.setContrasenia(contrasena);
        inst.setEmail(email);
        inst.setCargo(cargo);
        inst.setEstado(estado);

        controller.create(inst);
        em.close();
    }

    public void editar(int id, String nombres, String apellidos, String documento, String celular, String contrasena, String email, String cargo, String estado) throws NonexistentEntityException, Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ParqueaderoPU");
        EntityManager em = emf.createEntityManager();

        EmpleadosJpaController controller = new EmpleadosJpaController(emf);
        Empleados inst = controller.findEmpleados(id);
        inst.setNombres(nombres);
        inst.setApellidos(apellidos);
        inst.setDocumento(documento);
        inst.setCelular(celular);
        if (!contrasena.equals("")) {
            inst.setContrasenia(contrasena);
        }
        inst.setEmail(email);
        inst.setCargo(cargo);
        inst.setEstado(estado);

        controller.edit(inst);
        em.close();
    }

    public void eliminar(int id) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ParqueaderoPU");
        EntityManager em = emf.createEntityManager();

        EmpleadosJpaController controller = new EmpleadosJpaController(emf);

        controller.destroy(id);
        em.close();
    }
}
