/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inicio_sesion;
import java.util.Scanner;
/**
 *
 * @author clientepreferido
 */
public class Inicio_Sesion {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    Scanner sc = new Scanner(System.in);
    String contrasena="salmon";
    final int ingreso = 3;
        boolean verdad=false; //Esto sirve para controlar que en caso de que acierte la condicion cambie
        String password;
        for (int a=0;a<ingreso && !verdad;a++){
            System.out.println("Ingresa la contraseña");
            password = sc.next();
            if (password.equals(contrasena)){
                System.out.println("Enhorabuena, Bienvenido");
                verdad=true;
            }
    }
    } 
}
