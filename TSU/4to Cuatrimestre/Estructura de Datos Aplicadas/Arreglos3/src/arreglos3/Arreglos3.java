/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arreglos3;

import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class Arreglos3 {

    /**
     * @param args the command line arguments
     */

    
    public static void main(String[] args) {
        /* Elabora un programa que llene un arreglo de 10 posiciones con números 
        dados por el usuario y con un método recursivo obtenga e imprima suma de todos sus elementos 
        y cuál número fue el mayor.*/
        Scanner ama = new Scanner(System.in);
        int ant[] = new int[10];
        for(int i=0;i<ant.length;i++) {
            System.out.print("Ingrese un numero: ");
            ant[i] = ama.nextInt();
        }
        System.out.println("\n");
        System.out.println("La suma de los numeros es de: " + Sumatorio(ant, ant.length-1));
        System.out.println("\n");
        int mayor = ant[0];
            for (int x = 1; x < ant.length; x++) {
		if (ant[x] > mayor) {
                    mayor = ant[x];
		}
            }
        System.out.println("El mayor numero del arreglo es: " + mayor);

    }

    //Método Recursivo
    public static int Sumatorio(int ant[], int e){
        int tam = e;
            int suma;
            if (tam == 0){
                return ant[tam];
            }else{
                 suma = (ant[tam]) + Sumatorio(ant, tam - 1);
            }
            return suma;
    }
}

