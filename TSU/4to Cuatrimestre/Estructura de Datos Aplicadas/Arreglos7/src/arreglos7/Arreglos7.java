/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arreglos7;

import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class Arreglos7 {

    /**
     * @param args the command line arguments
     */
    
    static Scanner leer = new Scanner(System.in);
    static String[] g_juguetes = new String[5];
        
    public static void main(String[] args) {
        /*Desarrollar un programa que guarde juguetes en un arreglo de 5 elementos, al final 
        debe mostrar los 5 juguetes, sabiendo que un juguete tiene los siguientes atributos:
            Nombre (String)
            Marca (String) 
            Costo (double) */
        leer.useDelimiter("\n");
        System.out.println("------JUGUETERIA 3 HERMANOS-------");
        System.out.println("\n");
        ingresarJuguetes();
        for (int i = 0; i < g_juguetes.length; i++) {
            System.out.println(" JUGUETE No. ["+i+"] = "+g_juguetes[i]);
        }
    }
    
    public static void ingresarJuguetes(){
        String nom_juguete;
        String marca_juguete;
        float costo_juguete;
        int z=0;
            while (z < g_juguetes.length) {
            System.out.println("------JUGUETE No. ["+z+"]-------");
            System.out.print("Ingrese nombre del Juguete: ");
            nom_juguete = leer.next();
            System.out.print("Ingrese marca del Juguete: ");
            marca_juguete = leer.next();
            System.out.print("Ingrese costo del Juguete: ");
            costo_juguete=leer.nextFloat();
            g_juguetes[z]= ("Nombre: "+ nom_juguete+ " Marca: " + marca_juguete + " Costo: " + costo_juguete);
            z++;
            System.out.println("\n");
        }
    }
}
