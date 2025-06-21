/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pares_mayores;
import java.util.Scanner;
/**
 *
 * @author clientepreferido
 */
public class Pares_Mayores {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner teclado = new Scanner(System.in);
        int origen[] = {6,18,23,30,55,79,42,85,90,104,78,3,278};
        int destino[] = new int[origen.length];
        int contador = 0;
        for (int i = 0; i < origen.length; i++) { 
            if (origen[i] % 2 == 0 && origen[i] > 25) {
                destino[contador] = origen[i]; //contador como indice
                contador++; //aumentamos el contador
            }
        }
        System.out.println("Mostrar vector origen");
        for (int i = 0; i < origen.length; i++) {
            System.out.println(origen[i]);
        }
        System.out.println("Mostrar Vector destino");
        if (contador == 0) { 
            System.out.println("No hay numeros que cumplan la condicion");
        } else {
            for (int i = 0; i < contador; i++) {
                System.out.println(destino[i]);
            }
        }
    }
}
