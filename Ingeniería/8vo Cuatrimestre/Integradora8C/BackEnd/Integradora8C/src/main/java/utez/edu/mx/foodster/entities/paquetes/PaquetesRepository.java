package utez.edu.mx.foodster.entities.paquetes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaquetesRepository extends JpaRepository<Paquetes, String> {
    List<Paquetes> findAllByActiveOrderByUltimaModificacionDesc(Boolean active);

    Page<Paquetes> findAllByActiveOrderByUltimaModificacionDesc(Boolean active, Pageable pageable);

    Paquetes findByIdPaqueteAndActive(String idPaquete, Boolean active);
}
