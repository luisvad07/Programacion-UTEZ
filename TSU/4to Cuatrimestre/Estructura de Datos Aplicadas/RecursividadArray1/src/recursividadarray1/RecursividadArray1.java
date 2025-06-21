/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursividadarray1;

/**
 *
 * @author Luis
 */
public class RecursividadArray1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int[] numeros = new int[10];
        
        for (int x=0;x<numeros.length;x++) {
            numeros[x] = (int) (Math.random()*100)+1;
            System.out.println(numeros[x]);
        }
        
		int mayor = numeros[0];
		for (int x = 1; x < numeros.length; x++) {
			if (numeros[x] > mayor) {
				mayor = numeros[x];
			}
		}
                if (mayor > 50) {
                    System.out.println("El mayor es: " + mayor);
                }
    }
    
}
