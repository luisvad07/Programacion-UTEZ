/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arreglos18;
import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class Arreglos18 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Elaborar el programa que reciba dos índices (dados por el usuario) 
        posteriormente deberá imprimir un arreglo bidimensional con un triángulo dibujado(explicar).*/
        
        Scanner teclado = new Scanner(System.in);
       
        int n, m;
        boolean numeroCorrecto = false;
       
        do {
            System.out.print("Ingrese cantidad filas (número impar): ");
            n = teclado.nextInt();
            System.out.print("Ingrese cantidad columnas (número impar): ");
            m = teclado.nextInt();
           
            if(n % 2 != 0 && m % 2 != 0) {
                numeroCorrecto = true;
            }else {
                System.out.println("Error. Debe ingresar dos números impares");
            }
            System.out.println();
           
        }while(!numeroCorrecto);
       
        char matriz [][] = new char [n][m];
       
        int mitad = matriz[0].length / 2;
       
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                if((i+j) >= mitad && (j-i) <= mitad) {
                    matriz [i][j] = '*';
                }else {
                    matriz [i][j] = ' ';
                }
                System.out.print(matriz [i][j]+" ");
            }
            System.out.println();
        }
    }
    
}
