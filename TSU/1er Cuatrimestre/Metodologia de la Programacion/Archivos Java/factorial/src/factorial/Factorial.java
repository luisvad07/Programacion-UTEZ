/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factorial;
import java.util.Scanner;
/**
 *
 * @author clientepreferido
 */
public class Factorial {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    int num, fact = 1;
      Scanner teclado = new Scanner(System.in);
      System.out.println( "Ingrese un numero para calcular su factorial:" );
      num=teclado.nextInt();
      for( int j = 1; j <= num; j++ ) {
         fact *= j;
      }
      System.out.println( "El factorial de " + num + " es: " + fact );      
   }
}
