/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gyan.parqueadero.interfaz;

import com.gyan.parqueadero.entidades.GastosMensuales;
import com.gyan.parqueadero.entidades.TiposGastos;
import com.gyan.parqueadero.logicanegocio.GastosMensualesLogicaNegocio;
import com.gyan.parqueadero.utilidades.Utilidades;
import static com.gyan.parqueadero.utilidades.Utilidades.mensajeAdvertencia;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author libardo
 */
public class GUIGastos extends javax.swing.JInternalFrame {

    public static final String SELECCIONE = "--Seleccione--";

    /**
     * Una sóla instancia para acceder a la lógica del negocio
     */
    private GastosMensualesLogicaNegocio logica;

    /**
     * Constructor por defecto
     */
    public GUIGastos() {
        initComponents();
        //setClosable(true);
        this.setMaximizable(true);
        this.setResizable(true);
        this.setSize(900, 700);
        
        logica = new GastosMensualesLogicaNegocio();
        llenarCboTiposVehiculo();
        inicializarTabla();
        estadoControlesInicial();
    }
    
    GUIGastos(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void inicializarTabla() {
        tblDatos.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Id", "Fecha", "TipoGasto", "Valor", "Descripción"
                }
        ));
    }
    
    private void llenarCboTiposVehiculo() {
        
        DefaultComboBoxModel modeloCombo = new DefaultComboBoxModel();
        modeloCombo.addElement(SELECCIONE);
        List<TiposGastos> lista = logica.getTiposGastos();
        
        for (TiposGastos obj : lista) {
            modeloCombo.addElement("[" + obj.getId() + "]" + " " + obj.getDescripcion());
            
        }
        
        cboTipoGastos.setModel(modeloCombo);
    }
    
    private void estadoControlesInicial() {
        
        btnGrabar.setEnabled(false);
        
    }
    
    private void EstadoControlesEditar() {
        btnGrabar.setText("Editar");
        btnGrabar.setEnabled(true);
    }
    
    private void EstadoControlesAgregar() {
        btnGrabar.setText("Agregar");
        btnGrabar.setEnabled(true);
    }
    
    private void limpiarCajas() {
        cboTipoGastos.setSelectedIndex(0);
        txtFecha.setDate(null);
        txtFechaFinal.setDate(null);
        txtValor.setText("");
        txtDescripcion.setText("");
        
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        pnlAgregar = new javax.swing.JPanel();
        pnlCentro = new javax.swing.JPanel();
        lblTipoGasto = new javax.swing.JLabel();
        cboTipoGastos = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        lblValorPagar = new javax.swing.JLabel();
        lblObservacion = new javax.swing.JLabel();
        txtValor = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        txtFecha = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        pnlSur = new javax.swing.JPanel();
        btnGrabar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        pnlConsultar = new javax.swing.JPanel();
        pnlBuscar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtFechaInicial = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        txtFechaFinal = new com.toedter.calendar.JDateChooser();
        btnBuscar = new javax.swing.JButton();
        pnlTabla = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDatos = new javax.swing.JTable();
        pnlTotal = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtTotalRegistros = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtTotalGastos = new javax.swing.JTextField();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gastos");

        pnlAgregar.setLayout(new java.awt.BorderLayout());

        pnlCentro.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del gasto"));
        pnlCentro.setLayout(new java.awt.GridBagLayout());

        lblTipoGasto.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        lblTipoGasto.setText("Tipo de gasto:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        pnlCentro.add(lblTipoGasto, gridBagConstraints);

        cboTipoGastos.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        cboTipoGastos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        pnlCentro.add(cboTipoGastos, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        jLabel2.setText("Fecha ");
        jLabel2.setToolTipText("dd-mm-aaaa hh:mm am-pm");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        pnlCentro.add(jLabel2, gridBagConstraints);

        lblValorPagar.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        lblValorPagar.setText("Valor $:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        pnlCentro.add(lblValorPagar, gridBagConstraints);

        lblObservacion.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        lblObservacion.setText("Descripción (opcional):");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        pnlCentro.add(lblObservacion, gridBagConstraints);

        txtValor.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        txtValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValorActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        pnlCentro.add(txtValor, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        jLabel3.setText("Id:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        pnlCentro.add(jLabel3, gridBagConstraints);

        txtId.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        txtId.setPreferredSize(new java.awt.Dimension(50, 36));
        txtId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtIdFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        pnlCentro.add(txtId, gridBagConstraints);

        txtFecha.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        txtFecha.setPreferredSize(new java.awt.Dimension(150, 32));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        pnlCentro.add(txtFecha, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        pnlCentro.add(jLabel4, gridBagConstraints);

        txtDescripcion.setColumns(20);
        txtDescripcion.setRows(5);
        jScrollPane2.setViewportView(txtDescripcion);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        pnlCentro.add(jScrollPane2, gridBagConstraints);

        pnlAgregar.add(pnlCentro, java.awt.BorderLayout.CENTER);

        pnlSur.setBorder(javax.swing.BorderFactory.createTitledBorder("Opciones"));

        btnGrabar.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        btnGrabar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/grabar.png"))); // NOI18N
        btnGrabar.setText("Agregar");
        btnGrabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGrabarActionPerformed(evt);
            }
        });
        pnlSur.add(btnGrabar);

        btnCerrar.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/cerrar.png"))); // NOI18N
        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        pnlSur.add(btnCerrar);

        pnlAgregar.add(pnlSur, java.awt.BorderLayout.SOUTH);

        jTabbedPane1.addTab("Agregar", pnlAgregar);

        pnlConsultar.setLayout(new java.awt.BorderLayout());

        pnlBuscar.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel1.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        jLabel1.setText("Fecha Inicial");
        pnlBuscar.add(jLabel1);

        txtFechaInicial.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        txtFechaInicial.setPreferredSize(new java.awt.Dimension(150, 32));
        pnlBuscar.add(txtFechaInicial);

        jLabel5.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        jLabel5.setText("Fecha Final");
        pnlBuscar.add(jLabel5);

        txtFechaFinal.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        txtFechaFinal.setPreferredSize(new java.awt.Dimension(150, 32));
        pnlBuscar.add(txtFechaFinal);

        btnBuscar.setFont(new java.awt.Font("DejaVu Sans", 0, 16)); // NOI18N
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/buscar.png"))); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.setIconTextGap(2);
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        pnlBuscar.add(btnBuscar);

        pnlConsultar.add(pnlBuscar, java.awt.BorderLayout.PAGE_START);

        pnlTabla.setLayout(new java.awt.BorderLayout());

        tblDatos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblDatos);

        pnlTabla.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pnlConsultar.add(pnlTabla, java.awt.BorderLayout.CENTER);

        pnlTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlTotal.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel6.setText("Total registros: ");
        pnlTotal.add(jLabel6);

        txtTotalRegistros.setText("000000000");
        txtTotalRegistros.setPreferredSize(new java.awt.Dimension(110, 32));
        pnlTotal.add(txtTotalRegistros);

        jLabel8.setText("Total Gastos $:");
        pnlTotal.add(jLabel8);

        txtTotalGastos.setEditable(false);
        txtTotalGastos.setText("00000000000000");
        pnlTotal.add(txtTotalGastos);

        pnlConsultar.add(pnlTotal, java.awt.BorderLayout.SOUTH);

        jTabbedPane1.addTab("Consultar", pnlConsultar);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        
        Date fechaInicial = txtFechaInicial.getDate();
        
        if (fechaInicial == null) {
            Utilidades.mensajeAdvertencia("Debe seleccionar la fecha inicial", "Atención");
            txtFechaInicial.requestFocus();
            return;
        }
        
        Date fechaFinal = txtFechaFinal.getDate();
        
        if (fechaFinal == null) {
            Utilidades.mensajeAdvertencia("Debe seleccionar la fecha final", "Atención");
            txtFechaFinal.requestFocus();
            return;
        }
        
        buscarGastosPorFecha(fechaInicial, fechaFinal);
        

    }//GEN-LAST:event_btnBuscarActionPerformed
    
    private void buscarGastosPorFecha(Date fecha1, Date fecha2) {
        
        List<GastosMensuales> result = logica.getGastosMensualesPorFecha(fecha1, fecha2);
        inicializarTabla();
        DefaultTableModel model = (DefaultTableModel) tblDatos.getModel();
        
        Object rowData[] = new Object[5];//No columnas
        int totalGastos = 0;
        for (int i = 0; i < result.size(); i++) {
            rowData[0] = result.get(i).getId();
            rowData[1] = Utilidades.formatoFechaSinHora(result.get(i).getFecha());
            rowData[2] = "[" + result.get(i).getTiposGastosId().getId() + "]" + " " + result.get(i).getTiposGastosId().getDescripcion();
            rowData[3] = Utilidades.formatoMoneda(result.get(i).getValor());
            rowData[4] = result.get(i).getDescripcion();
            model.addRow(rowData);
            totalGastos += result.get(i).getValor();
        }
        txtTotalRegistros.setText("" + result.size());
        txtTotalGastos.setText(Utilidades.formatoMoneda(totalGastos));
    }

    private void btnGrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGrabarActionPerformed
        
        String tipoGasto = cboTipoGastos.getSelectedItem().toString();
        
        if (tipoGasto.equals(SELECCIONE)) {
            mensajeAdvertencia("Falta seleccionar el tipo de gasto", "Atención");
            cboTipoGastos.requestFocus();
            return;
        }
        
        int tipoGastoId = Integer.parseInt(Utilidades.extraerId(tipoGasto));
        
        Date fecha = txtFecha.getDate();
        if (fecha == null) {
            mensajeAdvertencia("Entre una fecha", "Atención");
            txtFecha.requestFocus();
            return;
        }
        
        int valor = 0;
        if (!txtValor.getText().trim().equals("")) {
            valor = Integer.parseInt(txtValor.getText());
        }
        
        String descripcion = txtDescripcion.getText();
        
        try {
            if (btnGrabar.getText().equals("Agregar")) {
                int id = logica.agregarGasto(tipoGastoId, fecha, valor, descripcion);
                Utilidades.mensajeExito("Gasto grabado con éxito con el id: " + id, "Atención");
            } else {
                logica.editarGasto(Integer.parseInt(txtId.getText()), tipoGastoId, fecha, valor, descripcion);
                Utilidades.mensajeExito("Gasto editado con éxito", "Atención");
            }
            
        } catch (Exception ex) {
            Utilidades.mensajeError("Error al grabar Gasto. \n" + ex.getMessage(), "Atención");
            Logger.getLogger(GUIGastos.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        estadoControlesInicial();
        limpiarCajas();
        txtId.setText("");
        

    }//GEN-LAST:event_btnGrabarActionPerformed
    

    private void txtValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorActionPerformed

    private void txtIdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdFocusLost
        
        String id = txtId.getText().trim();
        if (id.equals("")) {
            limpiarCajas();
            return;
        }
        limpiarCajas();
        GastosMensuales gastoMen = logica.getGastoMensual(Integer.parseInt(id));
        if (gastoMen == null) {
            // AGREGAR

            EstadoControlesAgregar();
        } else {
            // EDITAR
            EstadoControlesEditar();

            // Cargar datos
            //cboTipoGastos.setSelectedItem(TIPOS_VEHICULO.get(pm.getPlaca().getTipo()));
            cboTipoGastos.setSelectedItem("[" + gastoMen.getTiposGastosId().getId() + "]" + " " + gastoMen.getTiposGastosId().getDescripcion());
            txtFecha.setDate(gastoMen.getFecha());
            txtValor.setText("" + gastoMen.getValor());
            txtDescripcion.setText(gastoMen.getDescripcion());
        }
        

    }//GEN-LAST:event_txtIdFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnGrabar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboTipoGastos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblObservacion;
    private javax.swing.JLabel lblTipoGasto;
    private javax.swing.JLabel lblValorPagar;
    private javax.swing.JPanel pnlAgregar;
    private javax.swing.JPanel pnlBuscar;
    private javax.swing.JPanel pnlCentro;
    private javax.swing.JPanel pnlConsultar;
    private javax.swing.JPanel pnlSur;
    private javax.swing.JPanel pnlTabla;
    private javax.swing.JPanel pnlTotal;
    private javax.swing.JTable tblDatos;
    private javax.swing.JTextArea txtDescripcion;
    private com.toedter.calendar.JDateChooser txtFecha;
    private com.toedter.calendar.JDateChooser txtFechaFinal;
    private com.toedter.calendar.JDateChooser txtFechaInicial;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtTotalGastos;
    private javax.swing.JTextField txtTotalRegistros;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
