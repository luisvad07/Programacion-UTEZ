/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package array_valores;
import java.util.Scanner;
/**
 *
 * @author CC-6
 */
public class Array_Valores {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner teclado=new Scanner (System.in);
        int []lista= new int [10];
        for(int i=0;i<lista.length;i++){
            System.out.println("Introduce un número");
            lista[i]=teclado.nextInt();
        }    
        for(int i=0;i<lista.length;i++){
            System.out.println("En el indice "+i+" esta el valor "+lista[i]);
        }
        }
    }
