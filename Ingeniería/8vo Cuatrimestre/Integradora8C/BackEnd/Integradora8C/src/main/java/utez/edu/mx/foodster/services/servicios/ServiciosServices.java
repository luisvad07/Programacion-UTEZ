package utez.edu.mx.foodster.services.servicios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.foodster.entities.servicios.Servicios;
import utez.edu.mx.foodster.entities.servicios.ServiciosRepository;
import utez.edu.mx.foodster.utils.Base64DummyImages;
import utez.edu.mx.foodster.utils.Response;

import java.sql.SQLDataException;
import java.util.List;
import java.util.Optional;

@Service
public class ServiciosServices {
    private final ServiciosRepository repository;

    public ServiciosServices(ServiciosRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Response<List<Servicios>> getAll() {
        return new Response<>(this.repository.findAllByActiveOrderByUltimaModificacionDesc(true), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<Page<Servicios>> getAll(Pageable pageable) {
        return new Response<>(this.repository.findAllByActiveOrderByUltimaModificacionDesc(true, pageable), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<Servicios> getById(String id) {
        return new Response<>(this.repository.findByIdServicioAndActive(id, true), false, 200, "OK");
    }


    @Transactional(readOnly = true)
    public Response<List<Servicios>> getAllByIdCategoria(String idCategoria) {
        return new Response<>(this.repository.findAllByCategoriasAndActiveOrderByUltimaModificacionDesc(idCategoria, true), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<Page<Servicios>> getAllByIdCategoria(String idCategoria, Pageable pageable) {
        return new Response<>(this.repository.findAllByCategoriasAndActiveOrderByUltimaModificacionDesc(idCategoria, true, pageable), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<List<Servicios>> getAllByStatus(Boolean status) {
        return new Response<>(this.repository.findAllByActiveOrderByUltimaModificacionDesc(status), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<Page<Servicios>> getAllByStatus(Boolean status, Pageable pageable) {
        return new Response<>(this.repository.findAllByActiveOrderByUltimaModificacionDesc(status, pageable), false, 200, "OK");
    }

    @Transactional(rollbackFor = {SQLDataException.class})
    public Response<Servicios> insert(Servicios servicios) {
        if (servicios.getImagen().isEmpty()) {
            servicios.setImagen(Base64DummyImages.PLACEHOLDER);
        }
        return new Response<>(this.repository.save(servicios), false, 200, "OK");
    }

    @Transactional(rollbackFor = {SQLDataException.class})
    public Response<Servicios> update(Servicios servicios) {
        Optional<Servicios> update = this.repository.findById(servicios.getIdServicio());
        if (update.isPresent()) {
            if (servicios.getImagen().isEmpty()) {
                servicios.setImagen(update.get().getImagen());
            }
            return new Response<>(this.repository.saveAndFlush(servicios), false, 200, "OK");
        }
        return new Response<>(null, true, 400, "No encontrado para actualizar");
    }

    @Transactional(rollbackFor = {SQLDataException.class})
    public Response<Boolean> delete(String id) {
        Optional<Servicios> entity = this.repository.findById(id);
        if (entity.isPresent()) {
            entity.get().setActive(!entity.get().getActive());
            return new Response<>(this.repository.saveAndFlush(entity.get()).getActive(), false, 200, "ok");
        }
        return new Response<>(null, true, 400, "No encontrado para eliminar");
    }

    @Transactional(rollbackFor = {SQLDataException.class})
    public Response<Servicios> changeStatus(String id) {
        Optional<Servicios> entity = this.repository.findById(id);
        if (entity.isPresent()) {
            entity.get().setActive(!entity.get().getActive());
            return new Response<>(this.repository.saveAndFlush(entity.get()), false, 200, "ok");
        }
        return new Response<>(null, true, 400, "No encontrado para cambiar status");
    }

}
