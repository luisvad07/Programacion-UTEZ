/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package promedio_recursividad;

import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class Promedio_Recursividad {
    
    static Scanner teclado = new Scanner(System.in);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int n;
        System.out.println("----PROMEDIO DE N MATERIAS-----");
        System.out.print("Ingrese el numero de materias para calcular el numero: ");
        n=teclado.nextInt();   
        
        System.out.println("El Promedio es:" +promedio(n)); 
    }
    
    public static double promedio(int n){ //Metodo Recursivo
        int matt;
        int suma = 0;
        double result = 0;
        try {
            for(int i=1;i<=n;i++) {
                System.out.print("Materia ["+i+"]= ");
                matt=teclado.nextInt();
                suma= suma + matt; 
            }
            result = suma/n;
        } catch (Exception e) {
            System.out.println("Error al Promediar!");
        }
        return result;
    }
    
}
