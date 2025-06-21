/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio.pkg1;

import java.util.Scanner;

/**
 *
 * @author CC10
 */
public class EJERCICIO1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        final double PI = 3.1416;
        double l = 0.0D, b = 0.0D, h = 0.0D, r = 0.0D, perimetro = 0.0D;
        double fin;
        int num;
        Figuras ope = new Figuras();
        Scanner teclado = new Scanner(System.in);
        do {
            System.out.println("Introduce el numero de la operacion que desea efectuar:");
            System.out.println("1.- Triangulo\n"
                    + "2.- Cuadrado\n"
                    + "3.- Circulo\n"
                    + "4.- Salir");
            num = teclado.nextInt();
            System.out.println("\n");
            switch (num) {
                case 1:
                    if (num != 4) {
                        System.out.printf("Ingresa la medida del lado del Triangulo:");
                        l = teclado.nextDouble();
                    }
                    System.out.println("El perimetro del Triangulo es: " + ope.Triangulo(l));
                    System.out.println("\n");
                    break;
                case 2:
                    if (num != 4) {
                        System.out.printf("Ingresa la medida del lado del Cuadrado:");
                        l = teclado.nextDouble();
                    }
                    System.out.println("El perimetro del Cuadrado es: " + ope.Cuadrado(l));
                    System.out.println("\n");
                    break;
                case 3:
                    if (num != 4) {
                        System.out.printf("Ingresa la medida del radio del Circulo:");
                        r = teclado.nextDouble();
                    }
                    System.out.println("El perimetro del Circulo es: " + ope.Circulo(r));
                    System.out.println("\n");
                    break;
            }
        } while (num != 4);
        System.out.println("GOOD BYE!");
    }

}
