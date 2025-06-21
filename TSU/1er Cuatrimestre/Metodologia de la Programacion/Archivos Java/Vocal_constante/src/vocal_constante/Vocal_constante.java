/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vocal_constante;
import java.util.Scanner;
/**
 *
 * @author clientepreferido
 */
public class Vocal_constante {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    String letra;
    Scanner teclado=new Scanner (System.in);
    System.out.println("Ingrese Una Letra en Mayuscula:");
    letra=teclado.next();
    if ("A".equals(letra) || "E".equals(letra) || "I".equals(letra) || "O".equals(letra) || "U".equals(letra)) {
        System.out.println("Es Vocal");    
    } else {
        System.out.println("Es Consonante");
    }    
    }
    
}
