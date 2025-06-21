package utez.edu.mx.foodster.entities.eventos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventosRepository extends JpaRepository<Eventos, String> {
    List<Eventos> findAllByActiveOrderByUltimaModificacionDesc(Boolean active);

    Page<Eventos> findAllByActiveOrderByUltimaModificacionDesc(Boolean active, Pageable pageable);

    @Query(value = "SELECT * FROM eventos WHERE id_usuario = ?1 AND active = ?2", nativeQuery = true)
    List<Eventos> findAllByIdUsuarioAndActive(String idUsuario, Boolean active);

    @Query(value = """
            SELECT * FROM eventos 
            WHERE active = :active
            AND id_evento IN (
                SELECT id_evento FROM personal_evento 
                INNER JOIN personal ON personal.id_personal = personal_evento.id_personal 
                WHERE personal.id_usuario = :idUsuario AND personal_evento.active = :active AND personal.active = :active
            )
            """, nativeQuery = true)
    List<Eventos> findAllByPersonalIdUsuarioAndActive(String idUsuario, Boolean active);

    Eventos findByIdEventoAndActive(String idEvento, Boolean active);
}
