/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nummayor_menorarray;
import java.util.Scanner;
/**
 *
 * @author clientepreferido
 */
public class NumMayor_MenorArray {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    Scanner teclado = new Scanner(System.in);
    int mayior = 0, menior = 0;
    int[] nums = new int[11]; 
    for (int i = 0+1; i < 11; i++) {
        System.out.print("Ingresa un número cualquiera a evaluar, posicion #" + (i) + ":"); 
        nums[i] = teclado.nextInt(); 
    } 
    for (int i = 1; i < nums.length; i++) {
        if (mayior < nums[i]) { 
            mayior = nums[i];
        } 
    } 
    menior = mayior; 
    for (int i = 1; i < nums.length; i++) {
        if (menior > nums[i]) {
            menior = nums[i]; 
        } 
    }
    System.out.println("El numero mayor es " + mayior);
    System.out.println("El numero menor es " + menior); 
    } 
}
