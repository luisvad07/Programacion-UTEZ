package utez.edu.mx.foodster.entities.usuarios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, String> {


    /*
        @Query(value = "SELECT id_usuario, active, '' as contrasena, correo, nombres, primer_apellido, segundo_apellido, telefono, ultima_modificacion FROM usuarios WHERE active = ?1 ORDER BY ultima_modificacion DESC", nativeQuery = true)
    */
    List<Usuarios> findAllByActiveOrderByUltimaModificacionDesc(Boolean active);

    Page<Usuarios> findAllByActiveOrderByUltimaModificacionDesc(Boolean active, Pageable pageable);

    Usuarios findByCorreoAndActive(String correo, Boolean active);

    Optional<Usuarios> findByCorreo(String correo);

    /*
        @Query(value = "SELECT id_usuario, active, '' as contrasena, correo, nombres, primer_apellido, segundo_apellido, telefono, ultima_modificacion FROM usuarios WHERE id_usuario = ?1 AND active = ?2", nativeQuery = true)
    */
    Usuarios findByIdUsuarioAndActive(String idUsuario, Boolean active);

    @Modifying
    @Query(value = "INSERT INTO usuarios_roles (id_rol,id_usuario) VALUES (:roleId, :userId);", nativeQuery = true)
    int saveUserRole(String userId, String roleId);


    @Modifying
    @Query(value = "DELETE FROM usuarios_roles WHERE id_usuario = :userId", nativeQuery = true)
    int deleteUserRoles(String userId);


}