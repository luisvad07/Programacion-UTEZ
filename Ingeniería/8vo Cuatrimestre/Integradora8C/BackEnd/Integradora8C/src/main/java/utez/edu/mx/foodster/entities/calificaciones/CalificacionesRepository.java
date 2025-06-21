package utez.edu.mx.foodster.entities.calificaciones;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalificacionesRepository extends JpaRepository<Calificaciones, String> {
    List<Calificaciones> findAllByActiveOrderByUltimaModificacionDesc(Boolean active);

    Page<Calificaciones> findAllByActiveOrderByUltimaModificacionDesc(Boolean active, Pageable pageable);

    Calificaciones findByIdCalificacionAndActive(String idCalificacion, Boolean active);

    @Query(value = "SELECT * FROM calificaciones WHERE id_servicio = ?1 AND active = ?2 ORDER BY ultima_modificacion DESC", nativeQuery = true)
    List<Calificaciones> findAllByServiciosAndActiveOrderByUltimaModificacionDesc(String idServicio, Boolean active);

    @Query(value = "SELECT * FROM calificaciones WHERE id_servicio = ?1 AND active = ?2 ORDER BY ultima_modificacion DESC", nativeQuery = true)
    Page<Calificaciones> findAllByServiciosAndActiveOrderByUltimaModificacionDesc(String idServicio, Boolean active, Pageable pageable);

    @Query(value = "SELECT * FROM calificaciones WHERE id_usuario = ?1 AND active = ?2 ORDER BY ultima_modificacion DESC", nativeQuery = true)
    List<Calificaciones> findAllByUsuariosAndActiveOrderByUltimaModificacionDesc(String idUsuario, Boolean active);

    @Query(value = "SELECT * FROM calificaciones WHERE id_usuario = ?1 AND active = ?2 ORDER BY ultima_modificacion DESC", nativeQuery = true)
    Page<Calificaciones> findAllByUsuariosAndActiveOrderByUltimaModificacionDesc(String idUsuario, Boolean active, Pageable pageable);

    @Query(value = "SELECT * FROM calificaciones WHERE id_paquete = ?1 AND active = ?2 ORDER BY ultima_modificacion DESC", nativeQuery = true)
    List<Calificaciones> findAllByPaquetesAndActiveOrderByUltimaModificacionDesc(String idPaquete, Boolean active);

    @Query(value = "SELECT * FROM calificaciones WHERE id_paquete = ?1 AND active = ?2 ORDER BY ultima_modificacion DESC", nativeQuery = true)
    Page<Calificaciones> findAllByPaquetesAndActiveOrderByUltimaModificacionDesc(String idPaquete, Boolean active, Pageable pageable);

    //avg servicio calificacion
    @Query(value = "SELECT AVG(calificacion) FROM calificaciones WHERE id_servicio = ?1 AND active = ?2", nativeQuery = true)
    Double avgCalificacionServicio(String idServicio, Boolean active);

    //avg paquete calificacion
    @Query(value = "SELECT AVG(calificacion) FROM calificaciones WHERE id_paquete = ?1 AND active = ?2", nativeQuery = true)
    Double avgCalificacionPaquete(String idPaquete, Boolean active);
}
