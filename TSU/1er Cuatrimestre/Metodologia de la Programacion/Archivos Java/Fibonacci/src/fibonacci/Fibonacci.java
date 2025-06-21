/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fibonacci;
import java.util.Scanner;
/**
 *
 * @author clientepreferido
 */
public class Fibonacci {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    Scanner sc = new Scanner(System.in);
        int n,fib1,fib2,d;
        do{ //Ingreso del numero
            System.out.print("Ingresa un numero mayor que 1: ");
            n = sc.nextInt();
        }while(n<=1);
        System.out.println("Los " + n + " primeros numeros de la serie de Fibonacci son:");                 
        fib1=1;
        fib2=1;
        System.out.print(fib1 + " ");
        for(d=2;d<=n;d++){ //Sucesiones de Fibonacci
            System.out.print(fib2 + " ");
            fib2 = fib1 + fib2;
            fib1 = fib2 - fib1;
        }
        System.out.println();
    }
}
