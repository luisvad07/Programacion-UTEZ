/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ordenamiento4;

import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class Ordenamiento4 {

    /**
     * @param args the command line arguments
     */
    static int paquetes, k, x;
    static String[] Direccion;
    static int[] codigos_postales, orden;
    static Scanner gali = new Scanner(System.in);
    
    public static void main(String[] args) {
        /*El departamento de entregas de una famosa empresa de paquetería desea poder contar con todos sus paquetes ordenados 
        a entregar en el día. Cada paquete cuenta con un código postal y dirección.
        Ordenar ascendentemente todos los paquetes por código postal y mostrar sus destinos*/
        
        System.out.println("-----SISTEMA DE PAQUETERIA DHL------");
        System.out.print("No. de paquetes a registrar: ");
        paquetes = gali.nextInt();
        Direccion = new String[paquetes];
        codigos_postales = new int[paquetes];
        orden = new int[paquetes];
        System.out.println("\n");
        for (int i = 0; i < paquetes; i++) {
            System.out.print("Codigo Postal del Paquete #" + (k + 1)+": ");
            codigos_postales[i] = gali.nextInt();
            System.out.print("Destino del Paquete del Codigo Postal #" + codigos_postales[i] + ": ");
            Direccion[i] = gali.next();
            orden[k] = codigos_postales[i];
            k++;
            System.out.println("");
        }
        System.out.println("\n");
            System.out.println("-----PAQUETES DE ENVIO----");
        for (int a = 0; a < paquetes; a++) {
            System.out.println(codigos_postales[a] + " -> " + Direccion[a]);

        }
        System.out.println("\n");
        determinarArrays(paquetes);
    }
    
    public static void determinarArrays(int paquetes) {
        if (confirmarCambio()) {
            for (int i = 0; i < paquetes; i++) {
                for (int j = 1; j < (paquetes - i); j++) {
                    if (orden[j - 1] > orden[j]) {
                        k = orden[j - 1];
                        orden[j - 1] = orden[j];
                        orden[j] = k;
                    }
                }
            }
            System.out.println("\n-----FORMA ASCENDENTE-----");
            for (int a = 0; a < orden.length; a++) {
                System.out.println("Codigo Postal #" +orden[a]); //No pude poner la direccion :(
            }
        } else {
            System.out.println("Se cancelo la operacion.....");
        }
    }   
                    
    public static boolean confirmarCambio() {
        int resp;
        Scanner siuu = new Scanner(System.in);
        System.out.println("¿Estas seguro de realizar la operacion?");
        System.out.println("1.- Si");
        System.out.println("2.- No");
        System.out.print("Respuesta: ");
        resp = siuu.nextInt();
        siuu.skip("\n");
        return resp == 1;
    }
    
}
