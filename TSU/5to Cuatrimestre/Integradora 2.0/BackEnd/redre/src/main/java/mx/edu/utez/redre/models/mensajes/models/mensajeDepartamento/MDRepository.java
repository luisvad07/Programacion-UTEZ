package mx.edu.utez.redre.models.mensajes.models.mensajeDepartamento;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;

//@Repository
public interface MDRepository extends JpaRepository<MensajeDepartamento, Long>{
    List<MensajeDepartamento> findByNombre(String nombre);

    void deleteAllByNombre(String matricula);
}
