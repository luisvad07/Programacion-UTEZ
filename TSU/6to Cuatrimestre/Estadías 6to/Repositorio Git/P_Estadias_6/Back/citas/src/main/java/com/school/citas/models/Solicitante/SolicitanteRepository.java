package com.school.citas.models.Solicitante;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitanteRepository extends JpaRepository<Solicitante, Long> {
    @Modifying @Query(
            value = "UPDATE solicitante SET status = :status WHERE id = :id",
            nativeQuery = true
    )
    int updateStatusById(
            @Param("status") Boolean status,
            @Param("id") Long id
    );
    @Override
    Optional<Solicitante> findById(Long aLong);
    List<Solicitante> findAllByStatus(Boolean status);
    boolean existsByCorreoElectronico(String correoElectronico);
    Solicitante findByCorreoElectronico(String correoElectronico);
    Solicitante getById(Long id);
    //query para cambiar la contraseña
    @Modifying
    @Query(value = "UPDATE solicitante set pass =:newPass, changePassword=1  where  correoElectronico=:id",
            nativeQuery = true
    )
    Integer changePassword(
            @Param("newPass") String newContrasena,
            @Param("id") String  id
    );
}
