/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prueba;
import java.util.Scanner;
/**
 *
 * @author clientepreferido
 */
public class Prueba {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int numero = 0;
        Scanner leer = new Scanner(System.in);
        persona persona1 = new persona();
        persona persona2 = new persona();
        System.out.println(persona1);
        persona1.nombre= "Pedro";
        persona1.edad = 18;
        persona1.estatura= 2.00;
        persona1.direccion= "Cuernavaca";
        
        persona2.nombre= "Juanito";
        persona2.edad = 20;
        persona2.estatura= 1.50;
        persona2.direccion= "Temixco";
        
        System.out.println("El nombre de la persona 1 es "+persona1.nombre);
        System.out.println("La edad de la persona 1 es "+persona1.edad);
        persona1.incrementarEdad();
        System.out.println("La edad de la persona 1 es "+persona1.edad);
        System.out.println("La estatura de la persona 1 es "+persona1.estatura);
        persona1.incrementarEstatura();
        System.out.println("La estatura de la persona 1 es "+persona1.estatura);
        System.out.println("La direccion de la persona 1 es "+persona1.direccion);
        System.out.println("---------------------------------------------------------");
        System.out.println("El nombre de la persona 2 es "+persona2.nombre);
        System.out.println("La edad de la persona 2 es "+persona2.edad);
        persona2.incrementarEdad();
        System.out.println("La edad de la persona 2 es "+persona2.edad);
        System.out.println("La estatura de la persona 2 es "+persona2.estatura);
        persona2.incrementarEstatura();
        System.out.println("La estatura de la persona 2 es "+persona2.estatura);
        System.out.println("La direccion de la persona 2 es "+persona2.direccion);
    }
}
