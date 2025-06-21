package ejemplo_generics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Ejemplo_Generics {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        /*
        System.out.println("CREACION DE UN ARRAY GENERICO");
        int[] array_generico = new int[4];
        array_generico[0] = 1;
        array_generico[1] = 2;
        array_generico[2] = 3;
        array_generico[3] =4;
        //RECORREMOS LAS POSICIONES
        for (int i = 0; i < array_generico.length; i++) {
            System.out.println("[" + i + "][" + array_generico[i] + "]");
        }

        System.out.println("AHORA CON UN ARRAYLIST");
        ArrayList<Integer[]> Lista_generica = new ArrayList<Integer[]>();
        Lista_generica.add(new Integer[]{12,13,14});
        Lista_generica.add(new Integer[]{12,13,14});
        Lista_generica.add(new Integer[]{12,13,14});
        Lista_generica.add(new Integer[]{12,13,14});
        Iterator<Integer[]> it = Lista_generica.iterator();
        while (it.hasNext()) {
            Integer vector[]=it.next();
            for (int i = 0; i < vector.length; i++) {
                System.out.print("["+i+"]"+"["+vector[i]+"]");
            }
            System.out.println("");
        }
        /*
            QUE TENDRIA QUE HACER PARA QUE SOLO MIS ARRAYS SOLO
            RECIBAN CADENAS
        
            Tambien puedo crear mi array generico 
            y determinarlo en ejecucion
         */
        System.out.println("Cuantos elementos va a guardar");
        int tam = input.nextInt();
        System.out.println("Que tipo de vector va guardar");
        System.out.println("1. Vector de String");
        System.out.println("1. Vector de Enteros");
        System.out.println("1. Vector de Flotantes");
        Array_Propio arreglo;
        int resp = input.nextInt();
        switch (resp) {
            case 1:
                arreglo = new Array_Propio<String>(tam);
                break;
            case 2:
                arreglo = new Array_Propio<Integer>(tam);
                for (int i = 0; i < tam; i++) {
                    System.out.println("Ingresa el elemento " + i);
                    int dato = input.nextInt();
                    arreglo.set(i, dato);
                    System.out.println("El elemento aniadido es: " + arreglo.get(i));
                }
                break;
            case 3:
                arreglo = new Array_Propio<Float>(tam);
                break;
            default:
                System.out.println("Opcion Invalida");
        }
    }
}
