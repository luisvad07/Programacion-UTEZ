/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.patronproxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author CC6
 */
public class UserServiceLogger implements UserService {

    //La instancia del sujeto que este proxy accederá.
    private UserService userService;
    private Logger logger;

    /**
     * Para que este proxy funcione con varios tipos sujetos, el tipo a usar debe ser el de la interfaz (UserService).
     *
     * Además, estamos inyectando la dependencia para que sea alguien más quien decida cuál el sujeto a usar.
     * */
    public UserServiceLogger(UserService userService) {
        this.userService = userService;
        logger = LoggerFactory.getLogger(UserServiceLogger.class);
    }


    /**
     * Sobreescribimos la operación para poder controlar el acceso al recurso original
     *
     * En esta caso queremos sobreescribir la operación, para poder llevar logs.
     * */
    @Override
    public boolean save(String username) {
        logger.info("Antes de guardar el usuario " + username);
        //En algún punto, si no hay problemas, el proxy debe llamar al sujeto real.
        boolean result = userService.save(username);
        String message = result ? "Se guardo el usuario " 
        + username: "No se pudo guardar el usuario " + username;
        logger.info(message);

        return result;
    }
}
