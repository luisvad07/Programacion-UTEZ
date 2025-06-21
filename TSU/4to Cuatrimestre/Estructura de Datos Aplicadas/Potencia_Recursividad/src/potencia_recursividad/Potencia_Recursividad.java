/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package potencia_recursividad;

import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class Potencia_Recursividad {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner good = new Scanner(System.in);
        System.out.println("-----CALCULO DE POTENCIA-------");
        System.out.print("Ingrese un numero (base): ");
        int base = good.nextInt();
        System.out.print("Ingrese un numero para elevarlo (exponente): ");
        int exponente = good.nextInt();
        System.out.println("El resultado de la potencia es: " + potencia(base,exponente));
    }
    
    public static int potencia(int base, int exponente){ //Metodo Recursivo
        int result;
        if (exponente==0) { //Si el exponente es igual a 0, devuelve como resultado 1
            result = 1;
        } else if (exponente<0) { //Si el exponente es numero negativo, devuelve como resultado 0
            result = potencia(base, exponente+1) / base;
	} else { //Si el exponente es numero natural, devuelve el resultado de la potencia
            result = base * potencia(base, exponente-1);
	}
        return result;
    }
}
