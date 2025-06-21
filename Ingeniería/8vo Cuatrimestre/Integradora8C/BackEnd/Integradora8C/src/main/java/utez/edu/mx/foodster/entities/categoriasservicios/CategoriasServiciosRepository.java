package utez.edu.mx.foodster.entities.categoriasservicios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriasServiciosRepository extends JpaRepository<CategoriasServicios, String> {
    List<CategoriasServicios> findAllByActiveOrderByUltimaModificacionDesc(Boolean active);

    Page<CategoriasServicios> findAllByActiveOrderByUltimaModificacionDesc(Boolean active, Pageable pageable);

    CategoriasServicios findByNombreAndActive(String nombre, Boolean active);

    CategoriasServicios findByIdCategoriaAndActive(String idCategoria, Boolean active);
}
