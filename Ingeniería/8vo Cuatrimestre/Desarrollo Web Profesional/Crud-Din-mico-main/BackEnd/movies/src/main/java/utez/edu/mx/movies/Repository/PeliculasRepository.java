package utez.edu.mx.movies.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utez.edu.mx.movies.entities.Peliculas;

import java.util.Date;
import java.util.List;

@Repository
public interface PeliculasRepository extends JpaRepository<Peliculas,Long> {
    boolean existsByNombre(String nombre);

    //Consultar películas por nombre
    List<Peliculas> findByNombreContaining(String nombre);

    //Consultar películas por nombre del director
    List<Peliculas> findByDirectorMovieContaining(String director);

    //Filtrar películas por rango de fechas de publicación
    List<Peliculas> findByFechaPublicacionBetween(Date fechaInicio, Date fechaFin);

    //Consultar películas por género o categoría
    List<Peliculas> findByGeneroName(String genero);

    //Consultar películas por fecha de publicación de manera descendente
    List<Peliculas> findByOrderByFechaPublicacionDesc();
}
