/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testordenarnombres;

import java.util.Scanner;

/**
 *
 * @author clientepreferido
 */
public class TestOrdenarNombres {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        OrdenarNombres objeto;
        Scanner teclado = new Scanner(System.in);
        teclado.useDelimiter("\n");
        
        int longitud;
        String nombre;
        
        System.out.print("Ingresa el numero de personas: ");
        longitud = teclado.nextInt();
        objeto = new OrdenarNombres(longitud);
        
        for (int i = 0; i < objeto.longitud(); i++) {
            System.out.print("Ingresa el nombre de la persona "+ (i+1) + ": ");
            nombre = teclado.next();
            objeto.setNombre(i, nombre);
        }
        
        objeto.Ordenar();
        System.out.println("\n");
        for (int i = 0; i < objeto.longitud(); i++) {
            System.out.println((i+1)+" | " + objeto.getNombre(i));
        }
    }
    
}
