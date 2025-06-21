/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fibonacci_recursividad;

import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class Fibonacci_Recursividad {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner flor = new Scanner(System.in);
        System.out.println("-----SERIE DE FIBONACCI-------");
        System.out.print("Ingrese un numero para mostrar la serie: ");
        int tope = flor.nextInt();
        int cont = 0;
        System.out.println("\n");
        System.out.println("Mostrando la Serie");
        while (cont<tope) {
            cont++;
            System.out.print(fibonacci(cont) + ", ");
        }
        System.out.print("...");
        System.out.println("\n");
    }
    
    public static int fibonacci(int tope){ //Metodo Recursivo
        int muestra;
        if (tope == 0 || tope == 1) { // Si el num ingresado es igual a 0 o 1, devuelve el mismo numero ingresado como resutado
            muestra = tope;
        } else { //Si el num ingresado es mayor a 1, devuelve la suma de la serie
            muestra = fibonacci(tope-1) + fibonacci(tope-2);   
        }
        return muestra;
    }
    
}
