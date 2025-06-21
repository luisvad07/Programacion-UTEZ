/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejem_recursividad;

/**
 *
 * @author Luis
 */
public class Ejem_Recursividad {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        factorial(10);
    }
    
    public static int factorial(int n) {
        System.out.println("El valor de n es: " + n);
        int result;
        //Condicion de Salida
        if (n==0) {
            return 1;
        }
        result = factorial(n-1)*n;
        System.out.println("El resultado es: " + result);
        return result;
    }
    
}
