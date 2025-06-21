/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejem_genericos;

import java.util.ArrayList;
import java.util.Iterator;
/**
 *
 * @author Luis
 */
public class Ejem_Genericos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*System.out.println("CREACION DE UN ARRAY GENERICO");
        int[] array_generico = new int[4];
        array_generico[0] = 1;
        array_generico[1] = 2;
        array_generico[2] = 3;
        array_generico[3] = 4;
        //Recorremos Posiciones
        for (int i = 0; i < array_generico.length; i++) {
                System.out.println("["+i+"]"+"["+array_generico[i]+"]");
        }
        */
        
        
        System.out.println("CREACION DE UN ARRAYLIST");
        ArrayList<Integer[]> Lista_generica = new ArrayList<Integer[]>();
        Lista_generica.add(new Integer[]{1,2,3,4});
        Lista_generica.add(new Integer[]{1,2,3,4});
        Lista_generica.add(new Integer[]{1,2,3,4});
        Lista_generica.add(new Integer[]{1,2,3,4});
        Iterator<Integer[]> it = Lista_generica.iterator(); // Interador es Generico
        while (it.hasNext()) {
            Integer vector[] = it.next();
            for (int i = 0; i < vector.length; i++) {
                System.out.println("["+i+"]"+"["+vector[i]+"]");
            }
            System.out.println("");
        }
    }
    
}
