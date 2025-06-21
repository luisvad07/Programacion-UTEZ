/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arreglos9;

import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class Arreglos9 {

    /**
     * @param args the command line arguments
     */
    
    static Scanner sis = new Scanner(System.in);
    static String[] Autos_nuevos = new String[5];
    static float result = 0;
        
    public static void main(String[] args) {
        /*Desarrollar un programa que guarde juguetes en un arreglo de 5 elementos, al final 
        debe mostrar los 5 juguetes, sabiendo que un juguete tiene los siguientes atributos:
            Nombre (String)
            Marca (String) 
            Costo (double) */
        sis.useDelimiter("\n");
        System.out.println("------SISTEMA DE AUTOS-------");
        System.out.println("\n");
        ingresarAutos();
        System.out.println("\n");
        for (int i = 0; i < Autos_nuevos.length; i++) {
            System.out.println(" AUTO No. ["+i+"] = "+Autos_nuevos[i]);
        }
    }
    
    public static void ingresarAutos(){
        String marca_auto;
        String modelo_auto;
        float costo_auto;
        int z=0;
        while (z < Autos_nuevos.length) {
            System.out.println("------AUTO No. ["+z+"]-------");
            System.out.print("Ingrese marca del Auto: ");
            marca_auto = sis.next();
            System.out.print("Ingrese modelo del Auto: ");
            modelo_auto = sis.next();
            System.out.print("Ingrese costo del Auto: ");
            costo_auto=sis.nextFloat();
            result += costo_auto;
            Autos_nuevos[z]= ("Marca: "+ marca_auto+ " Modelo: " + modelo_auto + " Costo: " + costo_auto);
            z++;
            System.out.println("\n");
        }
        System.out.println("\n");
        System.out.println("Suma total de los Carros: " + result);
    }
    
}
