/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejemplo_seleccion;

/**
 *
 * @author Luis
 */
public class Ejemplo_Seleccion {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int[] arr = {5,2,8,7,1,0};
        int n = arr.length;
        for (int i = 0; i < n-1; i++) {
            int index = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[index]) {
                    index = j;
                }
            }
            int smallerNumber = arr[index];
            arr[index] = arr[i];
            arr[i] = smallerNumber;
            System.out.println("Pasada # "+(i+1)+" ");
        }
        
        
    }
    
}
