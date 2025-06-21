package utez.edu.mx.foodster.entities.direcciones;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DireccionesRepository extends JpaRepository<Direcciones, String> {
    List<Direcciones> findAllByActiveOrderByUltimaModificacionDesc(Boolean active);

    Page<Direcciones> findAllByActiveOrderByUltimaModificacionDesc(Boolean active, Pageable pageable);

    Direcciones findByIdDireccionAndActive(String idDireccion, Boolean active);


    @Query(value = "SELECT direcciones.* FROM direcciones INNER JOIN direcciones_usuario ON direcciones.id_direccion = direcciones_usuario.id_direccion WHERE direcciones_usuario.id_usuario = ?1 AND direcciones.active = ?2", nativeQuery = true)
    List<Direcciones> findAllByIdUsuarioAndActive(String idUsuario, Boolean active);
}
