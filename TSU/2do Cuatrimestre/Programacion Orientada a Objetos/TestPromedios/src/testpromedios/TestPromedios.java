/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package testpromedios;

import java.util.Scanner;

/**
 *
 * @author eduardoA
 */
public class TestPromedios {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Scanner leer = new Scanner(System.in);

        double[] matrizA = new double[5];
        double[] matrizB = new double[5];
        double[] promedio = new double[5];


        System.out.println("Ingresa las calificaciones del grupo A");

        for (int i = 0; i < matrizA.length; i++) {
            System.out.print("Alumno " + (i + 1) + ": ");
            matrizA[i] = leer.nextDouble();
        }

        System.out.println("\nCalificaciones GRUPO A");
        for (int i = 0; i < matrizA.length; i++) {
            System.out.println("Alumno[" + (i + 1) + "] | " + matrizA[i]);
        }

        System.out.println("\nIngresa las calificaciones del grupo B");

        for (int i = 0; i < matrizB.length; i++) {
            System.out.print("Alumno " + (i + 1) + ": ");
            matrizB[i] = leer.nextDouble();
        }

        System.out.println("\nCalificaciones GRUPO B");
        for (int i = 0; i < matrizB.length; i++) {
            System.out.println("Alumno[" + (i + 1) + "] | " + matrizB[i]);
        }

        for (int i = 0; i < promedio.length; i++) {
            if (matrizA[i] > matrizB[i]) {
                promedio[i] = matrizA[i];
            } else {
                promedio[i] = matrizB[i];
            }
        }

        System.out.println("\nPROMEDIOS MÁS ALTOS");
        for (int i = 0; i < promedio.length; i++) {
            System.out.println("Promedio más alto en la posición " + (i + 1) + " | " + promedio[i]);
        }

    }

}
