/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.util.Scanner;

/**
 *
 * @author clientepreferido
 */
public class Login {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner teclado = new Scanner(System.in);
        teclado.useDelimiter("\n");
        validarContra access = new validarContra();

        String passUsuario = null;
        boolean entrar = false; // Variable para acceder al sistema

        for (int i = 0; i < 3 && !entrar; i++) {
            if (i == 0) {
                System.out.print("Introduce la contraseña: ");
            } else if (i == 1) {
                System.out.print("Te quedan dos intentos: ");
            } else if (i == 2) {
                System.out.print("Último intento: ");
            }

            passUsuario = teclado.next();

            entrar= access.key(passUsuario);
            if (entrar) {
                System.out.println("CONTRASEÑA CORRECTA");
                break;               
            } else {
                System.out.println("CONTRASEÑA INCORRECTA");
            }
        }
    }
}
