/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package testcajero;

import java.util.Scanner;

/**
 *
 * @author eduardoA
 */
public class TestCajero {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner leer = new Scanner(System.in);
        leer.useDelimiter("\n");

        claveAcceso ev = new claveAcceso();

        String passwordUser;
        boolean ver;
        int opcion;

        funcionesCajero cajero = new funcionesCajero();

        double cantidadRetirar;
        double cantidadDepositar;
        double retirar;
        double depositar;

        System.out.println("--------- Bienvenido al cajero automático de BANAMEX ---------");
        System.out.print("Ingresa la contraseña/ el PIN: ");
        passwordUser = leer.next();

        ver = ev.verificarContraseña(passwordUser);

        if (ver) {
            do {
                System.out.println("==============================================");
                System.out.println("                  BIENVENIDO");
                System.out.println("==============================================");
                System.out.println("MENU");
                System.out.println("1. Retirar");
                System.out.println("2. Depositar");
                System.out.println("3. Imprimir saldo");
                System.out.println("4. Salir");
                System.out.println("Selecciona la opcion a realizar: ");
                opcion = leer.nextInt();

                switch (opcion) {
                    case 1:
                        System.out.println("Cantidad a retirar: ");
                        cantidadRetirar = leer.nextDouble();
                        if (cantidadRetirar > cajero.getSaldo()) {
                            cantidadRetirar = 0;
                            System.out.println("Saldo Insuficiente!");
                        } else {
                            retirar = cajero.retirar(cantidadRetirar);
                            System.out.println("Saldo restante: " + retirar);
                        }
                        break;
                    case 2:
                        System.out.println("Cantidad a depositar: ");
                        cantidadDepositar = leer.nextDouble();
                        depositar = cajero.depositar(cantidadDepositar);
                        System.out.println("ACTUALIZACION DE SALDO");
                        System.out.println("Saldo : " + depositar);
                        break;
                    case 3:
                        System.out.println("Saldo disponible: " + cajero.getSaldo());
                        break;
                    case 4:
                        System.out.println("Operacion realizada, hasta luego!");
                        break;
                    default:
                        System.out.println("ERROR... elija una opción válida");
                        break;
                }
            } while (opcion != 4);
        } else {
            System.out.println("Contraseña Incorrecta!, Se ha bloqueado tu tarjeta!");
        }
    }

}
