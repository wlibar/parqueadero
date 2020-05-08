/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gyan.parqueadero.interfaz;

import com.gyan.parqueadero.entidades.EntradasSalidas;
import com.gyan.parqueadero.entidades.EntradasSalidasMensuales;
import static com.gyan.parqueadero.interfaz.GUIInformeIngresosPorFecha.TIPOS_VEHICULO;
import com.gyan.parqueadero.logicanegocio.EntradasSalidasLogicaNegocio;
import com.gyan.parqueadero.pojos.TiempoTranscurrido;
import com.gyan.parqueadero.utilidades.Utilidades;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author libardo
 */
public class GUIVehiculosEnElParqueadero extends javax.swing.JInternalFrame {

    /**
     * Array asociativo con los tipos de vehiculos: C=>Carro, M=>Moto
     */
    public static Hashtable<String, String> TIPOS_VEHICULO = new Hashtable<String, String>();

    /**
     * Creates new form GUIInformeIngresosPorFecha
     */
    public GUIVehiculosEnElParqueadero() {
        initComponents();
        TIPOS_VEHICULO.put("M", "Moto");
        TIPOS_VEHICULO.put("C", "Carro");  
        //Inicializa las columnas del jTable
        this.inicializarTabla();

        this.buscarDatos();
        this.setSize(800, 500);
      
    }

    /**
     * Inicializa las columnas del jTable y pone cero filas
     */
    private void inicializarTabla() {
        tblResultado.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Tipo", "Placa", "FechaEntrada", "FechaSalida", "TiempoTranscurrido"
                }
        ));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlCentro = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblResultado = new javax.swing.JTable();
        pnlSur = new javax.swing.JPanel();
        btnCerrar = new javax.swing.JButton();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Informe - Vehiculos en el Parqueadero");

        pnlCentro.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        pnlCentro.setLayout(new java.awt.BorderLayout());

        tblResultado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblResultado);

        pnlCentro.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnlCentro, java.awt.BorderLayout.CENTER);

        pnlSur.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        pnlSur.add(btnCerrar);

        getContentPane().add(pnlSur, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void buscarDatos() {

        EntradasSalidasLogicaNegocio logica = new EntradasSalidasLogicaNegocio();

        //Primero: Buscar en EntradasSalidas
        List<EntradasSalidas> result = logica.vehiculosPorLiquidar();

        //Llenar los datos en la jTable
        this.inicializarTabla();
        DefaultTableModel model = (DefaultTableModel) tblResultado.getModel();
        //Tipo", "Placa", "FechaEntrada", "FechaSalida", "TiempoTranscurrido"

        Object rowData[] = new Object[5];
        for (int i = 0; i < result.size(); i++) {
            String tipo= result.get(i).getPlaca().getTipo();
            rowData[0] = TIPOS_VEHICULO.get(result.get(i).getPlaca().getTipo());
            rowData[1] = result.get(i).getPlaca().getPlaca();
            rowData[2] = Utilidades.formatoFecha(result.get(i).getFechaHoraEntrada());
            rowData[3] = "";//null
            //Tiempo transcurrido
            Date fecha1 = result.get(i).getFechaHoraEntrada();
            Date ahora = new GregorianCalendar().getTime();
            TiempoTranscurrido tc = Utilidades.calcularTiempoTranscurrido(fecha1, ahora);
            rowData[4] = tc.getHoras() + " horas " + tc.getMinutos() + " min.";

            model.addRow(rowData);
        }

        //Segundo: Buscar en EntradasSalidasMensuales
        List<EntradasSalidasMensuales> result2 = logica.vehiculosPorLiquidarMensuales();

        //Tipo", "Placa", "FechaEntrada", "FechaSalida", "TiempoTranscurrido"
        for (int i = 0; i < result2.size(); i++) {
            rowData[0] = TIPOS_VEHICULO.get(result2.get(i).getPagosMensualesId().getPlaca().getTipo());
            rowData[1] = result2.get(i).getPagosMensualesId().getPlaca().getPlaca();
            rowData[2] = Utilidades.formatoFecha(result2.get(i).getFechaHoraEntrada());
            rowData[3] = "";//null
            //Tiempo transcurrido
            Date fecha1 = result2.get(i).getFechaHoraEntrada();
            Date ahora = new GregorianCalendar().getTime();
            TiempoTranscurrido tc = Utilidades.calcularTiempoTranscurrido(fecha1, ahora);
            rowData[4] = tc.getHoras() + " horas " + tc.getMinutos() + " min.";

            model.addRow(rowData);
        }

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnlCentro;
    private javax.swing.JPanel pnlSur;
    private javax.swing.JTable tblResultado;
    // End of variables declaration//GEN-END:variables
}