/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ordenamiento6;

import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class Ordenamiento6 {

    /**
     * @param args the command line arguments
     */
    
    static Scanner gali = new Scanner(System.in);
    static int numeros[] = new int[5000], x;
    static String letrero;
    
    public static void main(String[] args) {
        /* Desarrolla un programa que ordene un arreglo de 5000 elementos aleatorios (1-100) de manera descendente 
        utilizando los métodos que hemos visto hasta el  momento e imprimir el tiempo que tarda cada método en ordenar 
        el arreglo (milisegundos).
            Burbuja
            Selección
            Inserción
            QuickSort */
    
        int ou;
        long init, end;
        letrero = null;
            System.out.println("------METODOS DE ORDENAMIENTO------");
            System.out.println("Elige una opcion para ordenar numeros de forma descendente: ");
            System.out.println("1. Burbuja");
            System.out.println("2. Selección");
            System.out.println("3. Inserción");
            System.out.println("4. QuickSort");
            System.out.print("Opcion: ");
            ou = gali.nextInt();
            switch (ou) {
                case 1:
                        init = System.currentTimeMillis();
                        rellenarArreglo();
                        burbuja();
                        imprimirArreglo();
                        letrero = "Burbuja";
                        end = System.currentTimeMillis() - init;
                        System.out.println("\n");
                        System.out.println("Tiempo de ordenacion del metodo "+ letrero + ": " + end +" milliseconds");
                    break;
                case 2:
                        init = System.currentTimeMillis();
                        rellenarArreglo();
                        ordenarPorSeleccion();
                        imprimirArreglo();
                        letrero = "Seleccion";
                        end = System.currentTimeMillis() - init;
                        System.out.println("\n");
                        System.out.println("Tiempo de ordenacion del metodo "+ letrero + ": " + end +" milliseconds");
                    break;
                case 3:
                        init = System.currentTimeMillis();
                        rellenarArreglo();
                        insercionDirecta();
                        imprimirArreglo();
                        letrero = "Insercion";
                        end = System.currentTimeMillis() - init;
                        System.out.println("\n");
                        System.out.println("Tiempo de ordenacion del metodo "+ letrero + ": " + end +" milliseconds");
                    break;
                case 4:
                        init = System.currentTimeMillis();
                        rellenarArreglo();
                        quicksort(0, numeros.length - 1);
                        imprimirArreglo();
                        letrero = "Quicksort";
                        end = System.currentTimeMillis() - init;
                        System.out.println("\n");
                        System.out.println("Tiempo de ordenacion del metodo "+ letrero + ": " + end +" milliseconds");
                    break;    
                default:
                    System.out.println("Finalizo el Programa!");;
            }  
            System.out.println("\n"); 
    }
    
    public static void rellenarArreglo(){
        for (x=0;x<numeros.length;x++) {
            numeros[x] = (int) (Math.random()*100)+1;
            //System.out.println("Numero generado = "+numeros[x]); // muestra los numeros almacenados en pantalla
        } 
    }
    
    public static void imprimirArreglo(){
        System.out.println("\n");
            for(int i=0;i<numeros.length;i++) {
                System.out.println(numeros[i] + ", ");               
            }
    }
    
    public static void burbuja() { //Metodo Burbuja
        int auxiliar;
        for (int i = 0; i < numeros.length; i++) {
            for (int j = 1; j < (numeros.length - i); j++) {
                if (numeros[j - 1] < numeros[j]) {
                    auxiliar = numeros[j - 1];
                    numeros[j - 1] = numeros[j];
                    numeros[j] = auxiliar;
                }
            }
        }
    }
     
    public static void ordenarPorSeleccion() { //Metodo Seleccion
        for (int i = 0; i < numeros.length - 1; i++) {
            for (int j = i + 1; j < numeros.length; j++) {
                if (numeros[i] < numeros[j]) {
                    int temporal = numeros[i];
                    numeros[i] = numeros[j];
                    numeros[j] = temporal;
                }
            }
        }
    }
     
    public static void insercionDirecta(){//Metodo Insercion Directa
        int p, j;
        int aux;
        for (p = 1; p < numeros.length; p++){
            aux = numeros[p];
            j = p - 1;
            while ((j >= 0) && (aux > numeros[j])){                              
                numeros[j + 1] = numeros[j];
                j--;
            }
            numeros[j + 1] = aux;
        }
    } 
      
    public static void quicksort(int izq, int der) { //Metodo QuickSort
        int pivote=numeros[izq];
        int i=izq;
        int j=der;
        int aux;
        
        while(i < j){                                  
            while(numeros[i] >= pivote && i < j) {
                i++;
            }
            while(numeros[j] < pivote) {
                j--;
            }
            if (i < j) {                     
               aux= numeros[i];
               numeros[i]=numeros[j];
               numeros[j]=aux;
            }
        }
        numeros[izq]=numeros[j];                            
        numeros[j]=pivote;
        if(izq < j-1){
            quicksort(izq,j-1);
        } 
        if(j+1 < der) {
            quicksort(j+1,der);
        }
    } 
}
