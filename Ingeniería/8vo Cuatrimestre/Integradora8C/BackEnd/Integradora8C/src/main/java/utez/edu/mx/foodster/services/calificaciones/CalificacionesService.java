package utez.edu.mx.foodster.services.calificaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.foodster.entities.calificaciones.Calificaciones;
import utez.edu.mx.foodster.entities.calificaciones.CalificacionesRepository;
import utez.edu.mx.foodster.utils.Response;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CalificacionesService {

    private final CalificacionesRepository repository;

    @Autowired
    public CalificacionesService(CalificacionesRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Response<List<Calificaciones>> getAll() {
        return new Response<>(this.repository.findAllByActiveOrderByUltimaModificacionDesc(true), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<Page<Calificaciones>> getAll(Pageable pageable) {
    return new Response<>(this.repository.findAllByActiveOrderByUltimaModificacionDesc(true, pageable), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<List<Calificaciones>> getAllByStatus(Boolean status) {
        return new Response<>(this.repository.findAllByActiveOrderByUltimaModificacionDesc(status), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<Page<Calificaciones>> getAllByStatus(Boolean status, Pageable pageable) {
    return new Response<>(this.repository.findAllByActiveOrderByUltimaModificacionDesc(status, pageable), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<Calificaciones> getById(String id) {
        return new Response<>(this.repository.findByIdCalificacionAndActive(id, true), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<Double> avgCalificacionServicio(String idServicio) {
        return new Response<>(this.repository.avgCalificacionServicio(idServicio, true), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<Double> avgCalificacionPaquete(String idPaquete) {
        return new Response<>(this.repository.avgCalificacionPaquete(idPaquete, true), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<List<Calificaciones>> getAllByServicios(String idServicio) {
        return new Response<>(this.repository.findAllByServiciosAndActiveOrderByUltimaModificacionDesc(idServicio, true), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<Page<Calificaciones>> getAllByServicios(String idServicio, Pageable pageable) {
    return new Response<>(this.repository.findAllByServiciosAndActiveOrderByUltimaModificacionDesc(idServicio, true, pageable), false, 200, "OK");
    }


    @Transactional(readOnly = true)
    public Response<List<Calificaciones>> getAllByUsuarios(String idUsuario) {
        return new Response<>(this.repository.findAllByUsuariosAndActiveOrderByUltimaModificacionDesc(idUsuario, true), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<Page<Calificaciones>> getAllByUsuarios(String idUsuario, Pageable pageable) {
    return new Response<>(this.repository.findAllByUsuariosAndActiveOrderByUltimaModificacionDesc(idUsuario, true, pageable), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<List<Calificaciones>> getAllByPaquetes(String idPaquete) {
        return new Response<>(this.repository.findAllByPaquetesAndActiveOrderByUltimaModificacionDesc(idPaquete, true), false, 200, "OK");
    }

    @Transactional(readOnly = true)
    public Response<Page<Calificaciones>> getAllByPaquetes(String idPaquete, Pageable pageable) {
    return new Response<>(this.repository.findAllByPaquetesAndActiveOrderByUltimaModificacionDesc(idPaquete, true, pageable), false, 200, "OK");
    }

    @Transactional(rollbackFor = {Exception.class})
    public Response<Calificaciones> insert(Calificaciones calificaciones) {
        return new Response<>(this.repository.save(calificaciones), false, 200, "OK");
    }

    @Transactional(rollbackFor = {Exception.class})
    public Response<Calificaciones> update(Calificaciones calificaciones) {
        return new Response<>(this.repository.saveAndFlush(calificaciones), false, 200, "OK");
    }

    @Transactional(rollbackFor = {Exception.class})
    public Response<Boolean> delete(String id) {
        Optional<Calificaciones> entity = this.repository.findById(id);
        if (entity.isPresent()) {
            Calificaciones calificaciones = entity.get();
            calificaciones.setActive(!calificaciones.getActive());
            this.repository.save(calificaciones);
            return new Response<>(true, false, 200, "OK");
        }
        return new Response<>(null, true, 400, "No encontrado para eliminar");
    }

    @Transactional(rollbackFor = {Exception.class})
    public Response<Boolean> changeStatus(String id) {
        Calificaciones calificaciones = this.repository.findById(id).orElse(null);
        if (calificaciones != null) {
            calificaciones.setActive(!calificaciones.getActive());
            this.repository.save(calificaciones);
            return new Response<>(true, false, 200, "OK");
        }
        return new Response<>(null, true, 400, "No encontrado para cambiar status");
    }


}
