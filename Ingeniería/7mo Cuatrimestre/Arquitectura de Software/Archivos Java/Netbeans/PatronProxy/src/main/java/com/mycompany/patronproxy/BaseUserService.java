/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.patronproxy;

import java.util.ArrayList;

/**
 *
 * @author CC6
 */
public class BaseUserService implements UserService {

    private ArrayList<String> users;

    public BaseUserService() {
        users = new ArrayList<>();
    }


    /**
     * Aquí ya se implementa la funcionalidad a controlar.
     * **/
    @Override
    public boolean save(String username) {

        boolean result = false;
        if(!users.contains(username)) {
            users.add(username);
            result = true;
        }
        return result;

    }
}
