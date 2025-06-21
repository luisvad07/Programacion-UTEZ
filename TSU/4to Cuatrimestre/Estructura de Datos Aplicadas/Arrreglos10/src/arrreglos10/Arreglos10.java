/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arrreglos10;

import java.util.Random;

/**
 *
 * @author Luis
 */
public class Arreglos10 {

    /**
     * @param args the command line arguments
     */
    static char letras [][] = new char [10][10];
    static int x, l = 0;
            
    public static void main (String[] args) {
        /* Elaborar un programa que llene un arreglo de 10 por 10 con letras aleatorias, 
        al final el programa deberá imprimir cuantas vocales tiene por renglón y cuantas consonantes por columna*/
        Random random = new Random();
        String setOfCharacters = "abcdefghijklmnopqrstuvwxyz";
        
        System.out.println("------LECTURA DE LETRAS ALEATORIAS------");
        for (x = 0; x < 10; x++){         
            for (l = 0; l < 10; l++){
                int randomInt = random.nextInt(setOfCharacters.length());
                char randomChar = setOfCharacters.charAt(randomInt);
                System.out.println("Letra generada aleatoriamente en la posicion ["+x+"] ["+l+"] = "  + randomChar);
                letras[x][l] = randomChar;
            }
            System.out.println("");
        }
        System.out.println("");
        System.out.println("---MATRIZ DE LETRAS---");
        for (x = 0; x < 10; x++){          
            for (l = 0; l < 10; l++){
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
        for (x = 0; x < 10; x++){
            int vocales = 0;
            for (l = 0; l < 10; l++){
                if (Character.toString(letras[x][l]).matches("^([aeiou]).*")) {
                    vocales ++;
                }   
            }   
            System.out.println("Fila #" + (x+1) + " ---- No. Vocales: " + vocales);
        }
        System.out.println("");
        System.out.println("------CONTEO DE COLUMNAS------");
        for (x = 0; x < 10; x++){
            int consonantes = 0;
            for (l = 0; l < 10; l++){
                if (Character.toString(letras[l][x]).matches("^[(bcdfghjklmnpqrstvwxyz)].*")) {
                    consonantes ++;
                }   
            }   
            System.out.println("Columna #" + (x+1) + " ---- No. Consonantes: " + consonantes);
        }
    }
}