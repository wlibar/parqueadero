/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gyan.parqueadero.interfaz;

import com.gyan.parqueadero.entidades.EntradasSalidas;
import com.gyan.parqueadero.entidades.EntradasSalidasMensuales;
import com.gyan.parqueadero.entidades.GUICapturarPlaca;
import com.gyan.parqueadero.entidades.PagosMensuales;
import com.gyan.parqueadero.entidades.Vehiculos;
import com.gyan.parqueadero.logicanegocio.EntradasSalidasLogicaNegocio;
import com.gyan.parqueadero.pojos.Puchito;
import com.gyan.parqueadero.pojos.TiempoTranscurrido;
import com.gyan.parqueadero.utilidades.ReciboPdf;
import com.gyan.parqueadero.utilidades.TextPrompt;
import com.gyan.parqueadero.utilidades.Utilidades;
import static com.gyan.parqueadero.utilidades.Utilidades.mensajeAdvertencia;
import static com.gyan.parqueadero.utilidades.Utilidades.mensajeError;
import static com.gyan.parqueadero.utilidades.Utilidades.mensajeExito;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author libardo
 */
public class GUIEntradasSalidas extends javax.swing.JInternalFrame {

    public static final String SELECCIONE = "--Seleccione--";
    public static final String SALIDA_TITULO = "Salida de Vehículo";
    public static final String ENTRADA_TITULO = "Entrada de Vehículo";

    public static final int CASO_ENTRADA_HORAS = 1;
    public static final int CASO_ENTRADA_MENSUAL = 2;
    public static final int CASO_ENTRADA_PAGO_MENSUAL = 3;

    public static final int CASO_SALIDA_HORAS = 4;
    public static final int CASO_SALIDA_MENSUAL = 5;
    public static final int CASO_SALIDA_MENSUAL_CON_PUCHITO = 6;

    /**
     * Guarda el caso una vez buscada la placa
     */
    private int caso;
    /**
     * Array asociativo con los tipos de vehiculos: C=>Carro, M=>Moto
     */
    public static Hashtable<String, String> TIPOS_VEHICULO = new Hashtable<String, String>();
    /**
     * Valor a pagar por la salida, se guarda a nivel de clase para evitar hacer
     * recalculos
     */
    private long valorPagar;

    /**
     * Objeto Entrada y salida mensual, se guarda a nivel de clase
     */
    private EntradasSalidasMensuales objEntSalMensual;

    /**
     * Objeto Entrada y salida, se guarda a nivel de clase
     */
    private EntradasSalidas objEntSal;

    /**
     * Objeto Pagos Mensuales
     */
    PagosMensuales objPagoMes;

    /**
     * horas del tiempo transcurrido entre la salida y la entrada
     */
    private long horas;

    /**
     * Guarda la fechaHora de salida de un vehiculo
     */
    private Date ahora;

    private Puchito puchito;

    /**
     * Una sóla instancia para acceder a la lógica del negocio
     */
    private EntradasSalidasLogicaNegocio logica;

    /**
     * Constructor por defecto
     */
    public GUIEntradasSalidas() {
        initComponents();
        //setClosable(true);
        this.setMaximizable(true);
        this.setResizable(true);
        estadoControlesCargarVentana();
        TextPrompt tp = new TextPrompt("Escanée el código de barras con la pistola", txtCodigoBuscar);
        tp.setForeground(Color.LIGHT_GRAY);
        this.valorPagar = 0;
        this.setSize(900, 700);

        llenarTiposVehiculos();
        logica = new EntradasSalidasLogicaNegocio();
    }

    public void llenarTiposVehiculos() {
        TIPOS_VEHICULO.put("M", "Moto");
        TIPOS_VEHICULO.put("C", "Carro");
    }

    /**
     * Estado de los controles al cargar la ventana
     */
    private void estadoControlesCargarVentana() {
        txtCodigoBuscar.setText("");
        txtValorPagar.setText("0");
        this.pnlCentro.setVisible(false);
        this.btnGrabar.setVisible(false);
        this.btnRecibo.setVisible(false);
        llenarCboTiposVehiculo();
        txtCodigoBuscar.setVisible(true);
        txtPlacaBuscar.setVisible(false);
        //rbtCodigo.setSelected(true);
    }

    /**
     * Estado de los controles al empezar una nueva transaccion o busqueda
     */
    private void estadoControlesEmpezarTransaccion() {
        this.pnlCentro.setVisible(false);
        this.btnGrabar.setVisible(false);
        this.btnRecibo.setVisible(false);

        cboTipoVehiculo.setSelectedIndex(0);
        lblMensaje.setText("Nota:");
        cboTipoVehiculo.setEnabled(true);

        //rbtCodigo.setSelected(true);
        //Limpiar controles
        //txtCodigoBuscar.setText("");
        //txtPlacaBuscar.setText("");
        txtPlaca.setText("");
        txtPropietario.setText("");
        txtFechaEntrada.setText("");
        txtFechaSalida.setText("");
        txtTiempoTranscurrido.setText("");
        txtDescuento.setText("");
        txtEfectivo.setText("");
        txtCambio.setText("");
        txtCantidadCascos.setText("");
        txtCasillero.setText("");

        chkLlaves.setSelected(false);
        chkExtravioTarjeta.setSelected(false);

        //lblCantidadCascos.setVisible(false);
        txtCantidadCascos.setEnabled(false);

        //lblCasillero.setVisible(false);
        txtCasillero.setEnabled(false);

        txtObservacion.setText("");

    }

    /**
     * Estado de los controles cuando el sistema nota que es una entrada por
     * horas
     */
    private void estadoControlesRegistrarEntradaHoras() {
        pnlCentro.setBorder(javax.swing.BorderFactory.createTitledBorder(ENTRADA_TITULO));
        this.pnlCentro.setVisible(true);
        this.lblFechaSalida.setVisible(false);
        this.txtFechaSalida.setVisible(false);
        this.lblDescuento.setVisible(false);
        this.txtDescuento.setVisible(false);
        this.lblTiempoTranscurrido.setVisible(false);
        this.txtTiempoTranscurrido.setVisible(false);
        this.lblValorPagar.setVisible(false);
        this.txtValorPagar.setVisible(false);
        this.lblEfectivo.setVisible(false);
        this.txtEfectivo.setVisible(false);
        this.lblCambio.setVisible(false);
        this.txtCambio.setVisible(false);
        this.btnGrabar.setVisible(true);
        this.btnRecibo.setVisible(true);
        this.btnDescuento.setVisible(false);
        this.btnEfectivo.setVisible(false);

        this.chkLlaves.setVisible(true);
        this.chkLlaves.setEnabled(true);

        this.txtPropietario.setEnabled(true);
        chkExtravioTarjeta.setVisible(false);

    }

    /**
     * Estado cuando es una entrada y se va a hacer el pago de todo el mes
     */
    private void estadoControlesRegistrarPagoMensual() {
        lblDescuento.setVisible(true);
        txtDescuento.setVisible(true);

        lblValorPagar.setVisible(true);
        txtValorPagar.setVisible(true);

        lblEfectivo.setVisible(true);
        txtEfectivo.setVisible(true);

        lblCambio.setVisible(true);
        txtCambio.setVisible(true);

        btnDescuento.setVisible(true);
        btnEfectivo.setVisible(true);

        btnRecibo.setVisible(true);

        this.txtPropietario.setEnabled(true);
    }

