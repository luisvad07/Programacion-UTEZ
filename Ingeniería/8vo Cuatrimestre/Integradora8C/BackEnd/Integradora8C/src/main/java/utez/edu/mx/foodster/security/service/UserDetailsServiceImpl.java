package utez.edu.mx.foodster.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.foodster.entities.usuarios.Usuarios;
import utez.edu.mx.foodster.entities.usuarios.UsuariosRepository;
import utez.edu.mx.foodster.security.entity.UserDetailsImpl;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UsuariosRepository repository;

    @Autowired
    public UserDetailsServiceImpl(UsuariosRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuarios foundUser = this.repository.findByCorreoAndActive(username, true);
        if (foundUser != null) return UserDetailsImpl.build(foundUser);
        throw new UsernameNotFoundException("UserNotFound");
    }
}
