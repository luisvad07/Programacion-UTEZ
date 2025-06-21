/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posiciones;
/**
 *
 * @author clientepreferido
 */
public class Posiciones {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    int[] arreglo1={1,2,3,4,5};
    int[] arreglo2=new int[arreglo1.length];
    for(int a=(arreglo1.length-1),j=0;a>=0;a--,j++){
        arreglo2[j]=arreglo1[a];
        System.out.println(arreglo2[j]);
    }    
  } 
}
