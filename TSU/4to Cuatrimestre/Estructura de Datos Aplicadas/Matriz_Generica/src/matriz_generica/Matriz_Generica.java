/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matriz_generica;

import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class Matriz_Generica {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Llama la clase Matriche para manipular la Matriz
        Matriche matriz;
        
        Scanner CR8 = new Scanner(System.in);
        System.out.print("Numeros de Filas para la matriz: ");
        int feel = CR8.nextInt();
        System.out.print("Numeros de Columnas para la matriz: ");
        int colombie = CR8.nextInt();
        matriz = new Matriche(feel,colombie);
        int tokle, value;
        
        do {
            System.out.println("-------------MENU DE MATRIZ-------------------");
            System.out.println("Selecciona la opcion" +
                    "\n1.- Insertar registros en la matriz" +
                    "\n2.- Devolver matriz" +
                    "\n3.- No. de Columnas" +
                    "\n4.- No. de Filas" +
                    "\n5.- Devolver en String." +
                    "\n6.- Salir");
            System.out.print("Opcion: ");
            tokle = CR8.nextInt();

            switch (tokle) {
                case 1:
                    for (int x=0; x < matriz.longt1(); x++) {
                        for (int y=0; y < matriz.longt2(); y++) {
                          System.out.print("Introduzca el elemento [" + x + "," + y + "] : ");
                          value = CR8.nextInt();
                          matriz.setMatriz(value);
                        }
                    }
                    break;
                case 2:
                    for (int x=0; x < matriz.longt1(); x++) {
                        for (int y=0; y < matriz.longt2(); y++) {
                            System.out.println(" | " + matriz.getMatriz() + " | ");
                        }
                    }
                    break;
                case 3:
                    System.out.println("No de Columnas: "+matriz.longt1());
                    break;
                case 4:
                    System.out.println("No de Filas: "+matriz.longt2());
                    break;
                case 5:
                    formaString(matriz);
                    break;
                case 6:
                    System.out.println("Saliendo :)");
                    break;     
                default:
                    System.out.println("Opcion No Valida!");
            }
            System.out.println("\n");
        } while (tokle != 6);
        
    }
    
    //Metodo que devuelve en forma de String la información del arrayList
    public static void formaString(Matriche matriz){
        System.out.println("El arrayList en String es ... "+matriz.toString());
    }
    
}
