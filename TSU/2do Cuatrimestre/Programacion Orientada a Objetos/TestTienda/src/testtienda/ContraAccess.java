/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testtienda;

/**
 *
 * @author clientepreferido
 */
public class ContraAccess {

    private final String PASS = "123";

    boolean verificarContra(String pass) {
        boolean key = PASS.equals(pass);
        return key;

    }
}
