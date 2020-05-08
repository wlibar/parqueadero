/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gyan.parqueadero.utilidades;

import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

/**
 *
 * @author libardo
 */
public class ReciboPdf {

    //private static String FILE = "c:/temp/FirstPdf.pdf";
    private static String FILE = "Recibo.pdf";

    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 10,
            Font.BOLD);
    private static Font smallFont = new Font(Font.FontFamily.TIMES_ROMAN, 10,
            Font.NORMAL);
    private static Font smallItalic = new Font(Font.FontFamily.TIMES_ROMAN, 10,
            Font.ITALIC);

    public static void generar(String nombreParqueadero, String direccionParqueadero, String placa, String fechaEntrada, String fechaSalida, String valorPagar, String llaves, String cascosNro) {
        try {
            Rectangle pageSize = new Rectangle(200f, 200f); //ancho y alto, Margenes, 72 puntos es 1 pulgada
            Document document = new Document(pageSize, 10, 10, 10, 10);
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            addMetaData(document);
            addContentPage(document, nombreParqueadero, direccionParqueadero, placa, fechaEntrada, fechaSalida, valorPagar, llaves, cascosNro);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // iText allows to add metadata to the PDF which can be viewed in your Adobe
    // Reader
    // under File -> Properties
    private static void addMetaData(Document document) {
        document.addTitle("Recibo Parqueadero");
        document.addSubject("Recibo");
        document.addKeywords("Parqueadero, Pasto");
        document.addAuthor("W. Libardo Pantoja Y.");
        document.addCreator("W. Libardo Pantoja Y.");
    }

    private static void addContentPage(Document document, String nombreParqueadero, String direccionParqueadero, String placa, String fechaEntrada, String fechaSalida, String valorPagar,String llaves, String cascosNro)
            throws DocumentException {
        Paragraph preface = new Paragraph();
        // We add one empty line
        addEmptyLine(preface, 1);

        // Lets write a big header
        preface.add(new Paragraph(nombreParqueadero, smallBold));
        preface.add(new Paragraph(direccionParqueadero, smallItalic));
        preface.add(new Paragraph("Placa: " + placa, smallFont));
        preface.add(new Paragraph("FechaHora Entrada: " + fechaEntrada, smallFont));
        if (!fechaSalida.equals("")){
            preface.add(new Paragraph("FechaHora Salida: " + fechaSalida, smallFont));
        }
        if (!valorPagar.equals("")){
            preface.add(new Paragraph("Valor Pagar: " + valorPagar, smallFont));
        }
        if (!llaves.equals("NO_APLICA")){
            preface.add(new Paragraph("¿LLaves? " + llaves, smallFont));
        }
        if (!cascosNro.equals("NO_APLICA")){
            preface.add(new Paragraph("Cantidad de cascos: " + cascosNro, smallFont));
        }
        
        preface.add(new Paragraph("Firma: _________________ ", smallFont));

        document.add(preface);
        // Start a new page
        document.newPage();
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
