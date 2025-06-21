/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package num_romanos;
import java.util.Scanner;
/**
 *
 * @author clientepreferido
 */
public class Num_romanos {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here    
    int num;
    Scanner teclado=new Scanner (System.in);
    System.out.println("Ingrese un numero del uno al diez");
    num=teclado.nextInt();
    switch (num){
        case 1: //Num 1
        System.out.println ("El numero "+ num + " en romano es I");
        break;
        case 2 : //Num 2
        System.out.println ("El numero "+ num + " en romano es II");
        break;    
        case 3: //Num 3
        System.out.println ("El numero "+ num + " en romano es III"); 
        break;
        case 4: //Num 4
        System.out.println ("El numero "+ num + " en romano es IV"); 
        break;    
        case 5: //Num 5
        System.out.println ("El numero "+ num + " en romano es V");
        break;
        case 6: //Num 6
        System.out.println ("El numero "+ num + " en romano es VI");
        break;    
        case 7: //Num 7
        System.out.println ("El numero "+ num + " en romano es VII");
        break;
        case 8: //Num 8
        System.out.println ("El numero "+ num + " en romano es VIII");
        break;    
        case 9: //Num 9
        System.out.println ("El numero "+ num + " en romano es IX");
        break;
        case 10: //Num 10
        System.out.println ("El numero "+ num + " en romano es X");
        break;    
        default: //Otra opcion
        System.out.printf("El numero no se pudo convertir");
        break;
    }    
    }    
}
    
