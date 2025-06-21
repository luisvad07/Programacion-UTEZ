package mx.edu.utez.redre.models.responsables;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface ResponsablesRepository extends JpaRepository<Responsables, Long>{
    @Modifying
    @Query(value = "UPDATE responsable SET status = :status WHERE id = :id", nativeQuery = true)
    int updateStatusById(
            @Param("status") Boolean status,
            @Param("id")Long id);
    boolean existsByCorreo(String correo);

    List<Responsables> findAllByStatus(Boolean status);

    Responsables findByCorreo(String correo);

    Responsables getById(Long id);
}
