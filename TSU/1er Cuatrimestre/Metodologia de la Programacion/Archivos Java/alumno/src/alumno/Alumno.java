/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alumno;
import java.util.Scanner;
/**
 *
 * @author clientepreferido
 */
public class Alumno {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    String nombre,grupo;
    int grado;
    double promedio;
    Scanner teclado=new Scanner (System.in);
    System.out.println("Introduce tu nombre completo:");
    nombre=teclado.next();
    System.out.println("Introduce el grado que estas cursando (De 1ro a 6to):");
    grado=teclado.nextInt();
    System.out.println("Introduce el grupo en el que cursas (De la A a la J, en mayusculas):");
    grupo=teclado.next();
    System.out.printf("Introduce el promedio obtenido:");
    promedio=teclado.nextDouble();
    System.out.println("Tu nombre completo es: "+ nombre);
    if (grado>=1 && grado<=5) {
        System.out.println("Estas en la escuela, cursando el " + grado + " grado");    
    } else {
        System.out.println("Estas en estadia, cursando el " + grado + " grado");
    }
    if ("A".equals(grupo) || "B".equals(grupo) || "C".equals(grupo) || "D".equals(grupo) || "E".equals(grupo)) {
        System.out.println("Estas en el turno matutino, en el grupo " + grupo);
    } else {
        if ("F".equals(grupo) || "G".equals(grupo) || "H".equals(grupo) || "I".equals(grupo) || "J".equals(grupo)) {
        System.out.println("Estas en el turno vespertino, en el grupo " + grupo);
        }
    }
    if (promedio>=7 && promedio<=10) {
        System.out.println("Estas aprobado, obtiendo " +promedio);
    } else {
        System.out.println("Estas reprobado, obtiendo " +promedio);
    }
    }   
}
