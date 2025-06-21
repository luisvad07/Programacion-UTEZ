package com.school.citas.models.Servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {
    @Modifying
    @Query(
            value = "UPDATE servicio SET status = :status WHERE id = :id",
            nativeQuery = true
    )
    int updateStatusById(
            @Param("status") Boolean status,
            @Param("id") Long id
    );
    @Override
    Optional<Servicio> findById(Long aLong);
    List<Servicio> findAllByStatus(Boolean status);
    Servicio getById(Long id);
}
