/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arreglos11;

import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class Arreglos11 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*Elaborar el programa que reciba dos índices (dados por el usuario) 
        posteriormente deberá imprimir un arreglo bidimensional con una cruz en el centro*/
        Scanner teclado = new Scanner(System.in);
       
        boolean numeroCorrecto = false;
        int filas,columnas;
       
        System.out.println("---SOLO INGRESA NUMEROS IMPARES---");
        do {
            System.out.print("Ingrese la cantidad de filas: ");
            filas = teclado.nextInt();
            System.out.print("Ingrese la cantidad de columnas: ");
            columnas = teclado.nextInt();
                if(filas % 2 != 0 && columnas % 2 != 0) {
                    numeroCorrecto = true;
                }else {
                    System.out.println("Datos incorrectos. Ingrese dos valores impares");
                }
            System.out.println();
        }while(!numeroCorrecto);
       
        imprimirCruz(filas, columnas);
        
    }
    
    public static void imprimirCruz(int filas, int columnas) {
        char matriz [][]= new char [filas][columnas];
       
        int mitadFilas = matriz.length / 2;
        int mitadColumnas = matriz[0].length / 2;
       
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                if(i == mitadFilas || j == mitadColumnas) {
                    matriz [i][j] = '*';
                }else {
                    matriz [i][j] = ' ';
                }
                System.out.print(matriz[i][j]+" ");
            }
            System.out.println();
        }
    }
    
}
