package pruebas;

import com.gyan.parqueadero.logicanegocio.PagosMensualesLogicaNegocio;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 *
 * @author libardo
 */
public class ClienteMain {

    public static void main(String args[]) {

        int [] edades = new int[10];
        
        Random rand = new Random();
        
        for(int i= 0; i< 10; i++){
            edades[i] = Math.abs(rand.nextInt()%80);
        }
        
               
        for(int i= 0; i< 10; i++){
            System.out.println("edad " + i + ": " + edades[i]);
        }
        
        System.out.println("prueba" +  edades[12]);
        
        
        
    
        
        /*
        try {
            File path = new File("./src/recursos/EjemplosCobroTarifas.pdf");
            Desktop.getDesktop().open(path);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        */
        /*
        try {
            File path = new File("FirstPdf.pdf");
            Desktop.getDesktop().open(path);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
         */
 /*
        Properties prop = new Properties();
        InputStream is = null;

        try {
            is = new FileInputStream("./config.properties");
            prop.load(is);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        

        System.out.println(prop.getProperty("empresa"));
         */
 /*
        System.out.println("99: " + Utilidades.redondearCentenaProxima(99));
        System.out.println("100: " + Utilidades.redondearCentenaProxima(100));
        System.out.println("101: " + Utilidades.redondearCentenaProxima(101));
        System.out.println("1238: " + Utilidades.redondearCentenaProxima(1238));
         */
    }
}
