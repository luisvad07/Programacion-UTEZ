package mx.edu.utez.servicioEscolar.models.cita;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    @Modifying
    @Query(
            value = "UPDATE citas SET status = :status WHERE id = :id",
            nativeQuery = true
    )
    int updateStatusById(
            @Param("status") Boolean status,
            @Param("id") Long id
    );

    @Override
    Optional<Cita> findById(Long aLong);
    List<Cita> findAllByStatus(Boolean status);
    Cita getById(Long id);

}
