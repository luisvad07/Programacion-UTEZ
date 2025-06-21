/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.patronproxy;

/**
 *
 * @author CC6
 */
public class ProxyClient {
    //El sujeto que utilizará el cliente.
    private UserService userService;


    /**
     * Aquí estamos inyectando una dependencia de tipo UserService (la interfaz).
     *
     * Esto hace que para el cliente sea transparente si usa el sujeto real (BaseUserService) o el proxy
     * (UserServiceLogger).
     * */
    public ProxyClient(UserService userService) {
        this.userService = userService;
    }

    public void saveUser(String username) {

        //Ejecutar lógica de negocio antes de llamar el servicio

        userService.save(username);

        //Ejecutar lógica de negocio después de llamar el servicio
    }
}
