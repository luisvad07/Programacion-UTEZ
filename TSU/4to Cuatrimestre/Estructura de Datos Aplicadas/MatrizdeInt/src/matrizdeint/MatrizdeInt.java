/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrizdeint;

import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class MatrizdeInt {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner consola = new Scanner(System.in);        
        Integer[][] mat_consecutiva = new Integer[4][2];

        System.out.println("RELLENA ESTA MATRIZ!");
        System.out.println("Instrucciones: ingresa numeros consecutivos apartir del 1");
        for (int x=0; x < mat_consecutiva.length; x++) {
            for (int y=0; y < mat_consecutiva[x].length; y++) {
              System.out.print("Introduzca el elemento [" + x + "," + y + "] : ");
              mat_consecutiva[x][y] = consola.nextInt();
            }
        }
        System.out.println("\n");
        System.out.println("-----------MATRIZ CONSECUTIVA------------");
        for (Integer[] const1 : mat_consecutiva) {
            for (Integer const2 : const1) {
                System.out.print(" | " + const2 + " | ");
            }
            System.out.println();
        }
        System.out.println("\n");
        int value = mat_consecutiva[0][1];
        System.out.println("El valor del elemento de la fila 1, columna 2 del matriz, es de " + value);
    }
    
}
