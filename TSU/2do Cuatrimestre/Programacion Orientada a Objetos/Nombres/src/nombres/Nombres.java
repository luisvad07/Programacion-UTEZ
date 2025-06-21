/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nombres;

import java.util.Scanner;

/**
 *
 * @author clientepreferido
 */
public class Nombres {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner teclado = new Scanner(System.in);
        String nomPer1, apePa1, apeMa1, nomPer2, apePa2, apeMa2, nomPer3, apePa3, apeMa3;

        /*Ingreso de Datos Persona 1*/
        System.out.println("Ingresa el nombre de la Persona 1:");
        nomPer1 = teclado.next();
        System.out.println("Ingresa el apellido paterno de la Persona 1:");
        apePa1 = teclado.next();
        System.out.println("Ingresa el apellido materno de la Persona 1:");
        apeMa1 = teclado.next();
        System.out.println("\n");
        /*Ingreso de Datos Persona 2*/
        System.out.println("Ingresa el nombre de la Persona 2:");
        nomPer2 = teclado.next();
        System.out.println("Ingresa el apellido paterno de la Persona 2:");
        apePa2 = teclado.next();
        System.out.println("Ingresa el apellido materno de la Persona 2:");
        apeMa2 = teclado.next();
        System.out.println("\n");
        /*Ingreso de Datos Persona 3*/
        System.out.println("Ingresa el nombre de la Persona 3:");
        nomPer3 = teclado.next();
        System.out.println("Ingresa el apellido paterno de la Persona 3:");
        apePa3 = teclado.next();
        System.out.println("Ingresa el apellido materno de la Persona 3:");
        apeMa3 = teclado.next();
        System.out.println("\n");

        String person1, person2, person3;
        person1 = nomPer1 + " ".concat(apePa1 + " ").concat(apeMa1);
        person2 = nomPer2 + " ".concat(apePa2 + " ").concat(apeMa2);
        person3 = nomPer3 + " ".concat(apePa3 + " ").concat(apeMa3);

        if (person1.compareToIgnoreCase(person2) < 0) { //Caso 1,2,3
            if (person1.compareToIgnoreCase(person3) < 0) {
                if (person2.compareToIgnoreCase(person3) < 0) {
                    System.out.println(person1 + "\n" + 
                                       person2 + "\n" +
                                       person3);
                }
            }
        }
        
        if (person3.compareToIgnoreCase(person2) < 0) { //Caso 3,2,1
            if (person3.compareToIgnoreCase(person1) < 0) {
                if (person2.compareToIgnoreCase(person1) < 0) {
                    System.out.println(person3 + "\n" + 
                                       person2 + "\n" +
                                       person1);
                }
            }
        }
        
        if (person2.compareToIgnoreCase(person1) < 0) { //Caso 2,1,3
            if (person2.compareToIgnoreCase(person3) < 0) {
                if (person1.compareToIgnoreCase(person3) < 0) {
                    System.out.println(person2 + "\n" + 
                                       person1 + "\n" +
                                       person3);
                }
            }
        }
        
        if (person3.compareToIgnoreCase(person1) < 0) { //Caso 3,1,2
            if (person3.compareToIgnoreCase(person2) < 0) {
                if (person1.compareToIgnoreCase(person2) < 0) {
                    System.out.println(person3 + "\n" + 
                                       person1 + "\n" +
                                       person2);
                }
            }
        }
        
        if (person1.compareToIgnoreCase(person3) < 0) { //Caso 1,3,2
            if (person1.compareToIgnoreCase(person2) < 0) {
                if (person3.compareToIgnoreCase(person2) < 0) {
                    System.out.println(person1 + "\n" + 
                                       person3 + "\n" +
                                       person2);
                }
            }
        }
        
        if (person2.compareToIgnoreCase(person3) < 0) { //Caso 2,3,1
            if (person2.compareToIgnoreCase(person1) < 0) {
                if (person3.compareToIgnoreCase(person1) < 0) {
                    System.out.println(person2 + "\n" + 
                                       person3 + "\n" +
                                       person1);
                }
            }
        }
    }
}
