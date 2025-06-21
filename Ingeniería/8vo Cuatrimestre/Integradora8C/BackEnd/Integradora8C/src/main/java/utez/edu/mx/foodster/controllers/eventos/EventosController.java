package utez.edu.mx.foodster.controllers.eventos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.foodster.dtos.eventos.EventoConServicios;
import utez.edu.mx.foodster.dtos.eventos.EventosDto;
import utez.edu.mx.foodster.entities.eventos.Eventos;
import utez.edu.mx.foodster.services.eventos.EventosServices;
import utez.edu.mx.foodster.utils.Response;

import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/eventos")
@CrossOrigin(value = {"*"})
public class EventosController {
    private final EventosServices services;

    public EventosController(EventosServices services) {
        this.services = services;
    }

    @GetMapping("/")
    public ResponseEntity<Response<List<Eventos>>> getAll() {
        return new ResponseEntity<>(this.services.getAll(), HttpStatus.OK);
    }

    @GetMapping("/paginado/{page}/{size}")
    public ResponseEntity<Response<Page<Eventos>>> getAllPaginado(@PathVariable("page") @NotNull Integer page, @PathVariable("size") @NotNull Integer size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return new ResponseEntity<>(this.services.getAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Response<List<Eventos>>> getAllByStatus(@PathVariable("status") @NotNull Boolean status) {
        return new ResponseEntity<>(this.services.getAllByStatus(status), HttpStatus.OK);
    }

    @GetMapping("/status/{status}/paginado/{page}/{size}")
    public ResponseEntity<Response<Page<Eventos>>> getAllByStatusPaginado(@PathVariable("status") @NotNull Boolean status, @PathVariable("page") @NotNull Integer page, @PathVariable("size") @NotNull Integer size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return new ResponseEntity<>(this.services.getAllByStatus(status, pageable), HttpStatus.OK);
    }

    @GetMapping("/{uid}")
    public ResponseEntity<Response<Eventos>> getById(@PathVariable("uid") @NotBlank String id) {
        return new ResponseEntity<>(this.services.getById(id), HttpStatus.OK);
    }


    @GetMapping("/usuario/")
    public ResponseEntity<Response<List<Eventos>>> getAllByIdUsuario() {
        return new ResponseEntity<>(this.services.getAllByIdUsuario(), HttpStatus.OK);
    }
    @GetMapping("/usuario/{uid}")
    public ResponseEntity<Response<List<Eventos>>> getAllByIdUsuario(@PathVariable("uid") @NotBlank String idUsuario) {
        return new ResponseEntity<>(this.services.getAllByIdUsuario(idUsuario), HttpStatus.OK);
    }

    @GetMapping("/personal/{uid}")
    public ResponseEntity<Response<List<Eventos>>> getAllByPersonalIdUsuario(@PathVariable("uid") @NotBlank String idUsuario) {
        return new ResponseEntity<>(this.services.getAllByPersonalIdUsuario(idUsuario), HttpStatus.OK);
    }
    @GetMapping("/personal/")
    public ResponseEntity<Response<List<Eventos>>> getAllByPersonalIdUsuario() {
        return new ResponseEntity<>(this.services.getAllByPersonalIdUsuario(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Response<Eventos>> insert(@RequestBody @Valid EventoConServicios dto) {
        return new ResponseEntity<>(this.services.insert(dto), HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<Response<Eventos>> update(@RequestBody @Valid EventosDto dto) {
        return new ResponseEntity<>(this.services.update(dto.toEntity()), HttpStatus.OK);
    }

    @PutMapping("/finalizar/{uid}")
    public ResponseEntity<Response<Eventos>> finalizar(@PathVariable("uid") @NotBlank String id) {
        return new ResponseEntity<>(this.services.setFinalizado(id), HttpStatus.OK);
    }

    @PutMapping("/cancelar/{uid}")
    public ResponseEntity<Response<Eventos>> cancelar(@PathVariable("uid") @NotBlank String id) {
        return new ResponseEntity<>(this.services.setCancelado(id), HttpStatus.OK);
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity<Response<Boolean>> delete(@PathVariable("uid") @NotBlank String id) {
        return new ResponseEntity<>(this.services.delete(id), HttpStatus.OK);
    }
}
