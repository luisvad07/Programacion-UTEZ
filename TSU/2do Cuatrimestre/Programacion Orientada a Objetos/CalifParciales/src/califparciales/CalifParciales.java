/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package califparciales;

import java.util.Scanner;

/**
 *
 * @author clientepreferido
 */
public class CalifParciales {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner teclado = new Scanner(System.in);
        teclado.useDelimiter("\n");
        int i;
        String nomalum, materia;
        double suma = 0;

        double[] parciales = new double[6];//Array para calificaciones
        
        System.out.print("Nombre de alumno: ");
        nomalum = teclado.nextLine();
        System.out.print("Nombre de la materia: ");
        materia = teclado.nextLine();
        System.out.print("\n");

        // Entrada de datos
        for (i = 0; i < parciales.length-1; i++) {
            System.out.print("Parcial " + (i + 1) + " Calificacion: ");
            parciales[i] = teclado.nextDouble();
        }

        // Sumar todas las notas
        for (i = 0; i < parciales.length-1; i++) {
            suma = suma + parciales[i];
        }

        // Calcular la calificacion total
        parciales[6] = suma / parciales.length-1;
        System.out.print("\n");
        // Mostrar todas las calificaciones
        System.out.println("Calificaciones totales: ");
        for (i = 0; i < parciales.length-1; i++) {
            System.out.println("Parcial " + (i + 1) + " Calificacion: " + parciales[i]);
        }
        // Mostrar la calif
        System.out.printf("Calificacion de la materia: %.2f %n", parciales[6]);

    }

}
