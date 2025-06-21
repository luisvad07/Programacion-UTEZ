/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.patronproxy;

/**
 *
 * @author CC6
 */
public class PatronProxy {

    public static void main(String[] args) {
        
        //El objeto que tendrá la operación que queremos ejecutar.
        UserService userService = new BaseUserService();
        //El objeto proxy.
        UserService userProxy = new UserServiceLogger(userService);

        //Ahora ejecutamos el cliente, y le pasamos el sujeto que debe utilizar.
        ProxyClient client = new ProxyClient(userProxy);
        client.saveUser("LuisVad");
    }
}
