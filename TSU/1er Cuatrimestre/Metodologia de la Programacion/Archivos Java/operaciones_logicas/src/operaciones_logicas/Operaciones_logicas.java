/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operaciones_logicas;
import java.util.Scanner;
/**
 *
 * @author clientepreferido
 */
public class Operaciones_logicas {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    String opera_logica,function;
    Scanner teclado=new Scanner (System.in);
    System.out.println("Ingresa la función logica en Mayusculas (AND/OR)");
    opera_logica=teclado.next();
    if ("AND".equals(opera_logica)) {
        System.out.println("Ingresa en Mayusculas tu operación en letras (Ejemplo=VF)");
        System.out.println("Verdadero/Falso");
        function=teclado.next();
        switch (function) {
        case "VV":
        System.out.println ("Verdadero");
        break;
        case "VF":
        System.out.println ("Falso");
        break;    
        case "FV":
        System.out.println ("Falso"); 
        break;
        case "FF":
        System.out.println ("Falso"); 
        break;
        default: //Otra opcion
        System.out.printf("Opcion no valida");
        break;    
        }
    } else {
        if ("OR".equals(opera_logica)) {
        System.out.println("Ingresa en Mayusculas tu operación en letras (Ejemplo=VF)");
        System.out.println("Verdadero/Falso");
        function=teclado.next();
        switch (function) {
        case "VV":
        System.out.println ("Verdadero");
        break;
        case "VF":
        System.out.println ("Verdadero");
        break;    
        case "FV":
        System.out.println ("Verdadero"); 
        break;
        case "FF":
        System.out.println ("Falso"); 
        break;
        default: //Otra opcion
        System.out.printf("Opcion no valida");
        break;    
        }
    }      
    }  
}
}
