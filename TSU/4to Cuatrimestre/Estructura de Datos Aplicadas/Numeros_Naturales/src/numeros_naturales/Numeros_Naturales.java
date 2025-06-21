/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package numeros_naturales;

import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class Numeros_Naturales {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        int pos;
        System.out.println("-----SUMA NUMERICA-----");
        System.out.print("Introduce un numero entero: ");
        pos = teclado.nextInt();
        System.out.println("\n");
        if (pos>0) {
            System.out.println("Sumando.....");
           System.out.println("El Resultado desde 1 hasta " + pos + " es igual a " + sumaNumerica(pos)); 
        } else {
            System.out.println("El numero no es entero!");
            System.out.println("Se termina el programa :(");
        }
    }

    public static int sumaNumerica(int n){ //Método recursivo
        final int s = 1;
        if(n == s) { // Si n es igual a 1, devuelve como resultado 1
            return 1;
        } else { // Si n es numero entero, devuelve la suma realizada
            return n+ sumaNumerica(n-s);
        }    
    }
    
}
