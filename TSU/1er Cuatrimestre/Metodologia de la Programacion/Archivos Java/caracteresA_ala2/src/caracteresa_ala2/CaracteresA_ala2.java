/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caracteresa_ala2;
import java.util.Scanner;
/**
 *
 * @author clientepreferido
 */
public class CaracteresA_ala2 {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    Scanner teclado = new Scanner(System.in);
        char mayus[] = new char[26];
        for (int i = 65, j = 0; i <= 90; i++, j++) {
            mayus[j] = (char) i;
        }
        String letra = "";
        int e;
        e = -1;
        do {
            System.out.println("Inserte un numero entre 0 y 25");
            e = teclado.nextInt();
            if (!(e >= 0 && e <= mayus.length - 1)) {
                System.out.println("Error! Ingresa otro numero");
            } else {
                if (e != -1) {
                    letra += mayus[e];
                }
            }
        } while (e != -1);
        System.out.println(letra);
    }
}
