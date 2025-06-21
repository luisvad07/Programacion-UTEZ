package utez.edu.mx.foodster.entities.direccionesusuario;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utez.edu.mx.foodster.entities.usuarios.Usuarios;

import java.util.List;

@Repository
public interface DireccionesUsuarioRepository extends JpaRepository<DireccionesUsuario, String> {
    List<DireccionesUsuario> findAllByUsuarios(Usuarios usuarios);

    Page<DireccionesUsuario> findAllByUsuarios(Usuarios usuarios, Pageable pageable);

    List<DireccionesUsuario> findAllByActiveOrderByUltimaModificacionDesc(Boolean active);

    DireccionesUsuario findByIdDireccionUsuarioAndActive(String idDireccionUsuario, Boolean active);

}
