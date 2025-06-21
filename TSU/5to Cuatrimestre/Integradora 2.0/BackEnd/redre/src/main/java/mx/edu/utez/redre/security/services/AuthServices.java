/*package mx.edu.utez.redre.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.redre.security.model.UsuarioAuth;



@Service
@Transactional
public class AuthServices implements UserDetailsService{
    @Autowired
    UserServices services;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return UsuarioAuth.build(services.getUserByCorreo(username));
    }
}*/
