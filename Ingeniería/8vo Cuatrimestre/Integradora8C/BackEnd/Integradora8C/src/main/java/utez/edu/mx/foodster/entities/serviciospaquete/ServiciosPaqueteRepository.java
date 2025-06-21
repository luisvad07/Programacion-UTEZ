package utez.edu.mx.foodster.entities.serviciospaquete;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ServiciosPaqueteRepository extends JpaRepository<ServiciosPaquete, String> {
    List<ServiciosPaquete> findAllByActiveOrderByUltimaModificacionDesc(Boolean active);

    Page<ServiciosPaquete> findAllByActiveOrderByUltimaModificacionDesc(Boolean active, Pageable pageable);


    @Query(value = "SELECT * FROM servicios_paquete WHERE id_paquete = ?1 AND active = ?2", nativeQuery = true)
    List<ServiciosPaquete> findByIdPaqueteAndActive(String idPaquete, Boolean active);

    ServiciosPaquete findByIdServicioPaqueteAndActive(String idServicioPaquete, Boolean active);
}
