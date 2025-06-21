/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package num_suerte;
import java.util.Scanner;
/**
 *
 * @author clientepreferido
 */
public class Num_suerte {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    Scanner sc = new Scanner(System.in);
        int jour, mois, année, chance, sum, a, b, c, d;  
        //jour, mois, année y chance son palabras francesas de dia, mes, año y suerte                                         
        System.out.println("Ingrese su fecha de nacimiento");
        System.out.print("Día: ");
        jour = sc.nextInt();
        System.out.print("Mes: ");
        mois = sc.nextInt();
        System.out.print("Año: ");
        année = sc.nextInt();
        sum = jour + mois + année;
        a = sum/1000;      //Se obtiene la primera cifra
        b = sum/100%10;    //Se obtiene la segunda cifra
        c = sum/10%10;     //Se obtiene la tercera cifra
        d = sum%10;        //Se obtiene la última cifra
        chance = a + b + c + d;
        System.out.println("Su número de la suerte es: " + chance);
    }
}
