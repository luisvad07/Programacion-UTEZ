/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ordenamiento3;

import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class Ordenamiento3 {

    /**
     * @param args the command line arguments
     */
    
    static int tot, k, x;
    static String[] alumno, apellido;
    static int[] orden;
    static double[] calificaciones;
    static Scanner gali = new Scanner(System.in);
    
    public static void main(String[] args) {
        /*Desarrollar un programa que permita ingresar a un docente las calificaciones obtenidas por sus estudiantes 
        en el examen final, el docente desea tener las calificaciones ordenadas de forma descendente.
        El programa deberá mostrar la calificación junto al nombre del estudiante que la obtuvo. 
        Ejemplo: 10 - José Narváez9.8 - Emilio Aguilar 9.7 - Samuel Perez*/
        
        System.out.println("-----SISTEMA PARA GUARDAR REGISTROS DE EXAMENES------");
        System.out.print("No. de alumnos a registrar: ");
        tot = gali.nextInt();
        alumno = new String[tot];
        apellido = new String[tot];
        calificaciones = new double[tot];
        orden = new int[tot];
        System.out.println("\n");
        for (int i = 0; i < tot; i++) {
            System.out.print("Nombre del Alumno " + (k + 1)+": ");
            alumno[i] = gali.next();
            System.out.print("Apellido del Alumno " + (k + 1)+": ");
            apellido[i] = gali.next();
            System.out.print("Calificación Examen del Alumno " + alumno[i] + " " + apellido[i] + ": ");
            calificaciones[i] = gali.nextDouble();
            orden[k] = (int) calificaciones[i];
            k++;
            System.out.println("");
        }
        System.out.println("\n");
            System.out.println("-----CALIFICACIONES DE LOS ESTUDIANTES----");
        for (int a = 0; a < tot; a++) {
            System.out.println(alumno[a] + " " + apellido[a] + " = " + calificaciones[a]);

        }
        System.out.println("\n");
        determinarArrays(tot);
    }
    
    public static void determinarArrays(int tot) {
        if (confirmarCambio()) {
            for (int i = 0; i < tot; i++) {
                for (int j = 1; j < (tot - i); j++) {
                    if (orden[j - 1] < orden[j]) {
                        k = orden[j - 1];
                        orden[j - 1] = orden[j];
                        orden[j] = k;
                    }
                }
            }
            k = 0;
            for (int a = 0; a < calificaciones.length; a++){
                calificaciones[a] = orden[k];
                k++;
            }
            System.out.println("\n-----FORMA DESCENDENTE-----");
            for (int a = 0; a < calificaciones.length; a++) {
                System.out.println("Calificacion: " +calificaciones[a]); //No pude poner el nombre :(
            }
        } else {
            System.out.println("Se cancelo la operacion.....");
        }
    }   
                    
    public static boolean confirmarCambio() {
        int resp;
        Scanner siuu = new Scanner(System.in);
        System.out.println("¿Estas seguro de realizar la operacion?");
        System.out.println("1.- Si");
        System.out.println("2.- No");
        System.out.print("Respuesta: ");
        resp = siuu.nextInt();
        siuu.skip("\n");
        return resp == 1;
    }
}
