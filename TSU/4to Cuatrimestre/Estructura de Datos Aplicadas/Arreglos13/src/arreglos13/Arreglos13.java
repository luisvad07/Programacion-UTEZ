/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arreglos13;

import java.util.Random;

/**
 *
 * @author Luis
 */
public class Arreglos13 {

    /**
     * @param args the command line arguments
     */
    static char letras [][] = new char [5][5];
    static int x, l = 0;
            
    public static void main (String[] args) {
        /* Elaborar el programa que guarde en un arreglo bidimensional de 5 x 5 
        caracteres aleatorios (letras) y lo imprima, después debe indicar cuantas vocales hay por fila*/
        Random random = new Random();
        String setOfCharacters = "abcdefghijklmnopqrstuvwxyz";
        
        System.out.println("------LECTURA DE LETRAS ALEATORIAS------");
        for (x = 0; x < letras.length; x++){         
            for (l = 0; l < letras[x].length; l++){
                int randomInt = random.nextInt(setOfCharacters.length());
                char randomChar = setOfCharacters.charAt(randomInt);
                System.out.println("Letra generada aleatoriamente en la posicion ["+x+"] ["+l+"] = "  + randomChar);
                letras[x][l] = randomChar;
            }
            System.out.println("");
        }
        System.out.println("");
        System.out.println("---MATRIZ DE LETRAS---");
        for (x = 0; x < letras.length; x++){          
            for (l = 0; l < letras[x].length; l++){
                System.out.print("   " + letras[x][l] + " ");
            }
            System.out.println("");
        }
        System.out.println("");
        System.out.println("\n");
        imprimirMatriz();
    }
    
    public static void imprimirMatriz() {
        System.out.println("------CONTEO DE FILAS------");
        for (x = 0; x < letras.length; x++){
            int vocales = 0;
            for (l = 0; l < letras[x].length; l++){
                if (Character.toString(letras[x][l]).matches("^([aeiou]).*")) {
                    vocales ++;
                }   
            }   
            System.out.println("Fila #" + (x+1) + " ---- No. Vocales: " + vocales);
        }
        System.out.println("");
    }
}
