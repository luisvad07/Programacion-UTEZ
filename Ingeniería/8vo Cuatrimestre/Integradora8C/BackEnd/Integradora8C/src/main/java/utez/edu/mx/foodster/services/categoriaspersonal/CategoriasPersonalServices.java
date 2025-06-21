package utez.edu.mx.foodster.services.categoriaspersonal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.foodster.entities.categoriaspersonal.CategoriasPersonal;
import utez.edu.mx.foodster.entities.categoriaspersonal.CategoriasPersonalRepository;
import utez.edu.mx.foodster.utils.Response;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoriasPersonalServices {
    private final CategoriasPersonalRepository repository;

    @Autowired
    public CategoriasPersonalServices(CategoriasPersonalRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Response<List<CategoriasPersonal>> getAll() {
        return new Response<>(this.repository.findAllByActiveOrderByUltimaModificacionDesc(true), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<Page<CategoriasPersonal>> getAll(Pageable pageable) {
        return new Response<>(this.repository.findAllByActiveOrderByUltimaModificacionDesc(true, pageable), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<CategoriasPersonal> getById(String id) {
        return new Response<>(this.repository.findByIdCategoriaAndActive(id, true), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<List<CategoriasPersonal>> getAllByStatus(Boolean status) {
        return new Response<>(this.repository.findAllByActiveOrderByUltimaModificacionDesc(status), false, 200, "OK");
    }
    @Transactional(readOnly = true)
    public Response<Page<CategoriasPersonal>> getAllByStatus(Boolean status, Pageable pageable) {
        return new Response<>(this.repository.findAllByActiveOrderByUltimaModificacionDesc(status, pageable), false, 200, "OK");
    }

    @Transactional(rollbackFor = {SQLException.class})
    public Response<CategoriasPersonal> insert(CategoriasPersonal categoriasPersonal) {
        return new Response<>(this.repository.save(categoriasPersonal), false, 200, "OK");
    }

    @Transactional(rollbackFor = {SQLException.class})
    public Response<CategoriasPersonal> update(CategoriasPersonal categoriasPersonal) {
        Optional<CategoriasPersonal> entityUpdate = this.repository.findById(categoriasPersonal.getIdCategoria());
        if (entityUpdate.isPresent()) {
            return new Response<>(this.repository.saveAndFlush(categoriasPersonal), false, 200, "OK");
        }
        return new Response<>(null, true, 400, "No encontrado");
    }

    @Transactional(rollbackFor = {SQLException.class})
    public Response<Boolean> delete(String id) {
        Optional<CategoriasPersonal> entity = this.repository.findById(id);
        if (entity.isPresent()) {
            entity.get().setActive(!entity.get().getActive());
            this.repository.save(entity.get());
            return new Response<>(true, false, 200, "Eliminado correctamente");
        }
        return new Response<>(null, true, 400, "No encontrado");
    }
}
