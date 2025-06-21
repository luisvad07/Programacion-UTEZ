/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrices;
import java.util.Scanner;
/**
 *
 * @author CC-6
 */
public class Matrices {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    int longt;
    int matriz[][];
    int opcion;
    int suma=0;
    int eleccion;
    int prom;
    boolean salir = false;
    Scanner teclado=new Scanner(System.in);
        System.out.println("Ingresa el tamaño del arreglo");
        longt=teclado.nextInt();
        matriz=new int [longt][longt];
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.println("Escribe un numero de la matriz en la posicion " + i + " " + j);
                matriz[i][j]=teclado.nextInt();
            }
        }
        System.out.println("\n");
        for (int i = 0; i < matriz.length; i++) { //Mostramos Matriz
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j]+"|");
            }
            System.out.println("");
        }
        do {
        System.out.println("\n");    
        System.out.println("Menu");
            System.out.println("1. Sumar fila");
            System.out.println("2. Sumar columna");
            System.out.println("3. Suma diagonal principal");
            System.out.println("4. Suma diagonal inversa");
            System.out.println("5. Media de los elementos");
            System.out.println("6. Salir");
            System.out.println("Elije una opcion");
        opcion=teclado.nextInt();
        switch(opcion){
            case 1:
                System.out.println("Elige una fila que quieras sumar");
                eleccion=teclado.nextInt();
                eleccion=eleccion-1;
                for (int i = 0; i < matriz.length; i++) {
                    for (int j = 0; j < matriz.length; j++) {
                        suma= suma+matriz[i][j];
                    }
                    if (i==eleccion) {
                        System.out.println("La suma de la fila " + (i+1) + " es igual a: " + suma);
                    }
                    suma=0;
                }
                break;
            case 2:
                System.out.println("Elige una columna que quieras sumar");
                eleccion=teclado.nextInt();
                eleccion=eleccion-1;
                for (int j = 0; j < matriz.length; j++) {
                    for (int i = 0; i < matriz.length; i++) {
                        suma=suma+matriz[i][j];
                    }
                    if (j==eleccion) {
                        System.out.println("La suma de la columna "+ (j+1) +" es igual a: "+suma);
                    }
                    suma=0;
                }
                break;
            case 3:
                for (int i = 0; i < matriz.length; i++) {
                    for (int j = 0; j < matriz.length; j++) {
                        if (i==j) {
                          suma=suma+matriz[i][j];
                        }
                    }
                }
                System.out.println("La suma de la diagonal principal es: "+suma);
                break;
            case 4:
                for (int i = 0; i < matriz.length; i++) {
                    for (int j = 0; j < matriz.length; j++) {
                        if (i+j==(matriz.length-1)) {
                          suma=suma+matriz[i][j];
                        }
                    }
                }
                System.out.println("La suma de la diagonal inversa es: "+suma);
                break;
            case 5:
                for (int i = 0; i < matriz.length; i++) {
                    for (int j = 0; j < matriz.length; j++) {
                        suma=suma+matriz[i][j];
                    }
                }
                prom=suma/(longt*longt);
                System.out.println("El promedio de todos los valores de la matriz es: "+prom);
                break;
            case 6:
                    salir = true;
                    break;
            default:
                    System.out.println("ERROR DE OPCION!");    
                    break;
        }
        } while (!salir);
        System.out.println("FIN");
    }
}

