package utez.edu.mx.foodster.services.categoriasservicios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.foodster.entities.categoriasservicios.CategoriasServicios;
import utez.edu.mx.foodster.entities.categoriasservicios.CategoriasServiciosRepository;
import utez.edu.mx.foodster.utils.Response;

import java.sql.SQLDataException;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriasServiciosServices {
    private final CategoriasServiciosRepository repository;

    public CategoriasServiciosServices(CategoriasServiciosRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Response<List<CategoriasServicios>> getAll(){
        return new Response<>(
                this.repository.findAll(),
                false,
                200,
                "OK"
        );
    }

    @Transactional(readOnly = true)
    public Response<Page<CategoriasServicios>> getAll(Pageable pageable){
        return new Response<>(
                this.repository.findAllByActiveOrderByUltimaModificacionDesc(true, pageable),
                false,
                200,
                "OK"
        );
    }

    @Transactional(readOnly = true)
    public Response<List<CategoriasServicios>> getAllByStatus(Boolean status){
        return new Response<>(
                this.repository.findAllByActiveOrderByUltimaModificacionDesc(status),
                false,
                200,
                "OK"
        );
    }

    @Transactional(readOnly = true)
    public Response<Page<CategoriasServicios>> getAllByStatus(Boolean status, Pageable pageable){
        return new Response<>(
                this.repository.findAllByActiveOrderByUltimaModificacionDesc(status, pageable),
                false,
                200,
                "OK"
        );
    }

    @Transactional(readOnly = true)
    public Response<CategoriasServicios> getById(String id){
        return new Response<>(
                this.repository.findByIdCategoriaAndActive(id, true),
                false,
                200,
                "OK"
        );
    }
    @Transactional(rollbackFor = {SQLDataException.class})
    public Response<CategoriasServicios> insert(CategoriasServicios categoriasServicios){
        return new Response<>(
                this.repository.save(categoriasServicios),
                false,
                200,
                "OK"
        );
    }
    @Transactional(rollbackFor = {SQLDataException.class})
    public Response<CategoriasServicios> update(CategoriasServicios categoriasServicios){
        Optional<CategoriasServicios> update = this.repository.findById(categoriasServicios.getIdCategoria());
        if(update.isPresent()){
            return new Response<>(
                    this.repository.saveAndFlush(categoriasServicios),
                    false,
                    200,
                    "OK"
            );
        }
        return new Response<>(
                null,
                true,
                400,
                "No encontrado para actualizar"
        );
    }
    @Transactional(rollbackFor = {SQLDataException.class})
    public Response<Boolean> delete(String id){
        Optional<CategoriasServicios> entity = this.repository.findById(id);
        if (entity.isPresent()){
           entity.get().setActive(!entity.get().getActive());
            return new Response<>(
                    this.repository.saveAndFlush(entity.get()).getActive(),
                    false,
                    200,
                    "OK"
            );
        }
        return new Response<>(
                null,
                true,
                400,
                "No encontrado para eliminar"
        );
    }
    @Transactional(rollbackFor = {SQLDataException.class})
    public Response<Boolean> changeStatus(String id){
        Optional<CategoriasServicios> entity = this.repository.findById(id);
        if (entity.isPresent()){
            entity.get().setActive(!entity.get().getActive());
            this.repository.saveAndFlush(entity.get());
            return new Response<>(
                    true,
                    false,
                    200,
                    "OK"
            );
        }
        return new Response<>(
                null,
                true,
                400,
                "No encontrado para cambiar de estado"
        );
    }
}
