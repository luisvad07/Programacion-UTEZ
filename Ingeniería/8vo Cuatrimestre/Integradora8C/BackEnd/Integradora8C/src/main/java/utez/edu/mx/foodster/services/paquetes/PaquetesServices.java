package utez.edu.mx.foodster.services.paquetes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.foodster.entities.paquetes.Paquetes;
import utez.edu.mx.foodster.entities.paquetes.PaquetesRepository;
import utez.edu.mx.foodster.utils.Base64DummyImages;
import utez.edu.mx.foodster.utils.Response;

import java.sql.SQLDataException;
import java.util.List;
import java.util.Optional;

@Service
public class PaquetesServices {
    private final PaquetesRepository repository;

    public PaquetesServices(PaquetesRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Response<List<Paquetes>> getAll() {
        return new Response<>(this.repository.findAllByActiveOrderByUltimaModificacionDesc(true), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<Page<Paquetes>> getAll(Pageable pageable) {
        return new Response<>(this.repository.findAllByActiveOrderByUltimaModificacionDesc(true, pageable), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<List<Paquetes>> getAllByStatus(Boolean status) {
        return new Response<>(this.repository.findAllByActiveOrderByUltimaModificacionDesc(status), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<Page<Paquetes>> getAllByStatus(Boolean status, Pageable pageable) {
        return new Response<>(this.repository.findAllByActiveOrderByUltimaModificacionDesc(status, pageable), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<Paquetes> getById(String id) {
        return new Response<>(this.repository.findByIdPaqueteAndActive(id, true), false, 200, "OK");
    }

    @Transactional(rollbackFor = {SQLDataException.class})
    public Response<Paquetes> insert(Paquetes paquetes) {
        if (paquetes.getImagen().isEmpty()) {
            paquetes.setImagen(Base64DummyImages.PLACEHOLDER);
        }
        return new Response<>(this.repository.save(paquetes), false, 200, "OK");
    }

    @Transactional(rollbackFor = {SQLDataException.class})
    public Response<Paquetes> update(Paquetes paquetes) {
        Optional<Paquetes> entity = this.repository.findById(paquetes.getIdPaquete());
        if (entity.isPresent()) {
            if (paquetes.getImagen().isEmpty()) {
                paquetes.setImagen(entity.get().getImagen());
            }
            return new Response<>(this.repository.saveAndFlush(paquetes), false, 200, "OK");
        }
        return new Response<>(null, true, 400, "No encontrado para actualizar");
    }

    @Transactional(rollbackFor = {SQLDataException.class})
    public Response<Boolean> delete(String id) {
        Optional<Paquetes> entity = this.repository.findById(id);
        if (entity.isPresent()) {
            entity.get().setActive(!entity.get().getActive());
            return new Response<>(this.repository.saveAndFlush(entity.get()).getActive(), false, 200, "OK");
        }
        return new Response<>(null, true, 400, "No encontrado para eliminar");
    }

    @Transactional(rollbackFor = {SQLDataException.class})
    public Response<Paquetes> changeStatus(String id) {
        Optional<Paquetes> entity = this.repository.findById(id);
        if (entity.isPresent()) {
            entity.get().setActive(!entity.get().getActive());
            return new Response<>(this.repository.saveAndFlush(entity.get()), false, 200, "OK");
        }
        return new Response<>(null, true, 400, "No encontrado para cambiar estado");
    }
}
