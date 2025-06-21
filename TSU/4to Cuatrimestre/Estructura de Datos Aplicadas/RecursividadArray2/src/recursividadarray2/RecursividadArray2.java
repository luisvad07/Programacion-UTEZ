/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursividadarray2;

import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class RecursividadArray2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        int[] n = new int[10] ;
        int num = 0;
        for(int i=1;i<=n.length;i++) {
                System.out.print("Ingrese un numero: ");
                num=teclado.nextInt();
                num = num + 1;
        }
        System.out.println("La suma de los numeros es de: " + num);
         int mayor = n[0];
		for (int x = 1; x < n.length; x++) {
			if (n[x] > mayor) {
				mayor = n[x];
			}
		}
                System.out.println("El mayor es: " + mayor);
    }
    
}
