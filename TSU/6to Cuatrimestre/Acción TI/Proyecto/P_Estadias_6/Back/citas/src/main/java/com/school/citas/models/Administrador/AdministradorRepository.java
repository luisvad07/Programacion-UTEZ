package com.school.citas.models.Administrador;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long>{

    @Modifying
    @Query(
        value = "UPDATE administrador SET status = :status WHERE id = :id",
        nativeQuery = true
    )
    int updateStatusById(
            @Param("status") Boolean status,
            @Param("id") Long id
    );

    @Override
    Optional<Administrador> findById(Long aLong);

    boolean existsByCorreoAdmin(String correoAdmin);

    List<Administrador> findAllByStatus(Boolean status);
    ///finDA
    Administrador findByCorreoAdmin(String correoAdmin);
    Administrador getById(Long id);

    //query para cambiar la contraseña
    @Modifying
    @Query(value = "UPDATE administrador set pass =:newPass, change_password=1  WHERE  correo_admin=:id",
            nativeQuery = true
    )
    Integer changePassword(
            @Param("newPass") String newContrasena,
            @Param("id") String  id
    );

}