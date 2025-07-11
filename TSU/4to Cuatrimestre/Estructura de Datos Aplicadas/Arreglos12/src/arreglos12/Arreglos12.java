/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arreglos12;

import java.util.Random;

/**
 *
 * @author Luis
 */
public class Arreglos12 {

    /**
     * @param args the command line arguments
     */
    
    /* Elaborar el programa que guarde en un arreglo bidimensional de 5 x 5, caracteres aleatorios (letras) y lo imprima, 
    después reemplazar los caracteres de la diagonal con un ‘-’, e imprimir el resultado.*/
    static char[][] Characters = new char[5][5]; //matriz de 5x5
    
    public static void main(String[] args) {
        Random random = new Random();
        int i, j;
        String setOfCharacters = "abcdefghijklmnopqrstuvwxyz123456789?-+='#/@";

        System.out.println("------LECTURA DE CARACTERES------");
        for (i = 0; i < Characters.length; i++) {
            for (j = 0; j < Characters.length; j++) {
                int randomInt = random.nextInt(setOfCharacters.length());
                char randomChar = setOfCharacters.charAt(randomInt);
                System.out.println("Caracter generado aleatoriamente en la posicion ["+i+"] ["+j+"] = "  + randomChar);
                Characters[i][j] = randomChar;
            }
        }
        System.out.println("\n");
        System.out.println("---MATRIZ DE CARACTERES---");
        for (i = 0; i < Characters.length; i++) { 
            for (j = 0; j < Characters[i].length; j++) {
                System.out.print("   " + Characters[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("\n");
        existeEnArreglo();
    }
    
    public static void existeEnArreglo() {
        char remplazar = '-';
        char busqueda = '/';
        for (int x = 0; x < Characters.length; x++) {
            for (int i = 0; i < Characters[x].length; i++) {
                if (Characters[x][i] == busqueda) {
                    Characters[x][i] = remplazar;
                }
            }
        }
       System.out.println("---MATRIZ DE CARACTERES REMPLAZADA---");
        for (int i = 0; i < Characters.length; i++) { 
            for (int j = 0; j < Characters[i].length; j++) {
                System.out.print("   " + Characters[i][j] + " ");
            }
            System.out.println();
        }
    }
    
}
