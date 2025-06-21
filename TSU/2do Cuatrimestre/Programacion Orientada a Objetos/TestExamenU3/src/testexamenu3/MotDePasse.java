/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testexamenu3;

/**
 *
 * @author clientepreferido
 */
public class MotDePasse {

    private final String PASSWORD = "123";

    boolean cocher(String tecladoUsuario) {
        boolean key = PASSWORD.equals(tecladoUsuario);
        return key;
    }
}
