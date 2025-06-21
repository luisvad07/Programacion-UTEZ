/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package examenrecupera4a;

import java.util.Scanner;

/**
 *
 * @author CC7
 */
public class Formula {

    /**
     * @param args the command line arguments
     */
    static Scanner s = new Scanner(System.in);
    static String cadena;
    static PilaEnlazada tratamiento = new PilaEnlazada();
    
    public static void main(String[] args) {
        int opc=0;
        int i=1;
        System.out.println("--- TRATAMIENTO DE FORMULAS ----");
            do{
                System.out.print("Insertar Formula #"+i+": ");
                cadena = s.next();
                if (balanceada()) {
                    System.out.println("Está correctamente balanceada.");
                } else {
                    System.out.println("No está correctamente balanceada.");
                }
                System.out.println("¿Desea agregar otra formula?");
                System.out.println("1.Si");
                System.out.println("2.No");
                System.out.print("Opcion: ");
                opc=s.nextInt();
                i++;
        }while(opc==1);
        tratamiento.imprimir();
    }
    
    public static boolean balanceada() {    
    	for (int i = 0 ; i < cadena.length() ; i++) {
    	    if (cadena.charAt(i) == '(' || cadena.charAt(i) == '[' || cadena.charAt(i) == '{') {
    	    	tratamiento.insertar(cadena.charAt(i));
    	    } else {
    	    	if (cadena.charAt(i)==')') {
    	    	    if (tratamiento.extraer()!='(') {
    	    	        return false;
    	    	    }
    	     	} else {
    	    	    if (cadena.charAt(i)==']') {
    	    	        if (tratamiento.extraer()!='[') {
    	    	            return false;
    	    	        }
    	    	    } else {
    	    	        if (cadena.charAt(i)=='}') {
    	    	            if (tratamiento.extraer()!='{') {
    	    	                return false;
    	    	            }
    	    	        }
    	    	    }
    	        }
   	    }   
        }
        return tratamiento.vacia();
    }
}
