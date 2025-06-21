/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package numeros_inversivos;
import java.util.Scanner;
/**
 *
 * @author CC10
 */
public class Numeros_inversivos {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    int num = 0;
        Scanner teclado=new Scanner (System.in);
        System.out.println("Programa que muestra numeros del N hasta 1");
        System.out.println("Por favor, ingresa un numero: ");
        num=teclado.nextInt();
        while(num>=1) {
            System.out.println(num);//imprime los numeros                                                                         
            num--;
        }
    }
}
