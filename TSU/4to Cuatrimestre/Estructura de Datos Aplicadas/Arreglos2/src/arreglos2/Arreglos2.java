/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arreglos2;

/**
 *
 * @author Luis
 */
public class Arreglos2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Elaborar el programa que guarde 10 números aleatorios entre 1 y 100 en un arreglo, 
        imprimir dicho arreglo y después filtrar (imprimir) aquellos números que sean mayores a 50*/
        int[] numeros = new int[10], numMayores = new int[10];
        int j=0, x;
        
        System.out.println("----NUMEROS GENERADOS ALETORIAMENTE-----");
        for (x=0;x<numeros.length;x++) {
            numeros[x] = (int) (Math.random()*100)+1;
            System.out.println("Numero generado = "+numeros[x]);

            if(numeros[x]>50){
                numMayores[j]=numeros[x];
                j++;
            }
        }
        System.out.println("\n");
        System.out.println("-----NUMEROS MAYORES A 50-----");
        for(int i=0;i<numMayores.length;i++) {
            if (numMayores[i]>50) {
                System.out.print(numMayores[i]+ ", " );
            }                
        }
        System.out.println("\n");
    }
}
