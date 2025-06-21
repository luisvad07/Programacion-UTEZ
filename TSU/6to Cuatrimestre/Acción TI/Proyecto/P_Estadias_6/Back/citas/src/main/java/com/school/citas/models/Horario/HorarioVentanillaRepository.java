package com.school.citas.models.Horario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HorarioVentanillaRepository extends JpaRepository<HorarioVentanilla, Long> {
    @Modifying
    @Query(
            value = "UPDATE horarios SET status = :status WHERE id = :id",
            nativeQuery = true
    )
    int updateStatusById(
            @Param("status") Boolean status,
            @Param("id") Long id
    );

    @Override
    Optional<HorarioVentanilla> findById(Long aLong);
    List<HorarioVentanilla> findAllByStatus(Boolean status);
    HorarioVentanilla getById(Long id);
}
