/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package produccion;
import java.util.Scanner;
/**
 *
 * @author clientepreferido
 */
public class Produccion {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    int numprod;
    double prom;
    Scanner teclado=new Scanner (System.in);
    System.out.println("Ingrese el número de producción del trabajador:");
    numprod=teclado.nextInt();
    prom = numprod/6;
    if (prom >= 100) {
        System.out.println("El trabajador recibira incentivos");    
    } else {
        System.out.println("El trabajador no recibira incentivos");
    }        
    }
}
