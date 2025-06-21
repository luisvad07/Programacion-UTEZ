/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menor.mayor;
import java.util.Scanner;
/**
 *
 * @author clientepreferido
 */
public class MenorMayor {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    Scanner sc = new Scanner(System.in);
        int a, b, menior, mayior;
        do { //Ingreso de los numeros
            System.out.print("Ingresa el primer numero:");
            a = sc.nextInt();
            System.out.print("Ingresa el Segundo numero:");                   
            b = sc.nextInt();
            if(a == b){
                System.out.println("Numeros iguales, por favor intentalo de nuevo");
            }
        } while (a == b);
        if (a > b) { //Calculo de los numeros de mayor o menor
            mayior = a;
            menior = b;
        } else {
            mayior = b;
            menior = a;
        }
        System.out.println("Estos son los números desde el menor: " + menior + " hasta el mayor: " + mayior + " : ");
        for (int x = menior; x <= mayior; x++) {
            System.out.println(x);
        }
    }
}
