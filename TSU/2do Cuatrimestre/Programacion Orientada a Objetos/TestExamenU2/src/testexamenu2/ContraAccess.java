/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testexamenu2;

/**
 *
 * @author CC10
 */
public class ContraAccess {

    private final String PASSWORD = "123";

    boolean verificar(String tecladoUsuario) {
        boolean key = PASSWORD.equals(tecladoUsuario);
        return key;
    }
}
