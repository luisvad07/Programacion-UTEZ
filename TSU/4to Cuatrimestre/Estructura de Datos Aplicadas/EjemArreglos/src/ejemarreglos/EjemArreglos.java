/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejemarreglos;

import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class EjemArreglos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner siu = new Scanner(System.in);
        int[]edades;
        int tam;
        double promedio = 0;
        System.out.print("Cuantas personas vas a guardar?");
        tam = siu.nextInt();
        edades = new int[tam];
        
        for (int i = 0; i < tam; i++) {
            System.out.print("Ingresa la edad para la persona ["+i+"] = ");
            edades[i]=siu.nextInt();
            promedio+=edades[i];
        }
        System.out.println("El promedio de edades de las personas es de: "+promedio/tam);
    }
    
}
