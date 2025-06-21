package mx.edu.utez.servicioEscolar.models.ventanilla;

import mx.edu.utez.servicioEscolar.models.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VentanillaRepository extends JpaRepository<Ventanilla, Long> {
    @Modifying
    @Query(
            value = "UPDATE ventanillas SET status = :status WHERE id = :id",
            nativeQuery = true
    )
    int updateStatusById(
            @Param("status") Boolean status,
            @Param("id") Long id
    );

    @Override
    Optional<Ventanilla> findById(Long aLong);
    List<Ventanilla> findAllByStatus(Boolean status);
    boolean existsByCorreoVent(String correoVent);
    Ventanilla findByCorreoVent(String correoVent);
    Ventanilla getById(Long id);
}
