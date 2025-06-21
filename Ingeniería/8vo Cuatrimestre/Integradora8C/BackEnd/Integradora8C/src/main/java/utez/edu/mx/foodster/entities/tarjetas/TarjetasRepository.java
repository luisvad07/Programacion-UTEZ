package utez.edu.mx.foodster.entities.tarjetas;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarjetasRepository extends JpaRepository<Tarjetas, String> {
    List<Tarjetas> findAllByActiveOrderByUltimaModificacionDesc(Boolean active);

    Page<Tarjetas> findAllByActiveOrderByUltimaModificacionDesc(Boolean active, Pageable pageable);

    @Query(value = "SELECT * FROM tarjetas WHERE id_usuario = ?1 AND active = ?2", nativeQuery = true)
    List<Tarjetas> findByIdUsuarioAndActive(String idUsuario, Boolean active);

    Tarjetas findByIdTarjetaAndActive(String idTarjeta, Boolean active);
}