    /**
     * Estado de los controles cuando el sistema nota que es una entrada mes, o
     * sea, que tiene vigente un pago mensual
     */
    private void estadoControlesRegistrarEntradaMes() {
        pnlCentro.setBorder(javax.swing.BorderFactory.createTitledBorder(ENTRADA_TITULO));
        this.txtValorPagar.setText("0");

        this.pnlCentro.setVisible(true);
        this.lblFechaSalida.setVisible(false);
        this.txtFechaSalida.setVisible(false);

        this.lblDescuento.setVisible(false);
        this.txtDescuento.setVisible(false);

        this.lblTiempoTranscurrido.setVisible(false);
        this.txtTiempoTranscurrido.setVisible(false);

        this.lblValorPagar.setVisible(false);
        this.txtValorPagar.setVisible(false);

        this.lblEfectivo.setVisible(false);
        this.txtEfectivo.setVisible(false);

        this.lblCambio.setVisible(false);
        this.txtCambio.setVisible(false);

        this.btnGrabar.setVisible(true);
        this.btnRecibo.setVisible(true);
        this.btnDescuento.setVisible(false);
        this.btnEfectivo.setVisible(false);

        cboTipoVehiculo.setEnabled(false);

        this.txtPropietario.setEnabled(true);

        this.chkLlaves.setVisible(true);
        this.chkExtravioTarjeta.setVisible(false);

    }

    /**
     * Estado de los controles cuando es una salida por horas
     */
    private void estadoControlesRegistrarSalidaHoras() {
        pnlCentro.setBorder(javax.swing.BorderFactory.createTitledBorder(SALIDA_TITULO));
        this.pnlCentro.setVisible(true);
        this.lblFechaSalida.setVisible(true);
        this.txtFechaSalida.setVisible(true);
        this.lblDescuento.setVisible(true);
        this.txtDescuento.setVisible(true);
        this.lblTiempoTranscurrido.setVisible(true);
        this.txtTiempoTranscurrido.setVisible(true);
        this.lblValorPagar.setVisible(true);
        this.txtValorPagar.setVisible(true);
        this.lblEfectivo.setVisible(true);
        this.txtEfectivo.setVisible(true);
        this.lblCambio.setVisible(true);
        this.txtCambio.setVisible(true);
        this.btnGrabar.setVisible(true);
        this.btnRecibo.setVisible(true);
        this.btnDescuento.setVisible(true);
        this.btnEfectivo.setVisible(true);
        this.chkLlaves.setVisible(true);
        this.chkLlaves.setEnabled(false);
        this.cboTipoVehiculo.setEnabled(false);
        this.txtPropietario.setEnabled(false);
        this.chkExtravioTarjeta.setVisible(true);

    }

    /**
     * Estado de los controles cuando el sistema nota que es una salida que ya
     * tiene un pago de todo el mes
     */
    private void estadoControlesRegistrarSalidaMes() {
        pnlCentro.setBorder(javax.swing.BorderFactory.createTitledBorder(SALIDA_TITULO));
        txtDescuento.setText("");
        txtValorPagar.setVisible(false);

        this.cboTipoVehiculo.setEnabled(false);

        this.pnlCentro.setVisible(true);
        this.lblFechaSalida.setVisible(true);
        this.txtFechaSalida.setVisible(true);
        this.lblDescuento.setVisible(false);
        this.txtDescuento.setVisible(false);
        this.lblTiempoTranscurrido.setVisible(true);
        this.txtTiempoTranscurrido.setVisible(true);

        this.lblValorPagar.setVisible(false);
        this.txtValorPagar.setVisible(false);

        this.lblEfectivo.setVisible(false);
        this.txtEfectivo.setVisible(false);
        this.lblCambio.setVisible(false);
        this.txtCambio.setVisible(false);
        this.btnGrabar.setVisible(true);
        this.btnRecibo.setVisible(true);
        this.txtDescuento.setText("");
        this.txtEfectivo.setText("0");
        this.txtCambio.setText("0");
        this.btnDescuento.setVisible(false);
        this.btnEfectivo.setVisible(false);

        this.chkLlaves.setVisible(true);
        this.chkLlaves.setEnabled(false);

        this.txtPropietario.setEnabled(false);
        this.chkExtravioTarjeta.setVisible(false);

    }

