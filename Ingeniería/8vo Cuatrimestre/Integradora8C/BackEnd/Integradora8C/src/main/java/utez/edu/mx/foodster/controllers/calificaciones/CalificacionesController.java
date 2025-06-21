package utez.edu.mx.foodster.controllers.calificaciones;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.foodster.dtos.calificaciones.CalificacionesDto;
import utez.edu.mx.foodster.entities.calificaciones.Calificaciones;
import utez.edu.mx.foodster.services.calificaciones.CalificacionesService;
import utez.edu.mx.foodster.utils.Response;

import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/calificaciones")
@CrossOrigin(value = {"*"})
public class CalificacionesController {

    private final CalificacionesService services;

    public CalificacionesController(CalificacionesService services) {
        this.services = services;
    }

    @GetMapping("/")
    public ResponseEntity<Response<List<Calificaciones>>> getAll() {
        return new ResponseEntity<>(this.services.getAll(), HttpStatus.OK);
    }
    @GetMapping("/paginado/{page}/{size}")
    public ResponseEntity<Response<Page<Calificaciones>>> getAllPaginado(@PathVariable("page") @NotNull Integer page, @PathVariable("size") @NotNull Integer size){
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(
                this.services.getAll(pageable),
                HttpStatus.OK
        );
    }

    @GetMapping("/{uid}")
    public ResponseEntity<Response<Calificaciones>> getById(@PathVariable String uid) {
        return new ResponseEntity<>(this.services.getById(uid), HttpStatus.OK);
    }

    @GetMapping("/servicios/{idServicio}/avg/")
    public ResponseEntity<Response<Double>> avgCalificacionServicio(@PathVariable @NotBlank String idServicio) {
        return new ResponseEntity<>(this.services.avgCalificacionServicio(idServicio), HttpStatus.OK);
    }

    @GetMapping("/paquetes/{idPaquete}/avg/")
    public ResponseEntity<Response<Double>> avgCalificacionPaquete(@PathVariable @NotBlank String idPaquete) {
        return new ResponseEntity<>(this.services.avgCalificacionPaquete(idPaquete), HttpStatus.OK);
    }

    @GetMapping("/servicios/{idServicio}")
    public ResponseEntity<Response<List<Calificaciones>>> getAllByServicios(@PathVariable @NotBlank String idServicio) {
        return new ResponseEntity<>(this.services.getAllByServicios(idServicio), HttpStatus.OK);
    }

    @GetMapping("/servicios/{idServicio}/paginado/{page}/{size}")
    public ResponseEntity<Response<Page<Calificaciones>>> getAllByServiciosPaginado(@PathVariable @NotBlank String idServicio, @PathVariable("page") @NotNull Integer page, @PathVariable("size") @NotNull Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(this.services.getAllByServicios(idServicio, pageable), HttpStatus.OK);
    }

    @GetMapping("/usuarios/{idUsuario}")
    public ResponseEntity<Response<List<Calificaciones>>> getAllByUsuarios(@PathVariable @NotBlank String idUsuario) {
        return new ResponseEntity<>(this.services.getAllByUsuarios(idUsuario), HttpStatus.OK);
    }

    @GetMapping("/usuarios/{idUsuario}/paginado/{page}/{size}")
    public ResponseEntity<Response<Page<Calificaciones>>> getAllByUsuariosPaginado(@PathVariable @NotBlank String idUsuario, @PathVariable("page") @NotNull Integer page, @PathVariable("size") @NotNull Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(this.services.getAllByUsuarios(idUsuario, pageable), HttpStatus.OK);
    }

    @GetMapping("/paquetes/{idPaquete}")
    public ResponseEntity<Response<List<Calificaciones>>> getAllByPaquetes(@PathVariable @NotBlank String idPaquete) {
        return new ResponseEntity<>(this.services.getAllByPaquetes(idPaquete), HttpStatus.OK);
    }

    @GetMapping("/paquetes/{idPaquete}/paginado/{page}/{size}")
    public ResponseEntity<Response<Page<Calificaciones>>> getAllByPaquetesPaginado(@PathVariable @NotBlank String idPaquete, @PathVariable("page") @NotNull Integer page, @PathVariable("size") @NotNull Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(this.services.getAllByPaquetes(idPaquete, pageable), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Response<Calificaciones>> insert(@RequestBody @Valid CalificacionesDto calificaciones) {
        return new ResponseEntity<>(this.services.insert(calificaciones.toEntity()), HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<Response<Calificaciones>> update(@RequestBody CalificacionesDto calificaciones) {
        return new ResponseEntity<>(this.services.update(calificaciones.toEntity()), HttpStatus.OK);
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity<Response<Boolean>> delete(@PathVariable @NotBlank String uid) {
        return new ResponseEntity<>(this.services.delete(uid), HttpStatus.OK);
    }
}
