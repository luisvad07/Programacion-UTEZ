package utez.edu.mx.movies.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.movies.entities.Generos;

public interface GenerosRepository extends JpaRepository<Generos,Long> {
    boolean existsByName(String name);
}
