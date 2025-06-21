/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package numeros_sucesivos;
import java.util.Scanner;
/**
 *
 * @author CC10
 */
public class Numeros_sucesivos {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    int i = 1, num;
        Scanner teclado=new Scanner (System.in);
        System.out.println("Programa que muestra numeros del 1 hasta N");
        System.out.println("Por favor, ingresa un numero: ");
        num=teclado.nextInt();
        do{
            System.out.println(i);//imprime los numeros 
            i++;
        }
        while(i<=num);    
    }
}
