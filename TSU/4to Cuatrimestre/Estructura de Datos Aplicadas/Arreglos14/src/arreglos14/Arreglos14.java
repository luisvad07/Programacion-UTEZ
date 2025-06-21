/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arreglos14;

import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class Arreglos14 {

    /**
     * @param args the command line arguments
     */
    static String equipos[][] = new String[5][2];
    static int menor = 0, mayor = 0, num=0;
    static int i, j;
    
    public static void main(String[] args) {
        /*Elaborar el programa que guarde en un arreglo bidimensional de 5 x 2, 
        los siguientes datos dados por el usuario: nombre de equipo y puntaje, 
        imprimir dicho arreglo y posteriormente indicar qué equipo salió más bajo y cual fue el más alto*/
        Scanner gali = new Scanner(System.in);
        String pos, equipo;

        for (int i = 0; i < equipos.length; i++) {
            for (int j = 0; j < equipos[i].length; j++) {
                if (j == 1) {
                    System.out.print("Ingresa la puntuacion: ");
                    pos = gali.nextLine();
                    equipos[i][j] = pos;
                } else {
                    System.out.print("Ingresa el equipo #"+(i+1)+" : ");
                    equipo = gali.nextLine();
                    equipos[i][j] = equipo;
                }
            }
        }
        System.out.println("");
        for (i = 0; i < equipos.length; i++) {
            for (j = 0; j < equipos[i].length; j++) {
                System.out.print(equipos[i][j] + " ");
            }
            System.out.println("");
        }
        
        mayor = num;
        for (i = 0; i < equipos.length; i++) {
            num = Integer.parseInt(equipos[i][1]);
            if (num > mayor) {
                mayor = num;
            }
        }
        menor = num;
        for (i = 0; i < equipos.length; i++) {
            num = Integer.parseInt(equipos[i][1]);
            if (num > mayor) {
                menor = num;
            }
        }
        System.out.println("\n");
        System.out.println("El equipo con mayor puntuacion: Puntos = " + mayor);
        System.out.println("El equipo con menor puntuacion: Puntos = " + menor);
    }
}
