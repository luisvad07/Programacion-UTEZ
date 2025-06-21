/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu_operaciones;

import java.util.Scanner;

/**
 *
 * @author clientepreferido
 */
public class Menu_Operaciones {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        double a = 0.0D, b = 0.0D, result = 0.0D;
        int num;
        Menu men = new Menu();
        Scanner teclado = new Scanner(System.in);
        do {
            System.out.println("Introduce el numero de la operacion que desea efectuar:");
            System.out.println("1.- Suma\n"
                    + "2.- Resta\n"
                    + "3.- Multiplicación\n"
                    + "4.- División\n"
                    + "5.- Salir");
            num = teclado.nextInt();

            if (num != 5) {
                System.out.printf("Ingresa el primer numero:");
                a = teclado.nextDouble();
                System.out.printf("Ingresa el segundo numero:");
                b = teclado.nextDouble();
            }

            switch (num) {
                case 1:
                    System.out.println("La suma es: " + men.sumar(a, b));
                    System.out.println("\n");
                    break;
                case 2:
                    System.out.println("La resta es: " + men.restar(a, b));
                    System.out.println("\n");
                    break;
                case 3:
                    System.out.println("La multiplicacion es: " + men.multiplicar(a, b));
                    System.out.println("\n");
                    break;
                case 4:
                    System.out.println("La division es: " + men.dividir(a, b));
                    System.out.println("\n");
                    break;
            }
        } while (num != 5);
        System.out.println("\n");
        System.out.println("GOOD BYE!");
    }
}