    // Activa los controles para hacer un pago extra
    private void estadoControlesActivarPagoExtra() {
        this.lblDescuento.setVisible(true);
        this.txtDescuento.setVisible(true);
        this.lblValorPagar.setVisible(true);
        this.txtValorPagar.setVisible(true);
        this.lblEfectivo.setVisible(true);
        this.txtEfectivo.setVisible(true);
        this.lblCambio.setVisible(true);
        this.txtCambio.setVisible(true);
        this.btnDescuento.setVisible(true);
        this.btnEfectivo.setVisible(true);
        //this.btnReciboEntrada.setVisible(false);
        //this.btnReciboSalida.setVisible(true);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        pnlNorte = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        rbtCodigo = new javax.swing.JRadioButton();
        rbtPlaca = new javax.swing.JRadioButton();
        txtCodigoBuscar = new javax.swing.JPasswordField();
        txtPlacaBuscar = new javax.swing.JFormattedTextField();
        btnBuscar = new javax.swing.JButton();
        pnlCentro = new javax.swing.JPanel();
        lblTipoVehiculo = new javax.swing.JLabel();
        cboTipoVehiculo = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        txtFechaEntrada = new javax.swing.JTextField();
        lblFechaSalida = new javax.swing.JLabel();
        txtFechaSalida = new javax.swing.JTextField();
        lblDescuento = new javax.swing.JLabel();
        txtDescuento = new javax.swing.JTextField();
        lblTiempoTranscurrido = new javax.swing.JLabel();
        txtTiempoTranscurrido = new javax.swing.JTextField();
        lblValorPagar = new javax.swing.JLabel();
        lblEfectivo = new javax.swing.JLabel();
        txtEfectivo = new javax.swing.JTextField();
        lblCambio = new javax.swing.JLabel();
        txtCambio = new javax.swing.JTextField();
        lblObservacion = new javax.swing.JLabel();
        btnDescuento = new javax.swing.JButton();
        btnEfectivo = new javax.swing.JButton();
        lblMensaje = new javax.swing.JLabel();
        chkLlaves = new javax.swing.JCheckBox();
        lblPropietario = new javax.swing.JLabel();
        txtPropietario = new javax.swing.JTextField();
        lblCantidadCascos = new javax.swing.JLabel();
        txtCantidadCascos = new javax.swing.JTextField();
        lblCasillero = new javax.swing.JLabel();
        txtCasillero = new javax.swing.JTextField();
        lblPlaca = new javax.swing.JLabel();
        txtObservacion = new javax.swing.JTextField();
        txtPlaca = new javax.swing.JFormattedTextField();
        txtValorPagar = new javax.swing.JTextField();
        chkExtravioTarjeta = new javax.swing.JCheckBox();
        pnlSur = new javax.swing.JPanel();
        btnGrabar = new javax.swing.JButton();
        btnRecibo = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Entrada y Salida de Vehículos");

        pnlNorte.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        pnlNorte.setLayout(new javax.swing.BoxLayout(pnlNorte, javax.swing.BoxLayout.LINE_AXIS));

        jLabel1.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        jLabel1.setText("Buscar por ");
        pnlNorte.add(jLabel1);

        buttonGroup1.add(rbtCodigo);
        rbtCodigo.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        rbtCodigo.setSelected(true);
        rbtCodigo.setText("Código");
        rbtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtCodigoActionPerformed(evt);
            }
        });
        pnlNorte.add(rbtCodigo);

        buttonGroup1.add(rbtPlaca);
        rbtPlaca.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        rbtPlaca.setText("Placa");
        rbtPlaca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtPlacaActionPerformed(evt);
            }
        });
        pnlNorte.add(rbtPlaca);

        txtCodigoBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoBuscarKeyPressed(evt);
            }
        });
        pnlNorte.add(txtCodigoBuscar);

        try {
            txtPlacaBuscar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("AAA-AAA")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtPlacaBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPlacaBuscarKeyPressed(evt);
            }
        });
        pnlNorte.add(txtPlacaBuscar);

        btnBuscar.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/buscar.png"))); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.setIconTextGap(2);
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        pnlNorte.add(btnBuscar);

        getContentPane().add(pnlNorte, java.awt.BorderLayout.PAGE_START);

        pnlCentro.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del vehículo"));
        pnlCentro.setLayout(new java.awt.GridBagLayout());

        lblTipoVehiculo.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        lblTipoVehiculo.setText("Tipo de Vehiculo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        pnlCentro.add(lblTipoVehiculo, gridBagConstraints);

        cboTipoVehiculo.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        cboTipoVehiculo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboTipoVehiculo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboTipoVehiculoItemStateChanged(evt);
            }
        });
        cboTipoVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTipoVehiculoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        pnlCentro.add(cboTipoVehiculo, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        jLabel2.setText("FechaHora de Entrada:");
        jLabel2.setToolTipText("dd-mm-aaaa hh:mm am-pm");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        pnlCentro.add(jLabel2, gridBagConstraints);

        txtFechaEntrada.setEditable(false);
        txtFechaEntrada.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        pnlCentro.add(txtFechaEntrada, gridBagConstraints);

        lblFechaSalida.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        lblFechaSalida.setText("FechaHora de Salida:");
        lblFechaSalida.setToolTipText("dd-mm-aaaa hh:mm am-pm");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        pnlCentro.add(lblFechaSalida, gridBagConstraints);

        txtFechaSalida.setEditable(false);
        txtFechaSalida.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        pnlCentro.add(txtFechaSalida, gridBagConstraints);

        lblDescuento.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        lblDescuento.setText("Descuento $:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        pnlCentro.add(lblDescuento, gridBagConstraints);

        txtDescuento.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        txtDescuento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDescuentoKeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        pnlCentro.add(txtDescuento, gridBagConstraints);

        lblTiempoTranscurrido.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        lblTiempoTranscurrido.setText("Tiempo transcurrido:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        pnlCentro.add(lblTiempoTranscurrido, gridBagConstraints);

        txtTiempoTranscurrido.setEditable(false);
        txtTiempoTranscurrido.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        pnlCentro.add(txtTiempoTranscurrido, gridBagConstraints);

        lblValorPagar.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        lblValorPagar.setText("Valor a Pagar $:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        pnlCentro.add(lblValorPagar, gridBagConstraints);

        lblEfectivo.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        lblEfectivo.setText("Efectivo $ (opcional):");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        pnlCentro.add(lblEfectivo, gridBagConstraints);

        txtEfectivo.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        txtEfectivo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtEfectivoKeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        pnlCentro.add(txtEfectivo, gridBagConstraints);

        lblCambio.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        lblCambio.setText("Cambio $ (opcional):");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        pnlCentro.add(lblCambio, gridBagConstraints);

        txtCambio.setEditable(false);
        txtCambio.setBackground(java.awt.Color.white);
        txtCambio.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        pnlCentro.add(txtCambio, gridBagConstraints);

        lblObservacion.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        lblObservacion.setText("Observación (opcional):");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        pnlCentro.add(lblObservacion, gridBagConstraints);

        btnDescuento.setFont(new java.awt.Font("DejaVu Sans", 0, 8)); // NOI18N
        btnDescuento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/aplicar.png"))); // NOI18N
        btnDescuento.setToolTipText("Aplicar descuento");
        btnDescuento.setMaximumSize(new java.awt.Dimension(50, 50));
        btnDescuento.setPreferredSize(new java.awt.Dimension(30, 30));
        btnDescuento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDescuentoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        pnlCentro.add(btnDescuento, gridBagConstraints);

        btnEfectivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/aplicar.png"))); // NOI18N
        btnEfectivo.setToolTipText("Aplicar efectivo");
        btnEfectivo.setPreferredSize(new java.awt.Dimension(30, 30));
        btnEfectivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEfectivoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        pnlCentro.add(btnEfectivo, gridBagConstraints);

        lblMensaje.setBackground(java.awt.Color.lightGray);
        lblMensaje.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        lblMensaje.setText("Nota:");
        lblMensaje.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblMensaje.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        pnlCentro.add(lblMensaje, gridBagConstraints);

        chkLlaves.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        chkLlaves.setText("Deja llaves");
        chkLlaves.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkLlavesActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        pnlCentro.add(chkLlaves, gridBagConstraints);

        lblPropietario.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        lblPropietario.setText("Nombres y Apellidos (opcional):");
        lblPropietario.setToolTipText("Nombres y apellidos del conductor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        pnlCentro.add(lblPropietario, gridBagConstraints);

        txtPropietario.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        pnlCentro.add(txtPropietario, gridBagConstraints);

        lblCantidadCascos.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        lblCantidadCascos.setText("Cantidad de cascos:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        pnlCentro.add(lblCantidadCascos, gridBagConstraints);

        txtCantidadCascos.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        txtCantidadCascos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadCascosKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        pnlCentro.add(txtCantidadCascos, gridBagConstraints);

        lblCasillero.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        lblCasillero.setText("Casillero No:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        pnlCentro.add(lblCasillero, gridBagConstraints);

        txtCasillero.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        txtCasillero.setPreferredSize(new java.awt.Dimension(60, 36));
        txtCasillero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCasilleroKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        pnlCentro.add(txtCasillero, gridBagConstraints);

        lblPlaca.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        lblPlaca.setText("Placa:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        pnlCentro.add(lblPlaca, gridBagConstraints);

        txtObservacion.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        pnlCentro.add(txtObservacion, gridBagConstraints);

        try {
            txtPlaca.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("AAA-AAA")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtPlaca.setFocusable(false);
        txtPlaca.setFont(new java.awt.Font("DejaVu Sans", 0, 36)); // NOI18N
        txtPlaca.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPlacaFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        pnlCentro.add(txtPlaca, gridBagConstraints);

        txtValorPagar.setFocusable(false);
        txtValorPagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValorPagarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        pnlCentro.add(txtValorPagar, gridBagConstraints);

        chkExtravioTarjeta.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        chkExtravioTarjeta.setText("Extravió la tarjeta");
        chkExtravioTarjeta.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkExtravioTarjetaItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        pnlCentro.add(chkExtravioTarjeta, gridBagConstraints);

        getContentPane().add(pnlCentro, java.awt.BorderLayout.CENTER);

        pnlSur.setBorder(javax.swing.BorderFactory.createTitledBorder("Opciones"));

        btnGrabar.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        btnGrabar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/grabar.png"))); // NOI18N
        btnGrabar.setText("Grabar");
        btnGrabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGrabarActionPerformed(evt);
            }
        });
        pnlSur.add(btnGrabar);

        btnRecibo.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        btnRecibo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/print.png"))); // NOI18N
        btnRecibo.setText("Imprimir Recibo");
        btnRecibo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReciboActionPerformed(evt);
            }
        });
        pnlSur.add(btnRecibo);

        btnCerrar.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/cerrar.png"))); // NOI18N
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

    private void btnReciboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReciboActionPerformed
        //Utilidades.mensajeExito("Esta opción está en construcción...", "Atención");
        //Cargar Datos del archivo
        Properties prop = new Properties();
        InputStream is = null;

        try {
            is = new FileInputStream("./config.properties");
            prop.load(is);
        } catch (IOException e) {
            Utilidades.mensajeError("Error al cargar el archivo de configuración de variables\n" + e.getMessage(), "Atención");
            return;
        }
        //Tarifas de carro
        String nombreParqueadero = prop.getProperty("nombreParqueadero");
        String direccionParqueadero = prop.getProperty("direccionParqueadero");
        String llaves;
        if (chkLlaves.isSelected()) {
            llaves = "Si";
        } else {
            llaves = "No";
        }
        String cascosNro = txtCantidadCascos.getText();
        
        String fechaHoraSalida = "";
        if (txtFechaSalida.isVisible()) {
            fechaHoraSalida = txtFechaSalida.getText();
        }
        String valorPagar = "";
        if (txtValorPagar.isVisible()) {
            valorPagar = txtValorPagar.getText();
        }
        ReciboPdf.generar(nombreParqueadero, direccionParqueadero, txtPlaca.getText(), txtFechaEntrada.getText(), fechaHoraSalida, valorPagar, llaves, cascosNro);

        //Leer pdf
        try {
            File path = new File("Recibo.pdf");
            Desktop.getDesktop().open(path);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnReciboActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String datoBusqueda;
        String tipo;
        // Determinar si busca por codigo de barras ó por placa
        if (rbtCodigo.isSelected()) {
            // Búsqueda por código de barras
            datoBusqueda = txtCodigoBuscar.getText().trim().toUpperCase();
            txtCodigoBuscar.setText(datoBusqueda);
            tipo = "CODIGO"; //Codigo de barras
        } else {
            // Busqueda por placa
            datoBusqueda = txtPlacaBuscar.getText().trim().toUpperCase();
            txtPlacaBuscar.setText(datoBusqueda);
            tipo = "PLACA";
        }

        if (datoBusqueda.equals("")) {
            estadoControlesCargarVentana();
            return;
        }
        try {
            buscarEntradaSalida(datoBusqueda, tipo);
        } catch (ParseException ex) {
            Utilidades.mensajeAdvertencia("Error al buscar.\n" + ex.getMessage(), "Atención");
            Logger.getLogger(GUIEntradasSalidas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnBuscarActionPerformed

    /**
     * Aplica la lógica cuando es tarifa mensual o por horas, el cual es
     * disparado desde los combos cboTipoVehiculo y cboTarifa
     */
    private void aplicarTarifa() {
        //if (cboTarifa.getSelectedItem().toString().equals("Mensual")) {
        //Se paga todo el mes
        //estadoControlesRegistrarPagoMensual();
        //calcularValorPagarEntradaMes();
        //caso = CASO_ENTRADA_PAGO_MENSUAL;
        //} else {
        //Es una entrada normal por horas
        // Tambien sirve esta logica para --Seleccione--
        lblDescuento.setVisible(false);
        txtDescuento.setVisible(false);

        lblValorPagar.setVisible(false);
        txtValorPagar.setVisible(false);

        lblEfectivo.setVisible(false);
        txtEfectivo.setVisible(false);

        lblCambio.setVisible(false);
        txtCambio.setVisible(false);

        btnDescuento.setVisible(false);
        btnEfectivo.setVisible(false);
        //calcularValorPagarEntradaMes();
        caso = CASO_ENTRADA_HORAS;
        //}
    }

    /**
     * Busca la entradaSalida de un vehiculo cuando da click en el boton buscar,
     * o ha presionado enter en la caja. Se debe determinar si es una entrada o
     * salida de vehiculo. Determina también el CASO.
     *
     * @param placa
     */
    private void buscarEntradaSalida(String dato, String tipo) throws ParseException {
        estadoControlesEmpezarTransaccion();

        // Buscar la placa y determinar si es una ENTRADA ó SALIDA MENSUAL ó SALIDA HORAS
        // Busca una entradaSalida con placa y fechaHoraSalida null
        this.objEntSal = logica.getEntradaSalida(dato, tipo);

        if (this.objEntSal != null) { //Encontró un registro
            //**********Es una SALIDA POR HORAS**********
            procesarSalidaHoras();
            caso = CASO_SALIDA_HORAS;
        } else {
            // Buscar la placa en la tabla entradas_salidas_mensuales con fechaHoraSalida null
            this.objEntSalMensual = logica.getEntradaSalidaMensual(dato, tipo);

            if (this.objEntSalMensual != null) {
                //**** Es una SALIDA de vehículo que tiene tarifa MENSUAL********
                procesarSalidaMes(objEntSalMensual);

            } else {
                //**********Es una ENTRADA*********
                // Ahora se requiere que primero pida la placa en un InputBox
                String placa;

                if (tipo.equals("CODIGO")) {
                    //Mi propio InputBox
                    GUICapturarPlaca gui = new GUICapturarPlaca(null, true);
                    gui.setVisible(true);
                    placa = gui.getPlaca();
                } else {
                    placa = dato;
                }
                if (placa.equals("-")) {
                    //Le dio cancelar

                    estadoControlesEmpezarTransaccion();
                    pnlCentro.setVisible(false);
                    pnlCentro.revalidate();
                    return;
                }
                placa = placa.trim().toUpperCase();
                txtPlaca.setText(placa);

                cargarVehiculo(placa);

                //Buscar un pago mensual  
                this.objPagoMes = logica.getPagoMensual(placa);
                if (objPagoMes != null) {
                    procesarEntradaConPagoMensual(objPagoMes);
                    caso = CASO_ENTRADA_MENSUAL;
                } else {
                    //Es una entrada normal por horas
                    procesarEntradaHoras(placa);
                    caso = CASO_ENTRADA_HORAS;
                }
            }

        }
    }

    /**
     * Busca el vehiculo por placa y llena las cajas respectivas
     *
     * @param placa
     */
    private void cargarVehiculo(String placa) {
        Vehiculos vehiculo = logica.getVehiculo(placa);
        if (vehiculo != null) {
            //Ya existe,  se muestran los datos
            txtPropietario.setText(vehiculo.getPropietario());
            cboTipoVehiculo.setSelectedItem(TIPOS_VEHICULO.get(vehiculo.getTipo()));
        }
    }

    private void cargarDatosEntradaHoras() {
        //Fijar fecha y hora de entrada con el sistema
        Calendar cal = new GregorianCalendar();
        SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        this.txtFechaEntrada.setText(formatDate.format(cal.getTime()));

        this.txtObservacion.setText("");
    }

    /**
     * Carga en los controles los datos de la salida cuando se trata de horas
     *
     */
    private void cargarDatosSalidaHoras() {
        //objEntSal del metodo buscarEntradaSalida() guarda toda la información de la entrada salida

        this.cboTipoVehiculo.setSelectedItem(GUIEntradasSalidas.TIPOS_VEHICULO.get(objEntSal.getPlaca().getTipo()));
        this.txtPlaca.setText(objEntSal.getPlaca().getPlaca());
        this.txtPropietario.setText(objEntSal.getPlaca().getPropietario());
        //Cargar fecha entrada, fecha salida y fecha/hora actual
        this.ahora = new GregorianCalendar().getTime();
        SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        this.txtFechaEntrada.setText(formatDate.format(objEntSal.getFechaHoraEntrada()));

        //Fecha salida es la fecha actual
        this.txtFechaSalida.setText(formatDate.format(ahora));

        //Cargar observación
        String observacion = this.objEntSal.getObservacion();
        if (this.objEntSal.getObservacion() == null) {
            observacion = "";
        }
        this.txtObservacion.setText(observacion);

        this.chkLlaves.setSelected(objEntSal.getDejaLlaves());
        this.txtCantidadCascos.setText(objEntSal.getCascosNro());
        this.txtCasillero.setText(objEntSal.getCasilleroNro());

        TiempoTranscurrido tc = Utilidades.calcularTiempoTranscurrido(objEntSal.getFechaHoraEntrada(), ahora);
        this.txtTiempoTranscurrido.setText(tc.getHoras() + " horas " + tc.getMinutos() + " min.");
        this.horas = tc.getHoras();
        calcularValorPagarSalidaHoras();
    }

    /**
     * Procesa la logica de presentación cuando es una SALIDA de vehículo que ya
     * tiene un pago mensual
     */
    private void procesarSalidaMes(EntradasSalidasMensuales objEntSalMensual) throws ParseException {
        Date fechaInicio = objEntSalMensual.getPagosMensualesId().getFechaInicio();
        Date fechaFin = objEntSalMensual.getPagosMensualesId().getFechaFin();
        //Si la fechaActual está dentro del mes entonces
        if (Utilidades.fechaEstaDentroDe(new Date(), fechaInicio, fechaFin)) {
            //Está dentro del mes
            //Cuando grabe, se debe registrar la fecha y hora salida en la tabla entradas_salidas_mensuales.                     

            estadoControlesRegistrarSalidaMes();
            cargarDatosSalidaMes(objEntSalMensual);
            caso = CASO_SALIDA_MENSUAL;
        } else {
            //Caso PUCHITO
            //Se registra la fecha y hora de salida en la tabla entradas_salidas_mensuales. 
            //Cuando se grabe, se debe crear un registro en entradas_salidas con un valor a pagar correspondiente a
            //las horas que se pasó
            this.ahora = new Date();
            TiempoTranscurrido tc = Utilidades.calcularTiempoTranscurrido(fechaFin, ahora);

            this.valorPagar = logica.calcularValorPagarSalidaHoras(TIPOS_VEHICULO.get(objEntSalMensual.getPagosMensualesId().getPlaca().getTipo()), fechaFin, ahora);
            txtValorPagar.setText("" + this.valorPagar);
            estadoControlesRegistrarSalidaMes();
            estadoControlesActivarPagoExtra();
            cargarDatosSalidaMes(objEntSalMensual);

            //Este mensaje sobreescribe el label de cargarSatosSalidaMes()
            StringBuilder sb = new StringBuilder(64);
            sb.append("<html>" + lblMensaje.getText() + "<br>La salida se pasó unas horas del límite, de deben cobrar " + tc.getHoras() + " horas " + tc.getMinutos() + " minutos: " + valorPagar + "</html>");
            lblMensaje.setText(sb.toString());

            puchito = new Puchito();
            puchito.setFechaInicio(fechaFin);
            puchito.setFechaFin(ahora);
            caso = CASO_SALIDA_MENSUAL_CON_PUCHITO;
        }
    }

    /**
     * Procesa la logica cuando es una SALIDA de vehiculo por HORAS
     */
    private void procesarSalidaHoras() {
        //pnlCentro.setBorder(javax.swing.BorderFactory.createTitledBorder(this.SALIDA_TITULO));
        estadoControlesRegistrarSalidaHoras();
        cargarDatosSalidaHoras();

    }

    private void procesarEntradaHoras(String placa) {
        estadoControlesRegistrarEntradaHoras();
        cargarDatosEntradaHoras();
    }

    /**
     * Logica cuando cuando es una ENTRADA que ya tiene pagado el MES
     *
     *
     * @param objPagoMes
     */
    private void procesarEntradaConPagoMensual(PagosMensuales objPagoMes) {
        //Hay un pago de mes
        Date fechaMasMes = Utilidades.sumarMesAFecha(objPagoMes.getFechaInicio());
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE MMMM dd yyyy");
        lblMensaje.setText("El vehiculo tiene vigente un pago mensual hasta el " + sdf.format(fechaMasMes));

        cboTipoVehiculo.setSelectedItem(TIPOS_VEHICULO.get(objPagoMes.getPlaca().getTipo()));
        cboTipoVehiculo.setEnabled(false);

        if (Utilidades.fechaEstaDentroDe(new Date(), objPagoMes.getFechaInicio(), objPagoMes.getFechaFin())) {
            //La entrada está dentro del mes pagado
            estadoControlesRegistrarEntradaMes();
            cargarDatosEntradaHoras();

        } else {
            //La entrada está posterior al mes pagado, se convierte en una entrada normal
            lblMensaje.setText("El vehiculo tuvo un contrato de mes el " + Utilidades.formatoFecha(objPagoMes.getFechaFin()) + ", pero se venció, se cobrará una nueva tarifa");
            estadoControlesRegistrarEntradaHoras();
            cargarDatosEntradaHoras();
            cboTipoVehiculo.setEnabled(true);
        }

    }

    /**
     * Carga en los controles los datos de la salida, que fueron grabado en la
     * entrada, cuando hay un mes contratado
     *
     */
    private void cargarDatosSalidaMes(EntradasSalidasMensuales objEntSalMensual) {

        //Poner la nota
        Date fechaFin = objEntSalMensual.getPagosMensualesId().getFechaFin();
        String strFechaFin = Utilidades.formatoFecha(fechaFin);
        lblMensaje.setText("Nota: El vehiculo tiene vigente un pago mensual hasta el " + strFechaFin);

        cboTipoVehiculo.setSelectedItem(GUIEntradasSalidas.TIPOS_VEHICULO.get(objEntSalMensual.getPagosMensualesId().getPlaca().getTipo()));

        txtPropietario.setText(objEntSalMensual.getPagosMensualesId().getPlaca().getPropietario());

        //Cargar fecha entrada, fecha salida y fecha/hora actual
        this.ahora = new GregorianCalendar().getTime();
        SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        this.txtFechaEntrada.setText(formatDate.format(objEntSalMensual.getFechaHoraEntrada()));
        //Cargar fecha salida, fecha actual
        this.txtFechaSalida.setText(formatDate.format(ahora));

        //Cargar observación
        String observacion = this.objEntSalMensual.getObservacion();
        if (this.objEntSalMensual.getObservacion() == null) {
            observacion = "";
        }
        this.txtObservacion.setText(observacion);
        TiempoTranscurrido tc = Utilidades.calcularTiempoTranscurrido(objEntSalMensual.getFechaHoraEntrada(), ahora);
        this.txtTiempoTranscurrido.setText(tc.getHoras() + " horas " + tc.getMinutos() + " min.");

        this.chkLlaves.setSelected(objEntSalMensual.getDejaLlaves());

    }

    /**
     * Calcula el valor a pagar cuando la tarifa es por horas
     *
     */
    private void calcularValorPagarSalidaHoras() {
        String vehiculo = cboTipoVehiculo.getSelectedItem().toString();
        Date fechaHoraEntrada = Utilidades.stringToDate(txtFechaEntrada.getText());
        Date fechaHoraSalida = Utilidades.stringToDate(txtFechaSalida.getText());

        try {
            this.valorPagar = logica.calcularValorPagarSalidaHoras(vehiculo, fechaHoraEntrada, fechaHoraSalida);
        } catch (ParseException ex) {
            Logger.getLogger(GUIEntradasSalidas.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (txtDescuento.getText().trim().equals("")) {
            txtDescuento.setText("0");
        }
        long descuento = Long.parseLong(this.txtDescuento.getText());
        if (descuento > valorPagar) {
            mensajeAdvertencia("El descuento no puede ser mayor que el valor a pagar", "Atención");
            txtDescuento.setText("0");
            txtDescuento.requestFocus();
            return;
        }
        long valorPagarDespuesDescuento = valorPagar - descuento;
        this.txtValorPagar.setText("" + valorPagarDespuesDescuento);

        //Calcular cambio
        if (txtEfectivo.getText().equals("")) {
            txtCambio.setText("0");
            txtEfectivo.setText("0");
        }
        if (txtEfectivo.getText().equals("0")) {
            return;
        }
        long efectivo = Long.parseLong(txtEfectivo.getText());
        long cambio = efectivo - valorPagarDespuesDescuento;
        if (cambio < 0) {
            mensajeAdvertencia("El efectivo debe ser mayor o igual que el valor a pagar", "Atención");
            return;
        }
        txtCambio.setText("" + cambio);
    }

    /**
     * Calcula el valor cuando es una entrada y la tarifa es mensual
     */
    private void calcularValorPagarEntradaMes() {
        String vehiculo = cboTipoVehiculo.getSelectedItem().toString();

        try {
            this.valorPagar = logica.calcularValorPagarEntradaMensual(vehiculo);
        } catch (ParseException ex) {
            Logger.getLogger(GUIEntradasSalidas.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (txtDescuento.getText().trim().equals("")) {
            txtDescuento.setText("0");
        }
        long descuento = Long.parseLong(this.txtDescuento.getText());
        if (descuento > valorPagar) {
            mensajeAdvertencia("El descuento no puede ser mayor que el valor a pagar", "Atención");
            txtDescuento.setText("0");
            txtDescuento.requestFocus();
            return;
        }
        long valorPagarDespuesDescuento = valorPagar - descuento;
        this.txtValorPagar.setText("" + valorPagarDespuesDescuento);

        //Calcular cambio
        if (txtEfectivo.getText().equals("")) {
            txtCambio.setText("0");
            txtEfectivo.setText("0");
        }
        if (txtEfectivo.getText().equals("0")) {
            return;
        }
        long efectivo = Long.parseLong(txtEfectivo.getText());
        long cambio = efectivo - valorPagarDespuesDescuento;
        if (cambio < 0) {
            mensajeAdvertencia("El efectivo debe ser mayor o igual que el valor a pagar", "Atención");
            return;
        }
        txtCambio.setText("" + cambio);
    }

    /**
     * Llena los tipos de vehículo
     */
    private void llenarCboTiposVehiculo() {

        DefaultComboBoxModel modeloCombo = new DefaultComboBoxModel();
        modeloCombo.addElement(SELECCIONE);
        modeloCombo.addElement("Carro");
        modeloCombo.addElement("Moto");
        cboTipoVehiculo.setModel(modeloCombo);
    }


    private void cboTipoVehiculoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboTipoVehiculoItemStateChanged
        if (!cboTipoVehiculo.isEnabled() || !this.pnlCentro.isVisible()) {
            return;
        }
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            //Do any operations you need to do when an item is selected.
            habilitarCascosCasillero();
            aplicarTarifa();
        } else if (evt.getStateChange() == ItemEvent.DESELECTED) {
            //Do any operations you need to do when an item is de-selected.
            //Do any operations you need to do when an item is selected.
        }

    }//GEN-LAST:event_cboTipoVehiculoItemStateChanged

    private void habilitarCascosCasillero() {
        txtCantidadCascos.setEnabled(false);
        txtCasillero.setEnabled(false);
        txtCantidadCascos.setText("");
        txtCasillero.setText("");
        if (cboTipoVehiculo.getSelectedIndex() == 2) {
            //Motos
            txtCantidadCascos.setEnabled(true);
            txtCasillero.setEnabled(true);
        }

    }
    private void btnGrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGrabarActionPerformed

        if (txtPlaca.getText().equals("-")) {
            Utilidades.mensajeAdvertencia("Falta llenar la placa", "Atención");
            txtPlaca.requestFocus();
            return;
        }
        if (cboTipoVehiculo.getSelectedItem().toString().equals(SELECCIONE)) {
            mensajeAdvertencia("Falta seleccionar el tipo de vehículo", "Atención");
            cboTipoVehiculo.requestFocus();
            return;
        }

        txtPropietario.setText(Utilidades.capitalize(txtPropietario.getText()));

        //Determinar qué caso debe grabar
        switch (caso) {
            case CASO_ENTRADA_HORAS:
                grabarEntradaHoras();
                break;
            case CASO_ENTRADA_MENSUAL:
                //Entrada que ya tiene un pago mensual
                grabarEntradaMensual();
                break;

            case CASO_SALIDA_HORAS:
                grabarSalidaHoras();
                break;
            case CASO_SALIDA_MENSUAL:
                grabarSalidaMensual();
                break;
            case CASO_SALIDA_MENSUAL_CON_PUCHITO:
                grabarSalidaMensualPuchito();
                break;
        }
        txtCodigoBuscar.setText("");// Estos casos van por fuera del estadoControlesEmpezarTransaccion()
        txtPlacaBuscar.setText("");

    }//GEN-LAST:event_btnGrabarActionPerformed

    /**
     * Graba la entrada en la bd cuando la tarifa es por horas. No hay valor a
     * pagar
     */
    private void grabarEntradaHoras() {
        String placa = txtPlaca.getText().trim();

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        Date fechaHoraEntrada;
        try {
            fechaHoraEntrada = format.parse(txtFechaEntrada.getText());
        } catch (ParseException ex) {
            mensajeAdvertencia("Error con la fecha/hora", "Atención");
            Logger.getLogger(GUIEntradasSalidas.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        int valorPagar = 0;

        int descuento = 0;

        String observacion = txtObservacion.getText();

        int empleadoId;
        if (Utilidades.empleadoSesion == null) {
            empleadoId = 1;
        } else {
            empleadoId = Utilidades.empleadoSesion.getId();
        }

        boolean dejaLlaves = true;

        String fichaNro = txtCodigoBuscar.getText().trim(); //o código de barras

        String propietario = txtPropietario.getText();

        String cascosNro = txtCantidadCascos.getText();

        String casilleroNro = txtCasillero.getText();

        try {
            logica.grabarEntradaHoras(placa, cboTipoVehiculo.getSelectedItem().toString(), propietario, fechaHoraEntrada, descuento, valorPagar, observacion, empleadoId, cascosNro, casilleroNro, dejaLlaves, fichaNro);
        } catch (Exception ex) {
            mensajeError("Error al grabar la entrada\n" + ex.getMessage(), "Lo siento");
            Logger.getLogger(GUIEntradasSalidas.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        mensajeExito("Entrada grabada con éxito!", "Atención");
        estadoControlesEmpezarTransaccion();
        txtCodigoBuscar.setText("");
    }

    /**
     * Logica de presentación cuando es una entrada y ya hay un pago. No se hace
 pago alguno, sólo se actualiza la fecha de entrada, cascosNro, llaves, ficha
 y observaciones
     */
    private void grabarEntradaMensual() {
        String placa = txtCodigoBuscar.getText().trim();

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        Date fechaHoraEntrada;
        try {
            fechaHoraEntrada = format.parse(txtFechaEntrada.getText());
        } catch (ParseException ex) {
            mensajeAdvertencia("Error con la fecha/hora", "Atención");
            Logger.getLogger(GUIEntradasSalidas.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        String observacion = txtObservacion.getText();

        int empleadoEntradaId;
        if (Utilidades.empleadoSesion == null) {
            empleadoEntradaId = 1;
        } else {
            empleadoEntradaId = Utilidades.empleadoSesion.getId();
        }
        boolean dejaCasco = true; //Arreglar
        boolean dejaLlaves = this.chkLlaves.isSelected();

        String ficha = txtCodigoBuscar.getText().trim(); //ARREGLAR

        String propietario = txtPropietario.getText();

        try {
            logica.grabarEntradaMensual(this.objPagoMes, placa, cboTipoVehiculo.getSelectedItem().toString(), propietario, fechaHoraEntrada, observacion, empleadoEntradaId, dejaCasco, dejaLlaves, ficha); // 1: Hugo Castro        
        } catch (Exception ex) {
            mensajeError("Error al grabar la entrada\n" + ex.getMessage(), "Lo siento");
            Logger.getLogger(GUIEntradasSalidas.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        mensajeExito("Entrada grabada con éxito!", "Atención");
        estadoControlesEmpezarTransaccion();
        txtCodigoBuscar.setText("");

    }


    /**
     * Graba la salida cuando es una tarifa por horas. Involucra un valor a
     * pagar
     */
    private void grabarSalidaHoras() {
        try {
            //Placa
            String placa = txtCodigoBuscar.getText().trim();

            //Fecha y hora de entrada
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
            Date fechaHoraEntrada;
            try {
                fechaHoraEntrada = format.parse(txtFechaEntrada.getText());
            } catch (ParseException ex) {
                mensajeAdvertencia("Error con la fecha/hora", "Atención");
                Logger.getLogger(GUIEntradasSalidas.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }

            //Fecha y hora de salida
            format = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
            Date fechaHoraSalida;
            try {
                fechaHoraSalida = format.parse(txtFechaSalida.getText());
            } catch (ParseException ex) {
                mensajeAdvertencia("Error con la fecha/hora salida", "Atención");
                Logger.getLogger(GUIEntradasSalidas.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }

            //Descuento
            int descuento = 0;
            if (!txtDescuento.getText().trim().equals("")) {
                descuento = Integer.parseInt(txtDescuento.getText());
            }

            //Valor Pagar
            int valorPagar = 0;
            if (!txtValorPagar.getText().trim().equals("")) {
                valorPagar = Integer.parseInt(txtValorPagar.getText());
            }

            //Observacion
            String observacion = txtObservacion.getText();

            //Grabar
            int empleadoSalidaId = 0;
            if (Utilidades.empleadoSesion == null) {
                empleadoSalidaId = 1;
            } else {
                empleadoSalidaId = Utilidades.empleadoSesion.getId();
            }

            boolean extravioTarjeta = chkExtravioTarjeta.isSelected();

            logica.grabarSalidaHoras(this.objEntSal, fechaHoraSalida, descuento, valorPagar, observacion, extravioTarjeta, empleadoSalidaId);// 1: Hugo Castro

            mensajeExito("Salida grabada con éxito!", "Atención");
            estadoControlesEmpezarTransaccion();
            txtCodigoBuscar.setText("");
        } catch (Exception ex) {
            mensajeAdvertencia("Error al grabar la salida!", "Atención");
            Logger.getLogger(GUIEntradasSalidas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Logica de presentación cuando es una salida que ya tiene un pago mensual
     */
    private void grabarSalidaMensual() {
        try {
            //Placa
            String placa = txtCodigoBuscar.getText().trim();

            //Fecha y hora de salida
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
            format = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
            Date fechaHoraSalida;
            try {
                fechaHoraSalida = format.parse(txtFechaSalida.getText());
            } catch (ParseException ex) {
                mensajeAdvertencia("Error con la fecha/hora salida", "Atención");
                Logger.getLogger(GUIEntradasSalidas.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }

            //Observacion
            String observacion = txtObservacion.getText();

            //Grabar
            int empleadoSalidaId = 0;
            if (Utilidades.empleadoSesion == null) {
                empleadoSalidaId = 1;
            } else {
                empleadoSalidaId = Utilidades.empleadoSesion.getId();
            }
            logica.grabarSalidaMensual(this.objEntSalMensual, fechaHoraSalida, observacion, empleadoSalidaId);// 1: Hugo Castro

            mensajeExito("Salida grabada con éxito!", "Atención");
            estadoControlesEmpezarTransaccion();
            txtCodigoBuscar.setText("");
        } catch (Exception ex) {
            mensajeAdvertencia("Error al grabar la salida!\n " + ex.getMessage(), "Atención");
            Logger.getLogger(GUIEntradasSalidas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Graba un registro en la tabla entradas_salidas con el puchito del caso
     * salida mensual con puchito
     */
    private void grabarSalidaMensualPuchito() {
        try {
            //Placa
            String placa = txtCodigoBuscar.getText().trim();

            //Fecha y hora de salida
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
            format = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
            Date fechaHoraSalida;
            try {
                fechaHoraSalida = format.parse(txtFechaSalida.getText());
            } catch (ParseException ex) {
                mensajeAdvertencia("Error con la fecha/hora salida", "Atención");
                Logger.getLogger(GUIEntradasSalidas.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }

            //Fecha y hora de entrada
            Date fechaHoraEntrada;
            try {
                fechaHoraEntrada = format.parse(txtFechaEntrada.getText());
            } catch (ParseException ex) {
                mensajeAdvertencia("Error con la fecha/hora entrada", "Atención");
                Logger.getLogger(GUIEntradasSalidas.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }

            //Descuento
            int descuento = 0;
            if (!txtDescuento.getText().trim().equals("")) {
                descuento = Integer.parseInt(txtDescuento.getText());
            }

            //Valor Pagar
            int valorPagar = 0;
            if (!txtValorPagar.getText().trim().equals("")) {
                valorPagar = Integer.parseInt(txtValorPagar.getText());
            }

            //Observacion
            String observacion = txtObservacion.getText();

            //EMPLEADO
            int empleadoEntradaId = 0;
            if (Utilidades.empleadoSesion == null) {
                empleadoEntradaId = 1;
            } else {
                empleadoEntradaId = Utilidades.empleadoSesion.getId();
            }
            //Graba el puchito en la tabla EntradasSalidas
            logica.grabarPuchito(placa, fechaHoraEntrada, fechaHoraSalida, descuento, valorPagar, observacion, empleadoEntradaId);// 1: Hugo Castro

            //Actualiza el registro en la tabla EntradasSalidasMensuales
            logica.grabarSalidaMensual(this.objEntSalMensual, fechaHoraSalida, observacion, empleadoEntradaId);// 1: Hugo Castro

            mensajeExito("Salida grabada con éxito!", "Atención");
            estadoControlesEmpezarTransaccion();
            txtCodigoBuscar.setText("");
        } catch (Exception ex) {
            mensajeAdvertencia("Error al grabar la salida!\n " + ex.getMessage(), "Atención");
            Logger.getLogger(GUIEntradasSalidas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void txtEfectivoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEfectivoKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            calcularValorPagarSalidaHoras();

        }
    }//GEN-LAST:event_txtEfectivoKeyPressed

    private void txtDescuentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescuentoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            calcularValorPagarSalidaHoras();
        }
    }//GEN-LAST:event_txtDescuentoKeyPressed

    private void btnDescuentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDescuentoActionPerformed
        // TODO add your handling code here:
        /*
        if (cboTarifa.getSelectedItem().equals("Por hora")) {
            calcularValorPagarSalidaHoras();
        } else {
            calcularValorPagarEntradaMes();
        }*/
        aplicarDescuentosYExtravioTarjeta();

    }//GEN-LAST:event_btnDescuentoActionPerformed

    private void btnEfectivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEfectivoActionPerformed
        aplicarDescuentosYExtravioTarjeta();

    }//GEN-LAST:event_btnEfectivoActionPerformed
    /**
     * Una vez calculado el valor a pagar, según el caso, se aplican descuentos
     * y la tarifa en caso que haya extraviado la tarjeta
     */
    private void aplicarDescuentosYExtravioTarjeta() {

        if (txtDescuento.getText().trim().equals("")) {
            txtDescuento.setText("0");
        }
        long descuento = Long.parseLong(this.txtDescuento.getText());
        if (descuento > valorPagar) {
            mensajeAdvertencia("El descuento no puede ser mayor que el valor a pagar", "Atención");
            txtDescuento.setText("0");
            txtDescuento.requestFocus();
            return;
        }
        // Calcular si extravió la tarjeta
        long tarifaExtravioTarjeta = 0;
        if (chkExtravioTarjeta.isSelected()) {
            tarifaExtravioTarjeta = logica.tarifaExtravioTarjeta();
        }
        long valorPagarDespuesDescuento = this.valorPagar - descuento + tarifaExtravioTarjeta;
        this.txtValorPagar.setText("" + valorPagarDespuesDescuento);

        //Calcular cambio
        if (txtEfectivo.getText().equals("")) {
            txtCambio.setText("0");
            txtEfectivo.setText("0");
        }
        if (txtEfectivo.getText().equals("0")) {
            return;
        }
        long efectivo = Long.parseLong(txtEfectivo.getText());
        long cambio = efectivo - valorPagarDespuesDescuento;
        if (cambio < 0) {
            mensajeAdvertencia("El efectivo debe ser mayor o igual que el valor a pagar", "Atención");
            return;
        }
        txtCambio.setText("" + cambio);

    }

    private void aplicarExtravioTarjeta() {
        //tarifaExtravioTarjeta = Integer.parseInt(prop.getProperty("extravioTaejeta")); // 5000

    }
    private void chkLlavesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkLlavesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkLlavesActionPerformed

    private void cboTipoVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTipoVehiculoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboTipoVehiculoActionPerformed

    private void txtPlacaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPlacaFocusLost

        txtPlaca.setText(txtPlaca.getText().toUpperCase());
    }//GEN-LAST:event_txtPlacaFocusLost

    private void txtValorPagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValorPagarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorPagarActionPerformed

    private void rbtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtCodigoActionPerformed
        txtPlacaBuscar.setVisible(false);
        txtCodigoBuscar.setVisible(true);
        txtPlacaBuscar.setText("");
        txtCodigoBuscar.requestFocus();
        pnlNorte.revalidate(); //Refresca el panel
    }//GEN-LAST:event_rbtCodigoActionPerformed

    private void rbtPlacaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtPlacaActionPerformed
        txtCodigoBuscar.setVisible(false);
        txtPlacaBuscar.setVisible(true);
        txtCodigoBuscar.setText("");
        txtPlacaBuscar.requestFocus();
        pnlNorte.revalidate(); //Refresca el panel

    }//GEN-LAST:event_rbtPlacaActionPerformed

    private void txtPlacaBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPlacaBuscarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnBuscar.requestFocus();
            btnBuscarActionPerformed(null);
        }
    }//GEN-LAST:event_txtPlacaBuscarKeyPressed

    private void txtCantidadCascosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadCascosKeyTyped
        if (txtCantidadCascos.getText().length() == 1) { //1: limite
            evt.consume();
        }
    }//GEN-LAST:event_txtCantidadCascosKeyTyped

    private void txtCasilleroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCasilleroKeyTyped
        if (txtCasillero.getText().length() == 10) { //4: limite
            evt.consume();
        }
    }//GEN-LAST:event_txtCasilleroKeyTyped

    private void chkExtravioTarjetaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkExtravioTarjetaItemStateChanged
        this.aplicarDescuentosYExtravioTarjeta();
    }//GEN-LAST:event_chkExtravioTarjetaItemStateChanged

    private void txtCodigoBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoBuscarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnBuscarActionPerformed(null);
        }
    }//GEN-LAST:event_txtCodigoBuscarKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnDescuento;
    private javax.swing.JButton btnEfectivo;
    private javax.swing.JButton btnGrabar;
    private javax.swing.JButton btnRecibo;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboTipoVehiculo;
    private javax.swing.JCheckBox chkExtravioTarjeta;
    private javax.swing.JCheckBox chkLlaves;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblCambio;
    private javax.swing.JLabel lblCantidadCascos;
    private javax.swing.JLabel lblCasillero;
    private javax.swing.JLabel lblDescuento;
    private javax.swing.JLabel lblEfectivo;
    private javax.swing.JLabel lblFechaSalida;
    private javax.swing.JLabel lblMensaje;
    private javax.swing.JLabel lblObservacion;
    private javax.swing.JLabel lblPlaca;
    private javax.swing.JLabel lblPropietario;
    private javax.swing.JLabel lblTiempoTranscurrido;
    private javax.swing.JLabel lblTipoVehiculo;
    private javax.swing.JLabel lblValorPagar;
    private javax.swing.JPanel pnlCentro;
    private javax.swing.JPanel pnlNorte;
    private javax.swing.JPanel pnlSur;
    private javax.swing.JRadioButton rbtCodigo;
    private javax.swing.JRadioButton rbtPlaca;
    private javax.swing.JTextField txtCambio;
    private javax.swing.JTextField txtCantidadCascos;
    private javax.swing.JTextField txtCasillero;
    private javax.swing.JPasswordField txtCodigoBuscar;
    private javax.swing.JTextField txtDescuento;
    private javax.swing.JTextField txtEfectivo;
    private javax.swing.JTextField txtFechaEntrada;
    private javax.swing.JTextField txtFechaSalida;
    private javax.swing.JTextField txtObservacion;
    private javax.swing.JFormattedTextField txtPlaca;
    private javax.swing.JFormattedTextField txtPlacaBuscar;
    private javax.swing.JTextField txtPropietario;
    private javax.swing.JTextField txtTiempoTranscurrido;
    private javax.swing.JTextField txtValorPagar;
    // End of variables declaration//GEN-END:variables
}
