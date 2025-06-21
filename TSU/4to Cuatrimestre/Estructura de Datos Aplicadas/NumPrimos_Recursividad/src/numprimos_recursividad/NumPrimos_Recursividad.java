/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package numprimos_recursividad;

import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class NumPrimos_Recursividad {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {    
    int max = 30;
        
    for(int x = 2; x <= max; x++){
        if(esPrimo(x)){
            System.out.println(x+" es primo");
        }
    }
       
}
    
public static boolean esPrimo(int num){
    int cont = 2;
    boolean primo=true;
    while ((primo) && (cont!=num)){
      if (num % cont == 0)
            primo = false;
      cont++;
    }
    return primo;
}
}
