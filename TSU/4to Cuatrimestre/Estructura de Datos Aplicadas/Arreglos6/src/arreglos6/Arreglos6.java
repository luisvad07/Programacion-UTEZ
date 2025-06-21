/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arreglos6;

import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class Arreglos6 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*Desarrollar un programa que pida una palabra en español y si esa palabra se encuentra en el banco 
        (previamente cargado con 20 palabras en inglés) deberá mostrar la traducción en inglés.*/
        //Vocabulario verbos
        Scanner leer = new Scanner(System.in);
        String[][] vocabulary = new String[20][20];
        vocabulary[0][0] = "Begin";
        vocabulary[0][1] = "Bite";
        vocabulary[0][2] = "Buy";
        vocabulary[0][3] = "Hit";
        vocabulary[0][4] = "Keep";
        vocabulary[0][5] = "Find";
        vocabulary[0][6] = "Spend";
        vocabulary[0][7] = "Hold";
        vocabulary[0][8] = "Bet";
        vocabulary[0][9] = "Speak";
        vocabulary[0][10] = "Answer";
        vocabulary[0][11] = "Believe";
        vocabulary[0][12] = "Miss";
        vocabulary[0][13] = "Pray";
        vocabulary[0][14] = "Iron";
        vocabulary[0][15] = "Polish";
        vocabulary[0][16] = "Enjoy";
        vocabulary[0][17] = "Sign";
        vocabulary[0][18] = "Wait";
        vocabulary[0][19] = "Train";

        vocabulary[1][0] = "Empezar";
        vocabulary[1][1] = "Morder";
        vocabulary[1][2] = "Comprar";
        vocabulary[1][3] = "Golpear";
        vocabulary[1][4] = "Mantener";
        vocabulary[1][5] = "Encontrar";
        vocabulary[1][6] = "Gastar";
        vocabulary[1][7] = "Sostener";
        vocabulary[1][8] = "Apostar";
        vocabulary[1][9] = "Hablar";
        vocabulary[1][10] = "Responder";
        vocabulary[1][11] = "Creer";
        vocabulary[1][12] = "Extrañar";
        vocabulary[1][13] = "Respetar";
        vocabulary[1][14] = "Planchar";
        vocabulary[1][15] = "Pulir";
        vocabulary[1][16] = "Disfrutar";
        vocabulary[1][17] = "Señalar";
        vocabulary[1][18] = "Esperar";
        vocabulary[1][19] = "Entrenar";

        System.out.println("-----TRADUCTOR ESPAÑOL/INGLÉS----");
        System.out.print("Escribe una palabra a traducir: ");
        String spanish = leer.next();
        System.out.println("\n");
        int x=0;
        while (x < vocabulary.length) {
            if (spanish.equals(vocabulary[1][x])) {
                System.out.print("Traducción: "+vocabulary[0][x]);
            }
            x++;
        }
        System.out.println("\n");
    }
    
}
