/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package num_pares;
import java.util.Scanner;
/**
 *
 * @author clientepreferido
 */
public class Num_pares {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    Scanner sc = new Scanner(System.in);
    int x, y;
        //Lectura de dos números enteros A Y B
        //A debe ser menor que B
        do {
            System.out.print("Ingresa el primer numero: ");
            x = sc.nextInt();
            System.out.print("Ingresa el segundo numero: ");
            y = sc.nextInt();
            if (x >= y) {
                System.out.println("Error! El segundo número debe ser mayor que el primero");                  
            }
        } while (x >= y);
        System.out.println("Números pares desde el num:  " + x + " hasta el num: " + y + " : "); //Imprimirr los números pares
        for (int a = x; a <= y; a++) {
            if (a % 2 == 0) {
                System.out.println(a);
            }
        }    
    }
}
