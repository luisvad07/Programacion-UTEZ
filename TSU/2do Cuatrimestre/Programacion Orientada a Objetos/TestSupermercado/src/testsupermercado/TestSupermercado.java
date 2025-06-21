/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testsupermercado;

import java.util.Scanner;

/**
 *
 * @author clientepreferido
 */
public class TestSupermercado {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner leer = new Scanner(System.in);

        int[] matrizVentas = new int[100];
        int sumaVentas = 0;

        System.out.println("==============================================");
        System.out.println("                VENTAS CLIENTES");
        System.out.println("==============================================");
        for (int i = 0; i < matrizVentas.length; i++) {
            matrizVentas[i] = (int) (Math.random() * 1000);
            System.out.println("Venta  " + (i + 1) + ": $" + matrizVentas[i]);
        }
        System.out.println("==============================================");

        for (int i = 0; i < matrizVentas.length; i++) {
            sumaVentas += matrizVentas[i];
        }
        System.out.println("TOTAL VENTAS: $" + sumaVentas);
    }

}
