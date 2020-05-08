/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gyan.parqueadero.logicanegocio;

import com.gyan.parqueadero.utilidades.Utilidades;
import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author libar
 */
public class EntradasSalidasLogicaNegocioTest {

    private EntradasSalidasLogicaNegocio logica;

    public EntradasSalidasLogicaNegocioTest() {
        logica = new EntradasSalidasLogicaNegocio();
    }

    /**
     * Test of calcularValorPagar method, of class EntradasSalidasLogicaNegocio.
     */
    @Test
    public void test1() throws Exception {
        System.out.println("Test1 - Carro, dos horas mañana");
        String tipoVehiculo = "Carro";

        Date fechaHoraEntrada = Utilidades.stringToDate("06-03-2019 08:00 AM");
        Date fechaHoraSalida = Utilidades.stringToDate("06-03-2019 10:00 AM");
        int resultadoEsperado = 2300;
        int result = logica.calcularValorPagarSalidaHoras(tipoVehiculo, fechaHoraEntrada, fechaHoraSalida);
        assertEquals(resultadoEsperado, result);
    }

    @Test
    public void test2() throws Exception {
        System.out.println("Test2 - Carro, una hora noche");
        String tipoVehiculo = "Carro";

        Date fechaHoraEntrada = Utilidades.stringToDate("06-03-2019 07:00 PM");
        Date fechaHoraSalida = Utilidades.stringToDate("06-03-2019 08:00 PM");
        int resultadoEsperado = 2000;
        int result = logica.calcularValorPagarSalidaHoras(tipoVehiculo, fechaHoraEntrada, fechaHoraSalida);
        assertEquals(resultadoEsperado, result);
    }

    @Test
    public void test3() throws Exception {
        System.out.println("Test3 - Carro, toda la mañana");
        String tipoVehiculo = "Carro";

        Date fechaHoraEntrada = Utilidades.stringToDate("06-03-2019 07:00 AM");
        Date fechaHoraSalida = Utilidades.stringToDate("06-03-2019 01:00 PM");
        int resultadoEsperado = 3500;
        int result = logica.calcularValorPagarSalidaHoras(tipoVehiculo, fechaHoraEntrada, fechaHoraSalida);
        assertEquals(resultadoEsperado, result);
    }

    @Test
    public void test4() throws Exception {
        System.out.println("Test4 - Carro, 24 horas");
        String tipoVehiculo = "Carro";

        Date fechaHoraEntrada = Utilidades.stringToDate("06-03-2019 7:00 AM");
        Date fechaHoraSalida = Utilidades.stringToDate("07-03-2019 07:00 AM");
        int resultadoEsperado = 14000;
        int result = logica.calcularValorPagarSalidaHoras(tipoVehiculo, fechaHoraEntrada, fechaHoraSalida);
        assertEquals(resultadoEsperado, result);
    }

    @Test
    public void test5() throws Exception {
        System.out.println("Test5 - Carro, hora y 15' en la mañana");
        String tipoVehiculo = "Carro";

        Date fechaHoraEntrada = Utilidades.stringToDate("06-03-2019 07:00 AM");
        Date fechaHoraSalida = Utilidades.stringToDate("06-03-2019 08:15 AM");
        int resultadoEsperado = 2100;
        int result = logica.calcularValorPagarSalidaHoras(tipoVehiculo, fechaHoraEntrada, fechaHoraSalida);
        assertEquals(resultadoEsperado, result);
    }

    @Test
    public void test6() throws Exception {
        System.out.println("Test6 - Carro, 2 horas y 15' en la mañana");
        String tipoVehiculo = "Carro";

        Date fechaHoraEntrada = Utilidades.stringToDate("06-03-2019 07:00 AM");
        Date fechaHoraSalida = Utilidades.stringToDate("06-03-2019 09:15 AM");
        int resultadoEsperado = 2400;
        int result = logica.calcularValorPagarSalidaHoras(tipoVehiculo, fechaHoraEntrada, fechaHoraSalida);
        assertEquals(resultadoEsperado, result);
    }

    @Test
    public void test7() throws Exception {
        System.out.println("Test7 - Motos, 3 horas diurnas, 6 tarde, 2 nocturnas");
        String tipoVehiculo = "Moto";

        Date fechaHoraEntrada = Utilidades.stringToDate("06-03-2019 10:00 AM");
        Date fechaHoraSalida = Utilidades.stringToDate("06-03-2019 09:00 PM");
        int resultadoEsperado = 4100;
        int result = logica.calcularValorPagarSalidaHoras(tipoVehiculo, fechaHoraEntrada, fechaHoraSalida);
        assertEquals(resultadoEsperado, result);
    }

    @Test
    public void test8() throws Exception {
        System.out.println("Test8 - Carro un minuto en la mañana");
        String tipoVehiculo = "Carro";

        Date fechaHoraEntrada = Utilidades.stringToDate("06-03-2019 07:00 AM");
        Date fechaHoraSalida = Utilidades.stringToDate("06-03-2019 07:01 AM");
        int resultadoEsperado = 2000;
        int result = logica.calcularValorPagarSalidaHoras(tipoVehiculo, fechaHoraEntrada, fechaHoraSalida);
        assertEquals(resultadoEsperado, result);
    }

    @Test
    public void test9() throws Exception {
        System.out.println("Test9 - Carro mañana");
        String tipoVehiculo = "Carro";

        Date fechaHoraEntrada = Utilidades.stringToDate("11-09-2019 07:00 AM");
        Date fechaHoraSalida = Utilidades.stringToDate("11-09-2019 1:00 PM");
        int resultadoEsperado = 3500;
        int result = logica.calcularValorPagarSalidaHoras(tipoVehiculo, fechaHoraEntrada, fechaHoraSalida);
        assertEquals(resultadoEsperado, result);
    }

    @Test
    public void test10() throws Exception {
        System.out.println("Test9 - Carro nocturno");
        String tipoVehiculo = "Carro";

        Date fechaHoraEntrada = Utilidades.stringToDate("11-09-2019 07:00 PM");
        Date fechaHoraSalida = Utilidades.stringToDate("12-09-2019 7:00 AM");
        int resultadoEsperado = 7000;
        int result = logica.calcularValorPagarSalidaHoras(tipoVehiculo, fechaHoraEntrada, fechaHoraSalida);
        assertEquals(resultadoEsperado, result);
    }

    @Test
    public void test11() throws Exception {
        System.out.println("Test9 - Carro nocturno fin de semana");
        String tipoVehiculo = "Carro";

        Date fechaHoraEntrada = Utilidades.stringToDate("12-09-2019 07:00 PM");
        Date fechaHoraSalida = Utilidades.stringToDate("13-09-2019 7:00 AM");
        int resultadoEsperado = 10000;
        int result = logica.calcularValorPagarSalidaHoras(tipoVehiculo, fechaHoraEntrada, fechaHoraSalida);
        assertEquals(resultadoEsperado, result);
    }
}
