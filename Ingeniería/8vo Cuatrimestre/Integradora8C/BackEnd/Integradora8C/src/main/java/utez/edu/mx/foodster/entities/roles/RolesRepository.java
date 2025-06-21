package utez.edu.mx.foodster.entities.roles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolesRepository extends JpaRepository<Roles, String> {
    List<Roles> findAllByActiveOrderByUltimaModificacionDesc(Boolean active);


    Roles findByNombreAndActive(String nombre, Boolean active);

    Roles findByIdRolAndActive(String idRol, Boolean active);


}
