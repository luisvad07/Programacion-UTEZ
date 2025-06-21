/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;

/**
 *
 * @author CC10
 */
public class validarContra {

    private boolean llave;
    private final String pass = "String123";

    boolean key(String password) {
        llave = pass.equals(password);
        return llave;
    }
}
