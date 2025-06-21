package utez.edu.mx.movies.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.movies.entities.Generos;
import utez.edu.mx.movies.services.GenerosServices;
import utez.edu.mx.movies.utils.CustomResponse;

@RestController
@RequestMapping("/api/generos")
@CrossOrigin(origins = "*")
public class GenerosController {

    @Autowired
    GenerosServices service;

    @GetMapping (value = "/getAll")
    public CustomResponse<List<Generos>> getAllGenero() throws SQLException {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public CustomResponse<Generos> getById(@PathVariable Long id) throws SQLException {
        return service.getById(id);
    }

    @PostMapping("/registrar")
    public CustomResponse<Generos> save(@RequestBody Generos genero) throws SQLException {
        return service.save(genero);
    }
}
