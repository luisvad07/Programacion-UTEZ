/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computadoras;
import java.util.Scanner;
/**
 *
 * @author clientepreferido
 */
public class Computadoras {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    int zona1, zona2, zona3, zona4;
    Scanner teclado = new Scanner(System.in);
    int ventas[][] = new int[3][4];
        for (int i = 0; i < ventas.length; i++) {
            for (int j = 0; j < ventas[i].length; j++) {
                System.out.println("Ingresa el numero de computadoras vendidas por el vendedor " + (i + 1) + " en la Zona " + (j + 1));
                ventas[i][j] = teclado.nextInt();
            }
        }
        //Imprimir la tabla
        for (int i = 0; i < ventas.length; i++) {
            System.out.println("Vendedor #" + (i + 1) + ": ");
            for (int j = 0; j < ventas[i].length; j++) {
                System.out.println("    Zona " + (j + 1) + ": " + ventas[i][j]);
            }
        }
        //Sacar la zona con mas ventas
        int suma = 0;
        int zona_cv = 0;
        int indice[] = new int[4];
        int contador = 0;
        for (int i = 0; i < ventas[0].length; i++) {
            for (int j = 0; j < ventas.length; j++) {
                suma = suma + ventas[j][i];
            }
            if (suma >= zona_cv) {
                zona_cv = suma;
                if (contador == 0) {
                    indice[contador] = i;
                } else {
                    indice[contador - 1] = i;
                }

                contador++;
            }
            suma = 0;
        }
        System.out.print("La(s) zona(s) con mas ventas son: ");
        for (int i = 0; i < indice.length; i++) {
            System.out.print(" Zona " + (i + 1) + ","); //Aqui no pude configurar solo una zona :(
        }
        System.out.print(" con un total de ventas de: " + zona_cv + " computadoras");
        System.out.println("");
    }    
}
