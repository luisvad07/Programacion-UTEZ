/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package busquedabinariamatriz;

import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class BusquedaBinariaMatriz {

    /**
     * @param args the command line arguments
     */
    static Scanner t = new Scanner(System.in);
    
    public static void main(String args[]) {
        // Se llena el arreglo
        int[][] numeros = new int[2][2];
        for (int i = 0; i < numeros.length; i++) {
            for (int j = 0; j < numeros.length; j++) {
                numeros[i][j] = (int) (Math.random()*10)+1;
            }
        }
        // Imprime los valores.
        for (int i = 0; i < numeros.length; i++) {
            for (int j = 0; j < numeros.length; j++) {
                System.out.println("Numero [" + i + "][" + j + "] = " + numeros[i][j]);
            }
        }
        System.out.println("\n");
        System.out.print("¿Que numero deseas buscar?: ");
        int busq = t.nextInt();

        int inicio = 0;
        int fin = numeros.length - 1;
        int pos, pos2;
        while (inicio <= fin) {
            pos = (inicio + fin) / 2;
            pos2 = (inicio + fin) / 2;
            if (numeros[pos][pos2] == busq) {
                System.out.println("Enontrado en la posicion: [" + pos + "][" + pos2+ "]");
                break;
            } else if (numeros[pos][pos2] < busq) {
                inicio = (pos+pos2) + 1;
            } else {
                fin = (pos+pos2) - 1;
            }
        }
    }
}
