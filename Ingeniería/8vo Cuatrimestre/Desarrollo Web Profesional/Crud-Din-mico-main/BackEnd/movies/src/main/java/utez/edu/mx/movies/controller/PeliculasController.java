package utez.edu.mx.movies.controller;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.movies.entities.Peliculas;
import utez.edu.mx.movies.services.PeliculasServices;
import utez.edu.mx.movies.utils.CustomResponse;


@RestController
@RequestMapping("/api/peliculas")
@CrossOrigin(origins = "*")
public class PeliculasController {

    @Autowired
    PeliculasServices service;

    @GetMapping("/getAll")
    public CustomResponse<List<Peliculas>> getAllPeliculas() throws SQLException {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public CustomResponse<Peliculas> getById(@PathVariable Long id) throws SQLException {
        return service.getById(id);
    }

    @PostMapping("/registrar")
    public CustomResponse<Peliculas> save(@RequestBody Peliculas pelicula) throws SQLException {
        return service.save(pelicula);
    }

    @PutMapping("/actualizar")
    public CustomResponse<Peliculas> update(@RequestBody Peliculas pelicula) throws SQLException {
        return service.update(pelicula);
    }

    @DeleteMapping("/{id}")
    public CustomResponse<Peliculas> delete(@PathVariable Long id) throws SQLException {
        return service.delete(id);
    }

    // Método para buscar películas por nombre
    @GetMapping("/buscarPorNombre")
    public CustomResponse<List<Peliculas>> getPeliculasByNombre(@RequestParam String nombre) throws SQLException {
        return service.findPeliculasByNombre(nombre);
    }

    // Método para buscar películas por nombre del director
    @GetMapping("/buscarPorDirector")
    public CustomResponse<List<Peliculas>> getPeliculasByDirector(@RequestParam String director) throws SQLException {
        return service.findPeliculasByDirector(director);
    }

    // Método para buscar películas por rango de fechas de publicación
    @GetMapping("/buscarPorFechaPublicacion")
    public CustomResponse<List<Peliculas>> getPeliculasByFechaPublicacion(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date fechaInicio, @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date fechaFin) throws SQLException {
        return service.findPeliculasByFechaPublicacion(fechaInicio, fechaFin);
    }

    // Método para buscar películas por género
    @GetMapping("/buscarPorGenero")
    public CustomResponse<List<Peliculas>> getPeliculasByGenero(@RequestParam String genero) throws SQLException {
        return service.findPeliculasByGenero(genero);
    }

    // Método para buscar películas por fecha de publicación de manera descendente
    @GetMapping("/buscarPorFechaPublicacionDesc")
    public CustomResponse<List<Peliculas>> getPeliculasOrderByFechaPublicacionDesc() throws SQLException {
        return service.findPeliculasOrderByFechaPublicacionDesc();
    }
}
