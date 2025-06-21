package com.school.citas.models.Ventanilla;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VentanillaRepository extends JpaRepository<Ventanilla, Long> {

    @Modifying
    @Query(
            value = "UPDATE ventanilla SET status = :status WHERE id = :id",
            nativeQuery = true
    )
    int updateStatusById(
            @Param("status") Boolean status,
            @Param("id") Long id
    );

    @Override
    Optional<Ventanilla> findById(Long aLong);
    List<Ventanilla> findAllByStatus(Boolean status);
    boolean existsByCorreoElectronico(String correoElectronico);
    Ventanilla findByCorreoElectronico(String correoElectronico);
    Ventanilla getById(Long id);

    //query para cambiar la contraseña
    @Modifying
    @Query(value = "UPDATE ventanilla set pass =:newPass, change_password=1  where  correo_electronico=:id",
            nativeQuery = true
    )
    Integer changePassword(
            @Param("newPass") String newContrasena,
            @Param("id") String  id
    );
}