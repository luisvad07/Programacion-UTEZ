package mx.edu.utez.redre.models.consultas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;

//@Repository
public interface ConsultasRepository extends JpaRepository<Consultas, Long> {
    List<Consultas> findByUsuario(String usuario);
}


