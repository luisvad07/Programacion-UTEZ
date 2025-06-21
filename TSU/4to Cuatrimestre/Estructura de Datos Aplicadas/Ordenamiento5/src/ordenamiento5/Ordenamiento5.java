/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ordenamiento5;

import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class Ordenamiento5 {

    /**
     * @param args the command line arguments
     */
    static Scanner gali = new Scanner(System.in);
    
    public static void main(String[] args) {
        /*
            Implementar un programa que ordene una baraja desordenada utilizando inserción (2 arrays)
            Implementar un programa que ordene una baraja desordenada utilizando inserción (Solo con 1 array)
        */
        int ou;
        do {
            System.out.println("----SISTEMA DE BARAJAS-----");
            System.out.println("Elige una opcion para contar, barajear y ordenar: ");
            System.out.println("1. Una Baraja");
            System.out.println("2. Dos Barajas");
            System.out.println("3. Salir");
            System.out.print("Opcion: ");
            ou = gali.nextInt();
            switch (ou) {
                case 1:
                    if (confirmarCambio()) {
                        baraja();
                    } else {
                        System.out.println("\n");
                        System.out.println("Se cancelo la operacion!...");
                    }
                    break;
                case 2:
                    if (confirmarCambio()) {
                        deuxBarajas();
                    } else {
                        System.out.println("\n");
                        System.out.println("Se cancelo la operacion!...");
                    }
                    break;
                case 3:
                    System.out.println("Finalizado...");
                    break;
                default:
                    System.out.println("Opcion no valida, intenta de nuevo!");;
            }  
            System.out.println("\n");
        } while (ou!=3);
        
        
    }
    
    public static void baraja(){
        System.out.println("\n");
        int baraja[], cards, F, A;
        System.out.print("No. de Cartas en la Baraja: ");
        cards = gali.nextInt();
        baraja = new int[cards];
        System.out.println("\n");
        int i =0;
        while (i < baraja.length) {
            System.out.print("Valor de la carta [" + (i + 1)+ "] = ");
            baraja[i] = gali.nextInt();
            i++;
        }

        for (int u = 0; u < cards; u++) { //Se ordena la baraja (Insercion)
            F = u;
            A = baraja[u];
            while ((F > 0) && (baraja[F - 1] > A)) {
                baraja[F] = baraja[F - 1];
                F--;
            }
            baraja[F] = A;
        }
        System.out.println("\n");
        System.out.print("BARAJA : ");
        for (int a = 0; a < cards; a++) {
            System.out.print(baraja[a] + " , ");
        }
        System.out.println("");
    }
    
    public static void deuxBarajas(){
        System.out.println("\n");
        int[] baraja1, baraja2;
        int cards, pokers, F, A, G, J;
        System.out.print("No. de Cartas en la Baraja 1: ");
        cards = gali.nextInt();
        System.out.print("No. de Cartas en la Baraja 2: ");
        pokers = gali.nextInt();
        baraja1 = new int[cards];
        baraja2 = new int[pokers];
        System.out.println("\n");
        int i =0;
        while (i < baraja1.length) {
            System.out.print("Valor de la carta [" + (i + 1)+ "] de la Baraja 1 = ");
            baraja1[i] = gali.nextInt();
            i++;
        }
        int k =0;
        while (k < baraja2.length) {
            System.out.print("Valor de la carta [" + (k + 1)+ "] de la baraja 2 = ");
            baraja2[k] = gali.nextInt();
            k++;
        }
        
        for (int a = 0; a < cards; a++) { //Se ordena la baraja 1 (Insercion)
            F = a;
            A = baraja1[a];
            while ((F > 0) && (baraja1[F - 1] > A)) {
                baraja1[F] = baraja1[F - 1];
                F--;
            }
            baraja1[F] = A;
        }
        
        for (int b = 0; b < pokers; b++) { //Se ordena la baraja 2 (Insercion)
            G = b;
            J = baraja2[b];
            while ((G > 0) && (baraja2[G - 1] > J)) {
                baraja2[G] = baraja2[G - 1];
                G--;
            }
            baraja2[G] = J;
        }
        System.out.println("\n");
        System.out.print("BARAJA 1: ");
        for (int a = 0; a < cards; a++) {
            System.out.print(baraja1[a] + " , ");
        }
        System.out.println("\n");
        System.out.print("BARAJA 2: ");
        for (int b = 0; b < pokers; b++) {
            System.out.print(baraja2[b] + " , ");
        }
        System.out.println("");
    }
    
    public static boolean confirmarCambio() {
        int resp;
        Scanner siuu = new Scanner(System.in);
        System.out.println("\n");
        System.out.println("¿Estas seguro de realizar la operacion?");
        System.out.println("1.- Aceptar");
        System.out.println("2.- Cancelar");
        System.out.print("Respuesta: ");
        resp = siuu.nextInt();
        siuu.skip("\n");
        return resp == 1;
    }
}
