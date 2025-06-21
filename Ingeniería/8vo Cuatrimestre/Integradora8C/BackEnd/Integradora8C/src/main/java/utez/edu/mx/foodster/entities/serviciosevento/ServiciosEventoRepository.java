package utez.edu.mx.foodster.entities.serviciosevento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServiciosEventoRepository extends JpaRepository<ServiciosEvento, String> {
    List<ServiciosEvento> findAllByActiveOrderByUltimaModificacionDesc(Boolean active);

    Page<ServiciosEvento> findAllByActiveOrderByUltimaModificacionDesc(Boolean active, Pageable pageable);

    ServiciosEvento findByIdServicioEventoAndActive(String idServicioEvento, Boolean active);

    @Query(value = "SELECT * FROM servicios_evento se WHERE se.id_evento = ?1 AND se.active = ?2 ORDER BY se.ultima_modificacion DESC", nativeQuery = true)
    List<ServiciosEvento> findAllByEventoAndActiveOrderByUltimaModificacionDesc(String idEvento, Boolean active);

    // obtendremos el costo total calculando precio * cantidad y si existe un precioDescuento mayor a 0 se tomara en cuenta ese precio
    @Query(value = """
            SELECT SUM(IF(s.precio_descuento > 0, s.precio_descuento, s.precio) * se.cantidad * e.numero_personas)
            FROM servicios_evento se
            INNER JOIN servicios s ON se.id_servicio = s.id_servicio
            INNER JOIN eventos e ON se.id_evento = e.id_evento
            WHERE se.id_evento = :idEvento AND se.active = :active
            """, nativeQuery = true)
    Double costoTotal(String idEvento, Boolean active);
}
