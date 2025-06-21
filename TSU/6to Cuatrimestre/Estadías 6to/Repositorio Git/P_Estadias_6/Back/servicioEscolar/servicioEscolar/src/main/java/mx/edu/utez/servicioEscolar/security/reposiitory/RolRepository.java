package mx.edu.utez.servicioEscolar.security.reposiitory;

import mx.edu.utez.servicioEscolar.security.entity.Rol;
import mx.edu.utez.servicioEscolar.security.enums.RolNombre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByRolNombre(RolNombre rolNombre);
}
