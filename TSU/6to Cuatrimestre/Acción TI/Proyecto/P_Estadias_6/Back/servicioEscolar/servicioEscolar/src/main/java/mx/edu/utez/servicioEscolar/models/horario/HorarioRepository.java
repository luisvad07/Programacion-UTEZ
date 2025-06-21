package mx.edu.utez.servicioEscolar.models.horario;

import mx.edu.utez.servicioEscolar.models.cita.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {

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
    Optional<Horario> findById(Long aLong);
    List<Horario> findAllByStatus(Boolean status);
    Horario getById(Long id);

}
