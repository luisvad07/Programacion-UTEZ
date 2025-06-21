/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arreglos5;

import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class Arreglos5 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*Desarrollar un programa que pida una palabra al usuario y cada letra la guarde en un arreglo, posteriormente , 
        debe solicitar una palabra con cada letra de ese arreglo y guardarla en otro al final, debe imprimir ambos.*/
        String palabra,palabraArr;
        Scanner leer = new Scanner(System.in);
        System.out.println("-----ARRAYS PALABRAS------");
        System.out.print("Ingrese una palabra random: ");
        palabra = leer.next();
        String palabraMayus = palabra.toUpperCase();

        char letras[] = palabraMayus.toCharArray();
        String multi[] = new String[letras.length];
        
        System.out.println("\n");
        int x=0;
        while (x < letras.length) {
            System.out.print("Ingresa una palabra random que inicie con la letra " + letras[x] + ": ");
            palabraArr = leer.next();
            String PalabraTS = palabraArr.toUpperCase().charAt(0) + palabraArr.substring(1, palabraArr.length()).toLowerCase();
            multi[x] = PalabraTS;
            x++;
        }
        System.out.println("\n");
        System.out.println("----------LETRAS CONVERTIDAS A UN ARRAY-----------");
        for (int a = 0; a < letras.length; a++) {
            System.out.print(letras[a]+" ");

        }
        System.out.println("\n");
        System.out.println("-----PALABRAS INICIALES DEL ARRAY-----");
        for (int b = 0; b < multi.length; b++) {
            System.out.println(multi[b]);
        }
    }
    
}
