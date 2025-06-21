/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arreglos4;

/**
 *
 * @author Luis
 */
public class Arreglos4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*Elabora un programa que ejecute las siguientes acciones: declarar un arreglo con estos elementos 
        {1, 0, 60, -3, 0, 5, -5, 12, 23, -8, -1, 0}
        Y que al final imprima cuales elementos son menores o iguales a 0 e indique su posición*/
        int arr[] = {1, 0, 60, -3, 0, 5, -5, 12, 23, -8, -1, 0};
        int numMayores[] = new int[12];
        int x , j = 0;
        
        System.out.println("----NUMEROS DECLARADOS DEL ARRAY-----");
        for (x=0;x<arr.length;x++) {
            System.out.println("Numero en la posicion ["+x+"] = "+arr[x]);

            if(arr[x]<=0){
                numMayores[j]=arr[x];
                j++;
            }
        }
        
        System.out.println("\n");
        System.out.println("-----NUMEROS MENORES O IGUALES A 0-----");
        for(int i=0;i<numMayores.length;i++) {
            if (numMayores[i]<=0) {
                System.out.print(numMayores[i]+ ", " );
            }                
        }
        System.out.println("\n");
    }
    
}
