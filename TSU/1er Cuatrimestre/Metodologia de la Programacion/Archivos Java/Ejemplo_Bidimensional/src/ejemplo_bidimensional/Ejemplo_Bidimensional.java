/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejemplo_bidimensional;
import java.util.Scanner;
/**
 *
 * @author CC10
 */
public class Ejemplo_Bidimensional {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    // TODO code application logic here
    int filas = 0; 
    int columnas = 0; 
    int matriz[][];
    Scanner teclado=new Scanner (System.in);
    System.out.println("Introducir el num de filas:");
    filas = teclado.nextInt();
    //Se crea con el # filas
    matriz = new int [filas][];
    //Definir el # de columnas para cada fila
    for (int i = 0; i < matriz.length; i++) {
        System.out.println("Num de columnas para la fila" + (i+1)); 
        matriz[i] = new int[teclado.nextInt()];  
    }
    for (int i = 0; i < matriz.length; i++) {
        System.out.println("El num de columnas de la fila"+(i+1)+" es: "+matriz[i].length); 
    }
    //leemos datos
    for (int i = 0; i < matriz.length; i++) {
        //For que recorre las columnas
        for (int j = 0; j < matriz.length; j++) {
        System.out.println("Escribe el valor para columna "+j+"  de la Fila "+i); 
        matriz[i][j] = teclado.nextInt();  
        }
    }
    //Imprimimos los datos
    for (int i = 0; i < matriz.length; i++) {
        //For que recorre las columnas
        for (int j = 0; j < matriz.length; j++) {
        System.out.println(matriz[i][j]+" "); 
        }
        System.out.println(" ");
    }
    }
}
