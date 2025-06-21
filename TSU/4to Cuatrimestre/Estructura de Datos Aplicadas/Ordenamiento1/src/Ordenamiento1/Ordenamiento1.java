/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ordenamiento1;

import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class Ordenamiento1 {

    /**
     * @param args the command line arguments
     */
    static int ox = 0, colonnes, lignes, k = 0, save = 0, i, j;
    static int normal[][], orden[], mult;
    static Scanner gali = new Scanner(System.in);
    
    public static void main(String[] args) {
        /*Elaborar un programa que permita ordenar elementos de un arreglo de enteros bidimensionales, 
        los datos deben ser solicitados por el usuario, 
        preguntar si quiere ordenarlos de forma ascendente o de forma descendente. y mostrar la matriz ordenada*/
        
        System.out.println("----ARREGLOS EN ORDENAMIENTO---");
        System.out.print("No. de Filas: ");
        lignes = gali.nextInt();
        System.out.print("No. de Columnas: ");
        colonnes = gali.nextInt();
        normal = new int[lignes][colonnes];
        mult = lignes * colonnes;
        orden = new int[mult];
        
        System.out.println("Ingrese numeros en la matriz!!!!");
        for (i = 0; i < normal.length; i++) {
            for (j = 0; j < normal[i].length; j++) {
                System.out.print("Posición " + "[" + i + "]" + "[" + j + "] = ");
                normal[i][j] = gali.nextInt();
                orden[k] = normal[i][j];
                k++;
            }

        }
        System.out.println("\n");
        System.out.println("-----MATRIZ GENERADO----");
        for (i = 0; i < normal.length; i++) {
            for (j = 0; j < normal[i].length; j++) {
                System.out.print(" " +normal[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("\n");
        
        determinarArrays(mult);
    }
    
    public static void determinarArrays(int mult) {
        
        System.out.println("¿Forma de Ordenar?");
        System.out.println("1.-Ascendente");
        System.out.println("2.-Descendente");
        System.out.print("Opcion: ");
        ox = gali.nextInt();
        do {
        switch (ox) {
            case 1:
                if (confirmarCambio()) {
                    for (i = 0; i < mult; i++) {
                        for (j = 1; j < (mult - i); j++) {
                            if (orden[j - 1] > orden[j]) {
                                save = orden[j - 1];
                                orden[j - 1] = orden[j];
                                orden[j] = save;
                            }
                        }
                    }
                    System.out.println("\n-----ASCENDENTE-----");
                    for (i = 0; i < orden.length; i++) {
                        System.out.print(orden[i] + " , ");
                    }
                } else {
                    System.out.println("Cambios no guardados!...");
                }
                break;
            case 2:
                if (confirmarCambio()) {
                    for (int a = 0; a < mult; a++) {
                        for (int b = 1; b < (mult - a); b++) {
                            if (orden[b - 1] < orden[b]) {
                                k = orden[b - 1];
                                orden[b - 1] = orden[b];
                                orden[b] = k;
                            }
                        }
                    }
                    k = 0;
                    for (int a = 0; a < normal.length; a++){
                        for (int b = 0; b < normal[0].length; b++){
                            normal[a][b] = orden[k];
                            k++;
                        }
                    }
                    System.out.println("\n-----DESCENDENTE-----");
                    for (int a = 0; a < normal.length; a++){
                        for (int b = 0; b < normal[0].length; b++){
                            System.out.println("  " + normal[a][b]+" , ");
                        }
                    }
                } else {
                    System.out.println("Cambios no guardados!...");
                }
                break;
            default:
                System.out.println("Opcion No Valida, Ingresa de Nuevo!");
                break;
        }
        }while(ox==1 && ox==2);
    }   
    public static boolean confirmarCambio() {
        int resp;
        Scanner siuu = new Scanner(System.in);
        System.out.println("\n");
        System.out.println("¿Estas seguro de realizar el cambio?");
        System.out.println("1.- Si");
        System.out.println("2.- No");
        System.out.print("Respuesta: ");
        resp = siuu.nextInt();
        siuu.skip("\n");
        return resp == 1;
    }
}
