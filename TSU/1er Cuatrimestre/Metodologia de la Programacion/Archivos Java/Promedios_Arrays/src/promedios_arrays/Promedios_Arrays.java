/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package promedios_arrays;
import java.util.Scanner;
/**
 *
 * @author CC10
 */
public class Promedios_Arrays {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    Scanner teclado = new Scanner (System.in);
    float[] calif = new float[6];
    float prom, suma=0;
    int contador = 1;
    while (contador<6) {
        System.out.println("Ingrese una calificacion:");
        calif[contador]=teclado.nextFloat();
        suma=suma+calif[contador];
        contador++;
    }
    prom=suma/5;
    System.out.println("El promedio total es de: " + prom);
    }
}
