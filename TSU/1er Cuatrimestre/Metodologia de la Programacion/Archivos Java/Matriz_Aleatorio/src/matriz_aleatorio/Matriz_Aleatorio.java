/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matriz_aleatorio;
import java.util.Scanner;
/**
 *
 * @author CC-6
 */
public class Matriz_Aleatorio {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    int longt;
    int matriz[][];
    int num;
    int flag;
    Scanner teclado=new Scanner(System.in);    
    System.out.println("Ingresa el tamaño de la matriz");
    longt = teclado.nextInt();
    matriz =new int[longt][longt]; 
    for (int i=0;i<matriz.length;i++){
        for(int j=0; j<matriz.length; j++) {
            flag = 0;
            num = (int) (Math.random() * longt * longt + 1);
            for(int k=0;k<matriz.length; k++) {
                for(int l=0;l<matriz.length; l++) {
                    if (num == matriz[k][l]) {
                        flag++;
                    }
                }
            }
            if (flag == 0) {
                matriz[i][j]= num;
            } else {
                j--;
            }
        }
    }
    System.out.println("Matriz de " + longt + "X"+longt+" de numeros aleatorios sin repetirse");
    String impress="";
    for(int i=0;i<matriz.length; i++) {
        for(int j=0;j<matriz[i].length; j++) {
            impress=impress+" | "+matriz[i][j];
        }
        impress=impress+"|\n"; 
    }
    System.out.println(impress);
  }
}
    
    
