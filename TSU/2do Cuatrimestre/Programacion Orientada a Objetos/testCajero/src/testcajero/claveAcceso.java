/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testcajero;

/**
 *
 * @author eduardoA
 */
public class claveAcceso {

    private final String PASSWORD = "8888";

    boolean verificarContraseña(String passwordUser) {
        boolean ver = PASSWORD.equals(passwordUser);
        return ver;
    }

}
