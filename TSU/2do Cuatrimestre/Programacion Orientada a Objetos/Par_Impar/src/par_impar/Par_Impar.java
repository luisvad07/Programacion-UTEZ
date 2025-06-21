/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package par_impar;

import java.util.Random;

/**
 *
 * @author clientepreferido
 */
public class Par_Impar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        int[] numeros = new int[20];
        int contpar = 0, contimpar = 0; //contadores
        int i;

        Random rnd = new Random();

        //Leemos los valores por teclado y los guardamos en el array                                              
        System.out.println("Numeros generados para array: ");
        for (i = 0; i < numeros.length; i++) {
            numeros[i] = rnd.nextInt(50);
            System.out.print("\nnumero[" + i + "]= " + numeros[i]);
        }

        int par[] = new int[20]; //Arreglo para almacenar los pares
        int impar[] = new int[20]; //Arreglo para almacenar los impares

        //se recorre el array para contar positivos, negativos y ceros
        for (i = 0; i < numeros.length; i++) {
            if (numeros[i] % 2 == 0) {
                par[contpar] = numeros[i];
                contpar++;
            } else {
                impar[contimpar] = numeros[i];
                contimpar++;
            }
        }

        //Mostramos los arrays
        System.out.println("\n");
        System.out.println("NUMEROS PARES GUARDADOS EN EL ARRAY");
        for (i = 0; i < numeros.length; i++) {
            if (numeros[i] % 2 == 0) {
                System.out.println("Numero Par ----->" + numeros[i]);
            }
        }
        System.out.println("\n");
        System.out.println("NUMEROS IMPARES GUARDADOS EN EL ARRAY");
        for (i = 0; i < numeros.length; i++) {
            if (numeros[i] % 2 != 0) {
                System.out.println("Numero impar ----->" + numeros[i]);
            }
        }
        System.out.println("\n");
        //Determinamos cuantos pares e impares hay
        System.out.println("Numeros Pares: " + contpar);
        System.out.println("Numeros Impares: " + contimpar);
    }

}
