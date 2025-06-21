/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sueldo_hr;
import java.util.Scanner;
/**
 *
 * @author clientepreferido
 */
public class Sueldo_hr {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    double horas, sueldo, pago, triples, dobles;
    Scanner teclado=new Scanner (System.in);
    System.out.printf("Ingrese las horas trabajadas:");
    horas=teclado.nextDouble();
    System.out.printf("Ingrese el pago por hora:");
    pago=teclado.nextDouble();
    if (horas > 48){
        triples = horas-48;
	sueldo = 40*pago+(8*pago*2)+(triples*pago*3);    
    } else {
        if (horas > 40) {
            dobles = horas-40;
	    sueldo = 40*pago+(dobles*pago*2);
        } else {
            sueldo = horas*pago;
        }
    }
    System.out.printf("El pago semanal de la persona es de: " + sueldo + " y las horas trabajadas fueron de " + horas);
    }
    
}
