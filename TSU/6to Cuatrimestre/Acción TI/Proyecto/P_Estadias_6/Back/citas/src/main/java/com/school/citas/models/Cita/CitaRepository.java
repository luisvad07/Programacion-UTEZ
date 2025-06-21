package com.school.citas.models.Cita;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    @Modifying
    @Query(
            value = "UPDATE cita SET atendida = :atendida WHERE id = :id",
            nativeQuery = true
    )
    int updateAtendidaById(
            @Param("atendida") Boolean atendida,
            @Param("id") Long id
    );

    @Override
    Optional<Cita> findById(Long aLong);
    List<Cita> findAllByAtendida(Boolean atendida);
    Cita getById(Long id);

    boolean existsByFechaAndHora(LocalDate fecha, LocalTime hora);
}
