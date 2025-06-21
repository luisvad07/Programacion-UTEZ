/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ordenamiento2;

import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class Ordenamiento2 {

    /**
     * @param args the command line arguments
     */
    static int ox = 0, colonnes, lignes, found, k = 0, save = 0, i, j, m;
    static int normal[][][], orden[], mult;
    static Scanner gali = new Scanner(System.in);
    
    public static void main(String[] args) {
        /*Elaborar un programa que permita ordenar elementos de un arreglo de enteros tridimensional, 
        los datos deben ser solicitados por el usuario, 
        preguntar si quiere ordenarlos de forma ascendente o de forma descendente. y mostrar el cubo ordenado*/
        
        System.out.println("----ARREGLOS EN ORDENAMIENTO---");
        System.out.print("No. de Filas: ");
        lignes = gali.nextInt();
        System.out.print("No. de Columnas: ");
        colonnes = gali.nextInt();
        System.out.print("No. de Inserciones: ");
        found = gali.nextInt();
        normal = new int[lignes][colonnes][found];
        mult = (lignes * colonnes) * found;
        orden = new int[mult];
        
        System.out.println("Ingrese numeros en el cubo de arreglos!!!!");
        for (i = 0; i < normal.length; i++) {
            for (j = 0; j < normal[i].length; j++) {
                for (m = 0; m < normal[j].length; m++) {
                    System.out.print("Posición " + "[" + i + "]" + "[" + j + "]" + "[" + k + "] = ");
                    normal[i][j][m] = gali.nextInt();
                    orden[k] = normal[i][j][m];
                    k++;
                } 
            }
        }
        System.out.println("\n");
        System.out.println("-----MATRIZ GENERADO----");
        for (i = 0; i < normal.length; i++) {
            for (j = 0; j < normal[i].length; j++) {
                for (m = 0; m < normal[j].length; m++) {
                    System.out.print(" " +normal[i][j][m] + " ");
                } 
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
                    for (int i = 0; i < mult; i++) {
                        for (int b = 1; b < (mult - i); b++) {
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
                            for (int c = 0; c < normal[0].length; c++) {
                                normal[a][b][c] = orden[k];
                                k++;
                            }
                        }
                    }
                    System.out.println("\n-----DESCENDENTE-----");
                    for (int a = 0; a < normal.length; a++) {
                        for (int b = 0; b < normal[0].length; b++) {
                            for (int c = 0; c < normal[0].length; c++) {
                                System.out.print("  " + normal[a][b][c] + " , ");
                            }
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
