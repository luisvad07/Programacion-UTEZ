/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arreglos8;

import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class Arreglos8 {

    /**
     * @param args the command line arguments
     */
    static char abecedario[] = new char[5];
    static String palabra[] = new String[abecedario.length];
    static Scanner leer = new Scanner(System.in);
    
    public static void main(String[] args) {
        /*Desarrollar un programa que solicite al usuario 5 letras del abecedario y almacenarlas en un array, 
        posteriormente el programa solicitará al usuario ingresar una palabra que inicie con la letra correspondiente. 
        En caso contrario solicitará nuevamente hasta ser correcto. 
            Ejemplo: 
            a, e, i, o ,u avión, elefante, iguana, oso, unidad*/
        int j=0;
        while (j < abecedario.length) {
            System.out.print("Ingresa cualquier letra del abecedario: ");
            abecedario[j] = leer.next().charAt(0);
            j++;
        }
        System.out.println("\n");
        ingresarPalabras();
        System.out.println("\n");
        System.out.println("----------LETRAS CONVERTIDAS A UN ARRAY-----------");
        for (int i = 0; i < abecedario.length; i++) {
            System.out.print(abecedario[i]+" ");

        }
        System.out.println("\n");
        System.out.println("-----PALABRAS INICIALES DEL ARRAY-----");
        for (int i = 0; i < palabra.length; i++) {
            System.out.println(palabra[i]);
        }
    }
    
    public static void ingresarPalabras(){
        String word;
        for (int i = 0; i < palabra.length; i++) {
            do {
                System.out.print("Escribe una palabra con la letra " + abecedario[i] + ": ");
                word = leer.next();        
            } while (!(word.charAt(0) == abecedario[i]));
            palabra[i] = word;
        }
    }
}
