package mx.edu.utez.redre.models.mensajes.models.mensajeResponsable;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;

//@Repository
public interface MRRepository extends JpaRepository<MensajeResponsable, Long>{
    List<MensajeResponsable> findByNombre(String nombre);

    void deleteAllByNombre(String matricula);
}
