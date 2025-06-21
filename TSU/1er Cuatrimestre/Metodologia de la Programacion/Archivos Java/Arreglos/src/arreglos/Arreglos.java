/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arreglos;
import java.util.Scanner;
/**
 *
 * @author CC-6
 */
public class Arreglos {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       int[] vector1= new int[0];
       int tam = 0;
       Scanner teclado=new Scanner (System.in);
       System.out.println("Escribir el tamaño del Vector: ");
       tam = teclado.nextInt();
       vector1=new int [tam];
       for(int i=0; i < vector1.length; i++) {
           System.out.println("Introduzca el valor para " + i);
           vector1[i]=teclado.nextInt();
       }
       System.out.println("Impresion de arreglo con WHILE");
       int contador = 0;
       while (contador < vector1.length) {
           System.out.println("El numero de la posicion # " + contador + " es: " + vector1[contador]);
           contador++;
       }
    }
}
