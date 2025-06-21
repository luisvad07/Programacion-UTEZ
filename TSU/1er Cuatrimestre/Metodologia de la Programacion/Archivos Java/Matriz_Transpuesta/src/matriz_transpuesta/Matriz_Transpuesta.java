/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matriz_transpuesta;
import java.util.Scanner;
/**
 *
 * @author CC-6
 */
public class Matriz_Transpuesta {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    int matriz[][];
        int transpuesta[][];
        int fila;
        int columna;
        Scanner teclado=new Scanner(System.in);
        System.out.println("Escriba el numero de Filas");
        fila=teclado.nextInt();
        System.out.println("Escriba el numero de Columnas");
        columna=teclado.nextInt();
        matriz=new int[fila][columna];
        transpuesta=new int[columna][fila];
        //Llenar de datos la matriz original
        for (int f = 0; f < fila; f++) {
            for (int c = 0; c < columna; c++) {
                System.out.println("Escriba el valor para la columna "+ c + " de la fila "+f);
                matriz[f][c]=teclado.nextInt();
            }
        }
        //Transponer la matriz
        for (int c = 0; c < columna; c++) {
            for (int f = 0; f < fila; f++) {
                transpuesta[c][f]=matriz[f][c];
            }
        }
        System.out.println("\n");
        System.out.println("Matriz Original");
        //Imprimir la matriz original
        for (int f = 0; f < fila; f++) {
            for (int c = 0; c < columna; c++) {
                System.out.print(matriz[f][c]+"|");
            }
            System.out.println("");
        }
        System.out.println("Matriz Transpuesta");
        //Imprimir la matriz transpuesta
        for (int f = 0; f < columna; f++) {
            for (int c = 0; c < fila; c++) {
                System.out.print(transpuesta[f][c]+"|");
            }
            System.out.println("");
        }
    }
}
