package utez.edu.mx.foodster.entities.personal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface PersonalRepository extends JpaRepository<Personal, String> {

    List<Personal> findAllByActiveOrderByUltimaModificacionDesc(Boolean active);

    Page<Personal> findAllByActiveOrderByUltimaModificacionDesc(Boolean active, Pageable pageable);


    Personal findByIdPersonalAndActive(String idPersonal, Boolean active);

    @Query(value = "SELECT * FROM personal WHERE id_usuario = ?1 AND active = ?2", nativeQuery = true)
    Personal findByIdUsuarioAndActive(String idUsuario, Boolean active);


    @Query(value = """
            SELECT * FROM personal 
            WHERE active=:active
            AND personal.id_categoria=:idCategoria
            AND id_personal NOT IN (
                SELECT DISTINCT personal.id_personal
                FROM personal
                INNER JOIN personal_evento ON personal.id_personal = personal_evento.id_personal
                INNER JOIN eventos ON personal_evento.id_evento = eventos.id_evento
                INNER JOIN categorias_personal ON personal.id_categoria = categorias_personal.id_categoria
                WHERE eventos.active=:active
                AND personal.active=:active
                AND personal_evento.active=:active
                AND personal.id_categoria=:idCategoria
                AND eventos.fecha_hora_inicio between :fechaHoraInicio AND :fechaHoraFin
                AND eventos.fecha_hora_fin between :fechaHoraInicio AND :fechaHoraFin
            ) 
            order by rand() LIMIT 1
            """, nativeQuery = true)
    Personal findRandomPersonalByCategoriaAndEventos(Timestamp fechaHoraInicio, Timestamp fechaHoraFin, String idCategoria, Boolean active);

    @Query(value = """
            SELECT COUNT(*) FROM personal 
            WHERE active=:active
            AND personal.id_categoria=:idCategoria
            AND id_personal NOT IN (
                SELECT DISTINCT personal.id_personal
                FROM personal
                INNER JOIN personal_evento ON personal.id_personal = personal_evento.id_personal
                INNER JOIN eventos ON personal_evento.id_evento = eventos.id_evento
                INNER JOIN categorias_personal ON personal.id_categoria = categorias_personal.id_categoria
                WHERE eventos.active=:active
                AND personal.active=:active
                AND personal_evento.active=:active
                AND personal.id_categoria=:idCategoria
                AND eventos.fecha_hora_inicio between :fechaHoraInicio AND :fechaHoraFin
                AND eventos.fecha_hora_fin between :fechaHoraInicio AND :fechaHoraFin
            ) 
            """, nativeQuery = true)
    Long countRandomPersonalByCategoriaAndEventos(Timestamp fechaHoraInicio, Timestamp fechaHoraFin, String idCategoria, Boolean active);


    @Query(value = """
            SELECT * FROM personal 
            WHERE active=:active
            AND id_personal NOT IN (
                SELECT DISTINCT personal.id_personal
                FROM personal
                INNER JOIN personal_evento ON personal.id_personal = personal_evento.id_personal
                INNER JOIN eventos ON personal_evento.id_evento = eventos.id_evento
                WHERE eventos.active=:active
                AND personal.active=:active
                AND personal_evento.active=:active
                AND eventos.fecha_hora_inicio between :fechaHoraInicio AND :fechaHoraFin
                AND eventos.fecha_hora_fin between :fechaHoraInicio AND :fechaHoraFin
            ) 
            order by ultima_modificacion desc
            """, nativeQuery = true)
    List<Personal> findPersonalDisponible(Timestamp fechaHoraInicio, Timestamp fechaHoraFin, Boolean active);

}
