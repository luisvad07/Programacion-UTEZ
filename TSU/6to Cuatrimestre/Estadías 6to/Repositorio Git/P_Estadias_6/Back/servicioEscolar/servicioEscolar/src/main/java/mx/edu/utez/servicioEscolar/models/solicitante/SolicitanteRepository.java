package mx.edu.utez.servicioEscolar.models.solicitante;

import mx.edu.utez.servicioEscolar.models.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
//Modelo al que hago referencia
public interface SolicitanteRepository  extends JpaRepository<Solicitante, Long> {

    @Modifying
    @Query(
            value = "UPDATE solicitantes SET status = :status WHERE id = :id",
            nativeQuery = true
    )
    int updateStatusById(
            @Param("status") Boolean status,
            @Param("id") Long id
    );

    @Override
    Optional<Solicitante> findById(Long aLong);
    List<Solicitante> findAllByStatus(Boolean status);
    boolean existsByCorreoSoli(String correoSoli);
    Solicitante findByCorreoSoli(String correoSoli);
    Solicitante getById(Long id);


}
