/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calificaciones;
import java.util.Scanner;
/**
 *
 * @author clientepreferido
 */
public class Calificaciones {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    double a,b,c,promedio;
    Scanner teclado=new Scanner (System.in);
    System.out.println("Ingrese la primera calificacion:");
    a=teclado.nextDouble();
    System.out.println("Ingrese la segunda calificacion:");
    b=teclado.nextDouble();
    System.out.println("Ingrese la tercera calificacion:");
    c=teclado.nextDouble();    
    promedio = (a+b+c)/3;
    System.out.println("El promedio es: " + promedio);
    if (promedio>=8.5) {
        System.out.println("Felicidades, has obtenido una beca");    
    }       
    }
}
