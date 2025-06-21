package mx.edu.utez.servicioEscolar.security.service;

import mx.edu.utez.servicioEscolar.security.entity.Rol;
import mx.edu.utez.servicioEscolar.security.enums.RolNombre;
import mx.edu.utez.servicioEscolar.security.reposiitory.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RolService {

    @Autowired
    RolRepository rolRepository;

    public Optional<Rol> getByRolNombre(RolNombre rolNombre){
        return rolRepository.findByRolNombre(rolNombre);
    }

    public void save(Rol rol){
        rolRepository.save(rol);
    }
}
