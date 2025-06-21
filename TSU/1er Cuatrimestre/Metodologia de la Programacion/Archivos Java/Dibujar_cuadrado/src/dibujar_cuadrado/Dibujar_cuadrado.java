/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dibujar_cuadrado;
import java.util.Scanner;
/**
 *
 * @author clientepreferido
 */
public class Dibujar_cuadrado {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    Scanner teclado = new Scanner(System.in);
        int num;
        System.out.print("Ingrese la dimension del cuadrado: ");
        num = teclado.nextInt();
        if(num >= 0 && num<=100) {
            for(int a = 0; a < num; a++) { //Linea superior del cuadrado
                System.out.print("*");
            }
            System.out.println();
            for(int b = 0; b < num-2; b++) { //centro de la forma del cuadrado
                System.out.print("*");
                for(int c = 0; c < num-2; c++) {
                    System.out.print(" ");
                }
                System.out.println("*");
            }
            for(int d = 0; d < num; d++) { //Linea inferior del cuadrado
                System.out.print("*");
            }
            System.out.print("\n");
        }else {
            System.out.println("Error. El dato a ingresar debe de estar entre 0 y 100");
        }    
    }
}
