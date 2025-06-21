package com.imagen_social.mac_morelos_api.security.service;

import com.imagen_social.mac_morelos_api.models.users.User;
import com.imagen_social.mac_morelos_api.models.users.UserRepository;
import com.imagen_social.mac_morelos_api.security.entity.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        
        // Busca al usuario por nombre de usuario
        User foundUser = null;

        // Verificar si el identificador es un email o un username
        if (identifier.contains("@")) {
            // Es un email, entonces solo buscamos en los usuarios que no son ciudadanos
            foundUser = repository.findByEmailAndStatus(identifier, true);
            if (foundUser != null && foundUser.getRole().getName().name().equalsIgnoreCase("CIUDADANO")) {
                throw new UsernameNotFoundException("Los ciudadanos deben iniciar sesión con su nombre de usuario.");
            }
        } else {
            // Es un username, entonces buscamos solo en ciudadanos
            foundUser = repository.findByUsernameAndStatus(identifier, true);
            if (foundUser == null) {
                throw new UsernameNotFoundException("Usuario no encontrado o inactivo.");
            }
        }
        return UserDetailsImpl.build(foundUser);
    }
}

