/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package array_caracteres;
import java.util.Scanner;
/**
 *
 * @author clientepreferido
 */
public class Array_Caracteres {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    Scanner teclado=new Scanner(System.in);
    int w=0; 
    System.out.println("Ingrese una palabra por favor:");
    String palabra=teclado.nextLine();
    char caracteres[]=new char[palabra.length()];
    for(int s=0;s<palabra.length();s++){            
        w=w+1;             
        caracteres[s]=palabra.charAt(s);             
        System.out.println(caracteres[s]);           
    } 
    System.out.println("Esta palabra esta conformada por: "+ w + " caracteres");
  } 
}    

