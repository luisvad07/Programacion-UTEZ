/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cruz;
import java.util.Scanner;
/**
 *
 * @author clientepreferido
 */
public class Cruz {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    Scanner teclado=new Scanner(System.in);    
    boolean numcheck = false;
        int a,b;
        do {
            System.out.print("Ingrese cantidad de filas (numero impar): ");
            a = teclado.nextInt();
            System.out.print("Ingrese cantidad de columnas (numero impar): ");
            b = teclado.nextInt();
           
            if(a % 2 != 0 && b % 2 != 0) {
                numcheck = true;
            }else {
                System.out.println("Los datos incorrectos. Vuelva a ingresar dos valores impares");
            }
            System.out.println();
        }while(!numcheck);
        char cruz [][]= new char [a][b];
        int mitadFilas = cruz.length / 2;
        int mitadColumnas = cruz[0].length / 2;
        System.out.println("Mitad de las Filas: "+mitadFilas);
        System.out.println("Mitad de las Columnas: "+mitadColumnas);
       
        for (int i = 0; i < cruz.length; i++) {
            for (int j = 0; j < cruz[0].length; j++) {
                if(i == mitadFilas || j == mitadColumnas) {
                    cruz [i][j] = 'E';
                }else {
                    cruz [i][j] = ' ';
                }
                System.out.print(cruz[i][j]+" ");
            }
            System.out.println(); 
        }
    }
}
