package utez.edu.mx.movies.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.movies.Repository.GenerosRepository;
import utez.edu.mx.movies.entities.Generos;
import utez.edu.mx.movies.utils.CustomResponse;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GenerosServices {
    @Autowired
    private GenerosRepository repository;

    @Transactional(readOnly = true)
    public CustomResponse<List<Generos>> getAll() throws SQLException {
        return new CustomResponse<>(this.repository.findAll(), false, 200, "Ok");
    }

    @Transactional(readOnly = true)
    public CustomResponse<Generos> getById(Long id) throws SQLException {
        Optional<Generos> generosOptional = this.repository.findById(id);
        if (generosOptional.isPresent()) {
            return new CustomResponse<>(
                    generosOptional.get(),
                    false,
                    404,
                    "Ok"
            );
        } else {
            return new CustomResponse<>(
                    null,
                    true,
                    200,
                    "Género no encontrado"
            );
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Generos> save(Generos genero) throws SQLException {
        if (this.repository.existsByName(genero.getName())) {
            return new CustomResponse<>(
                    null,
                    false,
                    404,
                    "¡Este Género ya existe!"
            );
        } else {
            return new CustomResponse<>(
                    this.repository.saveAndFlush(genero),
                    true,
                    200,
                    "Género registrada correctamente"
            );
        }
    }



}
