/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arreglos1;

import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class Arreglos1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*Solicitar al usuario N cantidad de números y almacenarlos en un arreglo, 
        posteriormente deberá multiplicar los elementos del arreglo y mostrar el resultado.*/
        Scanner gali = new Scanner(System.in);
        int numeros[], ing, promedio = 1;
        System.out.print("¿Cuantos numeros desea agregar? R= ");
        ing = gali.nextInt();
        numeros = new int[ing];
        
        for (int i = 0; i < ing; i++) {
            System.out.print("Ingresa numero para el elemento ["+i+"] = ");
            numeros[i]=gali.nextInt();
            promedio = numeros[i] * promedio;
        }
        
        System.out.println("\n");
        System.out.println("Total de la multiplicacion del Arreglo: "+promedio);
    }
    
}
