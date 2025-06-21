package mx.edu.utez.redre.models.mensajes.models.mensajeAsesor;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;

//@Repository
public interface MARepository extends JpaRepository<MensajeAsesor, Long>{
    List<MensajeAsesor> findByNombreA(String nombre);

    List<MensajeAsesor> findByNombre(String matricula);

    void deleteAllByNombre(String matricula);
}
