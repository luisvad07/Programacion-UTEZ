/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arreglos16;

import java.util.Random;

/**
 *
 * @author Luis
 */
public class Arreglos16 {

    /**
     * @param args the command line arguments
     */
    static Random siu = new Random();
    static final String cloc = "0123456789";
    static int num = cloc.length() - 1;
    static int i;

    public static void main(String args[]) {
        /*Elaborar el programa que guarde en un arreglo bidimensional de 4 x 4 números aleatorios y con recursividad indirecta los imprima*/
        char[][] rec = new char[4][4];

        for (i = 0; i < rec.length; i++) {
            rellenarFila(rec, i, 0);
        }
        
        for (i = 0; i < rec.length; i++) {
            System.out.println("Fila " + (i+1));
            for (int j = 0; j < rec.length; j++) {
                System.out.print(rec[i][j] + ",");
            }
            System.out.println("\n");
        }
    }

    public static int rellenarFila(char[][] butter, int i, int j) {
        int f = 0;
        int h = siu.nextInt(num);
        butter[i][j] = cloc.charAt(h);
        if (j < butter[i].length - 1) {
            f = rellenarFila(butter, i, j + 1);
        }
        return f;
    }
}
