/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package array_numpares;
import java.util.Scanner;
/**
 *
 * @author CC10
 */
public class Array_NumPares {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    Scanner teclado = new Scanner(System.in);
    int par [] = new int[10];
    int valor [] = new int[10];
    int cont = 0;
    for (int i = 0+1; i < 10; i++) {
        System.out.print("Ingresa un número para evaluar #" + (i) + ":"); 
        par[i] = teclado.nextInt();  
        if (par[i]%2==0) { 
            valor[cont] = par[i];
            cont++;
        } 
    } 
    System.out.println("A continuacion, se muestran los sig numeros pares");
    for (int i = 1; i<cont; i++) {
         System.out.println(valor[i]);
    }
    }
}
