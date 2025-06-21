package mx.edu.utez.servicioEscolar.security.service;

import mx.edu.utez.servicioEscolar.security.entity.User;
import mx.edu.utez.servicioEscolar.security.reposiitory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository usuarioRepository;

    public Optional<User> getByNombreUsuario(String nombreUsuario){
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }

    public boolean existsByNombreUsuario(String nombreUsuario){
        return usuarioRepository.existsByNombreUsuario(nombreUsuario);
    }

    public boolean existsByEmail(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public void save(User usuario){
        usuarioRepository.save(usuario);
    }
}
