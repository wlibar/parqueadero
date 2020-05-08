package com.gyan.parqueadero.logicanegocio;

import com.gyan.parqueadero.dao.EntradasSalidasJpaController;
import com.gyan.parqueadero.dao.EntradasSalidasMensualesJpaController;
import com.gyan.parqueadero.dao.PagosMensualesJpaController;
import com.gyan.parqueadero.dao.VehiculosJpaController;
import com.gyan.parqueadero.dao.exceptions.NonexistentEntityException;
import com.gyan.parqueadero.dao.exceptions.PreexistingEntityException;
import com.gyan.parqueadero.entidades.Empleados;
import com.gyan.parqueadero.entidades.EntradasSalidas;
import com.gyan.parqueadero.entidades.EntradasSalidasMensuales;
import com.gyan.parqueadero.entidades.PagosMensuales;
import com.gyan.parqueadero.entidades.Vehiculos;
import com.gyan.parqueadero.utilidades.Utilidades;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author libardo
 */
public class EntradasSalidasLogicaNegocio {

    private EntityManagerFactory emf;

    public int getTarifaMes(String tipoVehiculo) {
        return 25000;
    }

    public EntradasSalidasLogicaNegocio() {
        emf = Persistence.createEntityManagerFactory("ParqueaderoPU");
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

    public EntradasSalidas getEntradaSalida(String dato, String tipo) {
        EntityManager em = emf.createEntityManager();
        Query query;
        if (tipo.equals("PLACA")) {
            query = em.createNamedQuery("EntradasSalidas.findByPlaca");
            query.setParameter("placa", dato);
        } else {
            //Es por código de barras
            query = em.createNamedQuery("EntradasSalidas.findByFichaNro");
            query.setParameter("fichaNro", dato);
        }

        List<EntradasSalidas> resultList = query.getResultList();
        if (!resultList.isEmpty()) {
            return resultList.get(0);
        } else {
            return null;
        }
    }

    public EntradasSalidasMensuales getEntradaSalidaMensual(String dato, String tipo) {
        EntityManager em = emf.createEntityManager();
        Query query;
        if (tipo.equals("PLACA")) {
            query = em.createNamedQuery("EntradasSalidasMensuales.findByPlaca");
            query.setParameter("placa", dato);
        } else {
            query = em.createNamedQuery("EntradasSalidasMensuales.findByFichaNro");
            query.setParameter("fichaNro", dato);
        }
        List<EntradasSalidasMensuales> resultList = query.getResultList();
        if (!resultList.isEmpty()) {
            return resultList.get(0);
        } else {
            return null;
        }
    }

    /**
     * Buscar el último registro en pagos_mensuales con esa placa
     *
     * @param placa placa del vehículo
     * @return Objeto PagosMensuales
     */
    public PagosMensuales getPagoMensual(String placa) {
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("PagosMensuales.findByPlaca");
        query.setParameter("placa", placa);
        query.setMaxResults(1);
        List<PagosMensuales> resultList = query.getResultList();
        if (!resultList.isEmpty()) {
            return resultList.get(0);
        } else {
            return null;
        }
    }

    /**
     * Busca todas las entreadasSalidas en un rango de fechas
     *
     * @param fecha1 fecha |
     * @param fecha2 fecha 2
     * @return listado de entradas y salidas
     */
    public List<EntradasSalidas> entradasSalidasPorFecha(Date fecha1, Date fecha2) {
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("EntradasSalidas.findByFecha");
        query.setParameter("fecha1", fecha1);
        query.setParameter("fecha2", fecha2);
        return query.getResultList();
    }

    /**
     * Reporte de entradas mensuales por fecha
     *
     * @param fecha1
     * @param fecha2
     * @return
     */
    public List<PagosMensuales> pagosMensualesPorFecha(Date fecha1, Date fecha2) {
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("PagosMensuales.findByFecha");
        query.setParameter("fecha1", fecha1);
        query.setParameter("fecha2", fecha2);
        return query.getResultList();
    }

    /**
     * Busca todas las entreadasSalidas en un rango de fechas
     *
     * @param fecha1 fecha |
     * @param fecha2 fecha 2
     * @return listado de entradas y salidas
     */
    public List<EntradasSalidas> entradasSalidasPorEmpleadoFecha(int empleadoId, Date fecha1, Date fecha2) {
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("EntradasSalidas.findByEmpleadoFecha");
        query.setParameter("empleadoId", empleadoId);
        query.setParameter("fecha1", fecha1);
        query.setParameter("fecha2", fecha2);
        return query.getResultList();
    }

    public List<PagosMensuales> pagosMensualesPorEmpleadoFecha(int empleadoId, Date fecha1, Date fecha2) {
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("PagosMensuales.findByEmpleadoFecha");
        query.setParameter("empleadoId", empleadoId);
        query.setParameter("fecha1", fecha1);
        query.setParameter("fecha2", fecha2);
        return query.getResultList();
    }

    public List<EntradasSalidas> consultaAbierta(String sql, int max) throws Exception {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery(sql, EntradasSalidas.class);
        query.setMaxResults(max);
        return query.getResultList();
    }

    public List<EntradasSalidas> vehiculosPorLiquidar() {
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("EntradasSalidas.findVehiculosPorLiquidar");
        return query.getResultList();
    }

    public List<EntradasSalidasMensuales> vehiculosPorLiquidarMensuales() {
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("EntradasSalidasMensuales.findVehiculosPorLiquidar");
        return query.getResultList();
    }

    public Empleados getEntradaSalida(int id) {
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("Empleados.findById");
        query.setParameter("id", id);
        List<Empleados> resultList = query.getResultList();
        if (!resultList.isEmpty()) {
            return resultList.get(0);
        } else {
            return null;
        }
    }

    /**
     * Graba una entrada cuando es por horas
     *
     * @param placa
     * @param tipoVehiculo
     * @param propietario
     * @param fechaHoraEntrada
     * @param descuento
     * @param valorPagar
     * @param observacion
     * @param empleadoEntradaId
     * @param cascosNro
     * @param casilleroNro
     * @param dejaLlaves
     * @param fichaNro
     * @throws com.gyan.parqueadero.dao.exceptions.NonexistentEntityException
     */
    public void grabarEntradaHoras(String placa, String tipoVehiculo, String propietario, Date fechaHoraEntrada, int descuento, int valorPagar, String observacion, int empleadoEntradaId, String cascosNro, String casilleroNro, boolean dejaLlaves, String fichaNro) throws NonexistentEntityException, Exception {

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

        //Crear el objeto EntradaSalida
        EntradasSalidas entradaSalida = new EntradasSalidas();
        entradaSalida.setPlaca(vehiculo);
        entradaSalida.setFechaHoraEntrada(fechaHoraEntrada);
        entradaSalida.setFechaHoraSalida(null);
        entradaSalida.setDescuento(descuento);
        entradaSalida.setValorPagar(valorPagar);
        entradaSalida.setDejaLlaves(dejaLlaves);
        entradaSalida.setFichaNro(fichaNro);
        entradaSalida.setObservacion(observacion);
        entradaSalida.setEntradaEmpleadosId(empleado);
        entradaSalida.setSalidaEmpleadosId(null);
        entradaSalida.setCascosNro(cascosNro);
        entradaSalida.setCasilleroNro(casilleroNro);
        entradaSalida.setExtravioTarjeta(false);

        //Persistir
        EntradasSalidasJpaController controller = new EntradasSalidasJpaController(emf);
        controller.create(entradaSalida);
    }

    /**
     * Graba un registro completo de Entrada y Salida, proviene del caso con
     * puchito
     *
     * @param placa
     * @param fechaHoraEntrada
     * @param descuento
     * @param valorPagar
     * @param observacion
     * @param empleadoEntradaId
     * @throws NonexistentEntityException
     * @throws Exception
     */
    public void grabarPuchito(String placa, Date fechaHoraEntrada, Date fechaHoraSalida, int descuento, int valorPagar, String observacion, int empleadoEntradaId) throws NonexistentEntityException, Exception {

        //Recuperar el empleado
        Empleados empleado = this.getEmpleado(empleadoEntradaId);

        //Guardar el vehiculo
        Vehiculos vehiculo = getVehiculo(placa);

        //Crear el objeto EntradaSalida
        EntradasSalidas entradaSalida = new EntradasSalidas();
        entradaSalida.setPlaca(vehiculo);
        entradaSalida.setFechaHoraEntrada(fechaHoraEntrada);
        entradaSalida.setFechaHoraSalida(fechaHoraSalida);
        entradaSalida.setDescuento(descuento);
        entradaSalida.setValorPagar(valorPagar);
        entradaSalida.setDejaLlaves(false);
        entradaSalida.setFichaNro("");
        entradaSalida.setObservacion(observacion);
        entradaSalida.setEntradaEmpleadosId(empleado);
        entradaSalida.setSalidaEmpleadosId(empleado);

        //Persistir
        EntradasSalidasJpaController controller = new EntradasSalidasJpaController(emf);
        controller.create(entradaSalida);
    }

    /**
     * Graba en la bd una entrada cuando ya tiene un pago mensual
     *
     * @param placa
     * @param tipoVehiculo
     * @param propietario
     * @param fechaHoraEntrada
     * @param observacion
     * @param empleadoEntradaId
     * @param dejaCasco
     * @param dejaLlaves
     * @param ficha
     */
    public void grabarEntradaMensual(PagosMensuales objPagoMes, String placa, String tipoVehiculo, String propietario, Date fechaHoraEntrada, String observacion, int empleadoEntradaId, boolean dejaCasco, boolean dejaLlaves, String ficha) throws NonexistentEntityException, Exception {

        //Recuperar el empleado
        Empleados empleado = this.getEmpleado(empleadoEntradaId);

        //Guardar el vehiculo
        //Crear el objeto Vehiculo
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

        //Crear el objeto EntradaSalida
        EntradasSalidasMensuales entradaSalidaMensual = new EntradasSalidasMensuales();
        //entradaSalida.setId(2);
        entradaSalidaMensual.setFechaHoraEntrada(fechaHoraEntrada);
        entradaSalidaMensual.setFechaHoraSalida(null);
        entradaSalidaMensual.setDejaLlaves(dejaLlaves);
        entradaSalidaMensual.setFichaNro(ficha);
        entradaSalidaMensual.setObservacion(observacion);
        entradaSalidaMensual.setEntradaEmpleadosId(empleado);
        entradaSalidaMensual.setSalidaEmpleadosId(null);
        entradaSalidaMensual.setPagosMensualesId(objPagoMes);

        //Persistir
        EntradasSalidasMensualesJpaController controller = new EntradasSalidasMensualesJpaController(emf);
        controller.create(entradaSalidaMensual);
    }

    /**
     * Graba la entrada del pago mensual en la bd. Se requiere grabar en dos
     * tablas: PagosMensuales y EntradasSalidasMensuales FALTA TESTING
     *
     * @param placa
     * @param tipoVehiculo
     * @param propietario
     * @param fechaHoraEntrada
     * @param descuento
     * @param valorPagar
     * @param observacion
     * @param empleadoEntradaId
     * @param dejaCasco
     * @param dejaLlaves
     * @param ficha
     * @throws NonexistentEntityException
     * @throws Exception
     */
    public void grabarEntradaPagoMensual(String placa, String tipoVehiculo, String propietario, Date fechaHoraEntrada, int descuento, int valorPagar, String observacion, int empleadoEntradaId, boolean dejaCasco, boolean dejaLlaves, String ficha) throws NonexistentEntityException, Exception {

        //Recuperar el empleado
        Empleados empleado = this.getEmpleado(empleadoEntradaId);

        //Guardar el vehiculo
        //Crear el objeto Vehiculo
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

        //Crear el objeto PagosMensuales
        PagosMensuales pm = new PagosMensuales();
        pm.setPlaca(vehiculo);
        pm.setFechaInicio(fechaHoraEntrada);
        Date fechaHoraFin = Utilidades.sumarMesAFecha(fechaHoraEntrada);

        pm.setFechaFin(fechaHoraFin);
        pm.setDescuento(descuento);
        pm.setValorPagar(valorPagar);
        pm.setEmpleadosId(empleado);
        PagosMensualesJpaController controllerPagMens = new PagosMensualesJpaController(emf);
        controllerPagMens.create(pm);

        //Crear el objeto EntradasSalidasMensuales
        //
        EntradasSalidasMensuales entradaSalidaMen = new EntradasSalidasMensuales();
        //entradaSalida.setId(2);
        entradaSalidaMen.setPagosMensualesId(pm);

        entradaSalidaMen.setFechaHoraEntrada(fechaHoraEntrada);
        entradaSalidaMen.setFechaHoraSalida(null);
        entradaSalidaMen.setDejaLlaves(dejaLlaves);
        entradaSalidaMen.setFichaNro(ficha);
        entradaSalidaMen.setObservacion(observacion);
        entradaSalidaMen.setEntradaEmpleadosId(empleado);
        //entradaSalida.setSalidaEmpleadosId(empleado);

        //Persistir
        EntradasSalidasMensualesJpaController controller = new EntradasSalidasMensualesJpaController(emf);
        controller.create(entradaSalidaMen);
    }

    /**
     * Graba la salida en la bd cuando el pago es por Horas
     *
     * @param entradaSalida
     * @param fechaHoraSalida
     * @param descuento
     * @param valorPagar
     * @param observacion
     * @param empleadoSalidaId
     * @throws java.lang.Exception
     */
    public void grabarSalidaHoras(EntradasSalidas entradaSalida, Date fechaHoraSalida, int descuento, int valorPagar, String observacion, boolean extravioTarjeta, int empleadoSalidaId) throws Exception {

        //Empleado
        Empleados empleado = getEmpleado(empleadoSalidaId);

        EntradasSalidasJpaController controller = new EntradasSalidasJpaController(emf);

        //Edita los campos que cambiaron
        entradaSalida.setSalidaEmpleadosId(empleado);
        entradaSalida.setObservacion(observacion);
        entradaSalida.setDescuento(descuento);
        entradaSalida.setFechaHoraSalida(fechaHoraSalida);
        entradaSalida.setValorPagar(valorPagar);
        entradaSalida.setExtravioTarjeta(extravioTarjeta);

        controller.edit(entradaSalida);

    }

    /**
     * Graba en la bd la salida cuando hay un pago mensual Solo se debe
     * actualizar la fecha de salida y el empleado de salida
     *
     * @param objEntSalMensual
     * @param fechaHoraSalida
     * @param observacion
     * @param empleadoSalidaId
     * @throws Exception
     */
    public void grabarSalidaMensual(EntradasSalidasMensuales objEntSalMensual, Date fechaHoraSalida, String observacion, int empleadoSalidaId) throws Exception {

        //Empleado
        Empleados empleado = getEmpleado(empleadoSalidaId);

        EntradasSalidasMensualesJpaController controller = new EntradasSalidasMensualesJpaController(emf);

        //Edita los campos que cambiaron
        objEntSalMensual.setSalidaEmpleadosId(empleado);
        objEntSalMensual.setObservacion(observacion);
        objEntSalMensual.setFechaHoraSalida(fechaHoraSalida);

        controller.edit(objEntSalMensual);

    }

    /**
     * Algoritmo que calcula el valor a apgar cuando es una entrada por HORAS Es
     * un algoritmo complejo
     *
     * @param tipoVehiculo
     * @param fechaHoraEntrada
     * @param fechaHoraSalida
     * @return Valor a pagar por el tiempo que estuvo el vehiculo en el
     * parqueadero
     * @throws ParseException
     */
    public int calcularValorPagarSalidaHoras(String tipoVehiculo, Date fechaHoraEntrada, Date fechaHoraSalida) throws ParseException {
        //Cargar Datos del archivo
        Properties prop = new Properties();
        InputStream is = null;

        try {
            is = new FileInputStream("./config.properties");
            prop.load(is);
        } catch (IOException e) {
            Utilidades.mensajeError("Error al cargar el archivo de configuración de variables\n" + e.getMessage(), "Atención");
            return 0;
        }

        //Tarifas de carro
        int carrosTarifaDiurna = Integer.parseInt(prop.getProperty("carrosTarifaDiurna"));//Turno 1: 3500
        int carrosTarifaTarde = Integer.parseInt(prop.getProperty("carrosTarifaTarde"));//Turno 2: 3500
        int carrosTarifaNocturna = Integer.parseInt(prop.getProperty("carrosTarifaNocturna"));//Turno 3: 7000

        //Tarifas de moto
        int motosTarifaDiurna = Integer.parseInt(prop.getProperty("motosTarifaDiurna")); //Turno 1: 2000
        int motosTarifaTarde = Integer.parseInt(prop.getProperty("motosTarifaTarde")); //Turno 2: 2000
        int motosTarifaNocturna = Integer.parseInt(prop.getProperty("motosTarifaNocturna")); // Turno 3: 2000

        //Tarifas minimas de carro y moto
        int carrosTarifaMinima = Integer.parseInt(prop.getProperty("carrosTarifaMinima")); //2000
        int motosTarifaMinima = Integer.parseInt(prop.getProperty("motosTarifaMinima")); // 1000

        //Recargos Nocturnos Fin de Semana        
        int carrosRecargoNocturnoFinSemana = Integer.parseInt(prop.getProperty("carrosRecargoNocturnoFinSemana")); //3000
        int motosRecargoNocturnoFinSemana = Integer.parseInt(prop.getProperty("carrosRecargoNocturnoFinSemana")); //2000
        
        // Paso 1: Dependiendo si es carro o moto determinar la tarifa minima (el costo de una hora)
        int tarifaMinima;
        if (tipoVehiculo.equals("Carro")) {
            tarifaMinima = carrosTarifaMinima;
        } else {
            tarifaMinima = motosTarifaMinima;
        }
        // Paso 2: Determinar el turno en que entró el vehiculo (turno1, turno2, o turno3).
        int turno = determinarTurno(fechaHoraEntrada);

        boolean terminar = false;
        boolean primerTurno = true;

        Date fechaHoraAux = (Date) fechaHoraEntrada.clone();

        int valorPagar = tarifaMinima;
        do {

            // Paso 3: Determinar la tarifa de ese turno (Ejemplo de 7:00 am. A 1:00 pm. 3500 para carro)
            int tarifa = 0;
            switch (turno) {
                case 1:
                    if (tipoVehiculo.equals("Carro")) {
                        tarifa = carrosTarifaDiurna;
                    } else {
                        //Moto
                        tarifa = motosTarifaDiurna;
                    }
                    break;
                case 2:
                    if (tipoVehiculo.equals("Carro")) {
                        tarifa = carrosTarifaTarde;
                    } else {
                        tarifa = motosTarifaTarde;
                    }
                    break;
                case 3:
                    if (tipoVehiculo.equals("Carro")) {
                        tarifa = carrosTarifaNocturna;
                        int diaDeLaSemana = Utilidades.calcularDiaDeLaSemana(fechaHoraAux);
                        if (diaDeLaSemana >= 5 && diaDeLaSemana <= 7){
                            tarifa = tarifa + carrosRecargoNocturnoFinSemana;
                        }
                    } else {
                        tarifa = motosTarifaNocturna;
                        int diaDeLaSemana = Utilidades.calcularDiaDeLaSemana(fechaHoraAux);
                        if (diaDeLaSemana >= 5 && diaDeLaSemana <= 7){
                            tarifa = tarifa + motosRecargoNocturnoFinSemana;
                        }                        
                    }
                    break;
            }

            // Paso 4: Calcular las horas que estuvo en ese turno
            int horasASumar = 0;
            float totalHorasTurno = 0;
            Date fechaLimiteTurno = null;
            switch (turno) {
                case 1: //Turno: 7:00 am - 1:00 pm                    
                    horasASumar = 13;
                    break;
                case 2: // Turno: 1:00 pm - 7:00 pm
                    horasASumar = 19;
                    break;
                case 3: // Turno: 7:00 pm - 7:00 am del siguiente dia
                    horasASumar = 31;
                    break;
            }
            fechaLimiteTurno = Utilidades.quitarHoraAFecha(fechaHoraAux);//
            fechaLimiteTurno = Utilidades.sumarHorasAFecha(fechaLimiteTurno, horasASumar);
            if (Utilidades.fechaEsPosterior(fechaLimiteTurno, fechaHoraSalida)) {
                //Es el ultimo turno a examinar, debe compararse con la fechaHoraSalida
                totalHorasTurno = Utilidades.calcularDiferenciaEnMinutos(fechaHoraAux, fechaHoraSalida) / 60.0f;
            } else {
                totalHorasTurno = Utilidades.calcularDiferenciaEnMinutos(fechaHoraAux, fechaLimiteTurno) / 60.0f;
            }

            // Paso 5: Acumular: tarifa minima + cobro proporcional al tiempo en ese turno
            int proporcionHorasTurno = 0;
            if (primerTurno) {
                if (turno == 1 || turno == 2) {
                    proporcionHorasTurno = 5;
                } else {
                    proporcionHorasTurno = 11;
                }
            } else {
                if (turno == 1 || turno == 2) {
                    proporcionHorasTurno = 6;
                } else {
                    proporcionHorasTurno = 12;
                }
            }
            if (primerTurno) {
                //Pequeña validación cuando estuvo minutos, para que no reste nada a la tarifa minima
                if (totalHorasTurno < 1) {
                    totalHorasTurno = 1;
                }
                //totalHorasTurno - 1: se resta 1 porque la primera hora ya el cobró la tarifa mínima
                valorPagar = (int) (valorPagar + (totalHorasTurno - 1) * (tarifa - tarifaMinima) / proporcionHorasTurno);
            } else {
                valorPagar = (int) (valorPagar + totalHorasTurno * (tarifa - tarifaMinima) / proporcionHorasTurno);
            }
            tarifaMinima = 0; //Despues del primer turno la tarifaMinima es cero
            primerTurno = false; //Despues de la primera iteracion ya no es primer turno
            turno++;
            if (turno > 3) {
                turno = 1;
            }

            // Paso 6: Si el limite del turno es mayor a la fecha de salida, 
            // terminar. Si no pasar al siguiente turno y asignar como
            // fecha1 la fecha limite del anterior turno e ir al paso 3.
            if (Utilidades.fechaEsPosterior(fechaLimiteTurno, fechaHoraSalida)) {
                terminar = true;
            } else {
                fechaHoraAux = fechaLimiteTurno;
            }

        } while (terminar != true);

        //Aplicar si extravio tarjeta
        return (int) Utilidades.redondearCentenaProxima(valorPagar);
    }

    public int tarifaExtravioTarjeta() {
        //Cargar Datos del archivo
        Properties prop = new Properties();
        InputStream is = null;

        try {
            is = new FileInputStream("./config.properties");
            prop.load(is);
        } catch (IOException e) {
            Utilidades.mensajeError("Error al cargar el archivo de configuración de variables\n" + e.getMessage(), "Atención");
            return 0;
        }
        //Tarifas de carro
        int tarifaExtravioTarjeta = Integer.parseInt(prop.getProperty("extravioTarjeta")); //5000;
        return tarifaExtravioTarjeta;
    }

    /**
     * Algoritmo que calcula el valor a apgar cuando es una entrada mensual
     *
     * @param tipoVehiculo Tipo de vehiculo: Carro ó Moto
     * @return Valor a pagar por el mes
     * @throws ParseException
     */
    public int calcularValorPagarEntradaMensual(String tipoVehiculo) throws ParseException {
        //Cargar Datos del archivo
        Properties prop = new Properties();
        InputStream is = null;

        try {
            is = new FileInputStream("./config.properties");
            prop.load(is);
        } catch (IOException e) {
            Utilidades.mensajeError("Error al cargar el archivo de configuración de variables\n" + e.getMessage(), "Atención");
            return 0;
        }
        //Tarifas de carro
        int tarifaMesCarro = Integer.parseInt(prop.getProperty("carrosTarifaMensual")); //180000;
        int tarifaMesMoto = Integer.parseInt(prop.getProperty("motosTarifaMensual"));//40000;

        int tarifa;
        if (tipoVehiculo.equals("Carro")) {
            tarifa = tarifaMesCarro;
        } else {
            tarifa = tarifaMesMoto;
        }
        return tarifa;

    }

    public int determinarTurno(Date fechaHora) throws ParseException {
        Date fechaMediaNoche = Utilidades.quitarHoraAFecha(fechaHora);
        Date fecha1 = Utilidades.sumarHorasAFecha(fechaMediaNoche, 7);
        Date fecha2 = Utilidades.sumarHorasAFecha(fechaMediaNoche, 13);
        if (Utilidades.fechaEstaDentroDe(fechaHora, fecha1, fecha2)) {
            return 1;
        } else {
            fecha1 = Utilidades.sumarHorasAFecha(fechaMediaNoche, 13);
            fecha2 = Utilidades.sumarHorasAFecha(fechaMediaNoche, 19);
            if (Utilidades.fechaEstaDentroDe(fechaHora, fecha1, fecha2)) {
                return 2;
            } else {
                return 3;
            }
        }
    }

    /**
     * Borra todos los registros de la base de datos, la inicializa
     */
    public void inicializarDatos() {

        EntityManagerFactory emf;
        EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory("ParqueaderoPU");
            em = emf.createEntityManager();

            em.getTransaction().begin();
            Query query = em.createQuery(
                    "DELETE FROM EntradasSalidas e ");
            int deletedCount = query.executeUpdate();
            System.out.println("Entradas y Salidas borradas: " + deletedCount);

            query = em.createQuery(
                    "DELETE FROM EntradasSalidasMensuales e ");
            deletedCount = query.executeUpdate();
            System.out.println("Entradas y Salidas Mensuales borradas: " + deletedCount);

            query = em.createQuery(
                    "DELETE FROM PagosMensuales e ");
            deletedCount = query.executeUpdate();
            System.out.println("Pagos Mensuales borrados: " + deletedCount);

            query = em.createQuery(
                    "DELETE FROM Empleados e Where e.id != 1 ");
            deletedCount = query.executeUpdate();
            System.out.println("Empleados borrados: " + deletedCount);

            query = em.createQuery(
                    "DELETE FROM Vehiculos e ");
            deletedCount = query.executeUpdate();
            System.out.println("Vehiculos borrados: " + deletedCount);
            
            query = em.createQuery(
                    "DELETE FROM GastosMensuales e ");
            deletedCount = query.executeUpdate();
            System.out.println("Gasto mensuales borrados: " + deletedCount);            
            
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

}
