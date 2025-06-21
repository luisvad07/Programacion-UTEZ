/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examenrecupera;

/**
 *
 * @author clientepreferido
 */
public class ValidarContra {
    private final String PASSWORD = "123"; //BahenaCastilloLuisEduardo

    boolean Acceso(String teclado) {
        boolean llave = PASSWORD.equals(teclado);
        return llave;
    }
}
