/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arreglo_aleatorio;

/**
 *
 * @author clientepreferido
 */
public class Arreglo_Aleatorio {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    int cant = 10;
    int index = 0;
    int [] aleatorios = new int [cant];
    while(index < cant) {
        int propuesto = (int)(Math.random()*cant);
        boolean repet = false;
        while(!repet) {
            for(int i=0; i<index; i++) {
                if(propuesto == aleatorios[i]) {
                    repet = true;
                    break;
                }
            }
            if(!repet) {
                aleatorios[index] = propuesto;
                index++;
            }
        }
    }
    for (int i=0;i<aleatorios.length;i++){
        System.out.print(aleatorios[i]+ " ");
    }
    System.out.println("");
  }
}
