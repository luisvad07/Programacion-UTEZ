package utez.edu.mx.foodster.controllers.serviciosevento;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.foodster.dtos.serviciosevento.ServiciosEventoDto;
import utez.edu.mx.foodster.entities.serviciosevento.ServiciosEvento;
import utez.edu.mx.foodster.services.serviciosevento.ServiciosEventoServices;
import utez.edu.mx.foodster.utils.Response;

import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/servicios-evento")
@CrossOrigin(value = {"*"})
public class ServiciosEventoController {
    private final ServiciosEventoServices services;

    public ServiciosEventoController(ServiciosEventoServices services) {
        this.services = services;
    }

    @GetMapping("/")
    public ResponseEntity<Response<List<ServiciosEvento>>> getAll() {
        return new ResponseEntity<>(this.services.getAll(), HttpStatus.OK);
    }

    @GetMapping("/paginado/{page}/{size}")
    public ResponseEntity<Response<Page<ServiciosEvento>>> getAllPaginado(@PathVariable("page") @NotNull Integer page, @PathVariable("size") @NotNull Integer size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return new ResponseEntity<>(this.services.getAll(pageable), HttpStatus.OK);
    }
    @GetMapping("/status/{status}")
    public ResponseEntity<Response<List<ServiciosEvento>>> getAllByStatus(@PathVariable("status") @NotNull Boolean status) {
        return new ResponseEntity<>(this.services.getAllByStatus(status), HttpStatus.OK);
    }

    @GetMapping("/status/{status}/paginado/{page}/{size}")
    public ResponseEntity<Response<Page<ServiciosEvento>>> getAllByStatusPaginado(@PathVariable("status") @NotNull Boolean status, @PathVariable("page") @NotNull Integer page, @PathVariable("size") @NotNull Integer size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return new ResponseEntity<>(this.services.getAllByStatus(status, pageable), HttpStatus.OK);
    }

    @GetMapping("/{uid}")
    public ResponseEntity<Response<ServiciosEvento>> getById(@PathVariable("uid") @NotBlank String id) {
        return new ResponseEntity<>(this.services.getById(id), HttpStatus.OK);
    }

    @GetMapping("/evento/{uid}")
    public ResponseEntity<Response<List<ServiciosEvento>>> getAllByIdEvento(@PathVariable("uid") @NotBlank String id) {
        return new ResponseEntity<>(this.services.getAllByIdEvento(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Response<ServiciosEvento>> insert(@RequestBody @Valid ServiciosEventoDto dto) {
        return new ResponseEntity<>(this.services.insert(dto.toEntity()), HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<Response<ServiciosEvento>> update(@RequestBody @Valid ServiciosEventoDto dto) {
        return new ResponseEntity<>(this.services.update(dto.toEntity()), HttpStatus.OK);
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity<Response<Boolean>> delete(@PathVariable("uid") @NotBlank String id) {
        return new ResponseEntity<>(this.services.delete(id), HttpStatus.OK);
    }

    @DeleteMapping("/status/{uid}")
    public ResponseEntity<Response<ServiciosEvento>> changeStatus(@PathVariable("uid") @NotBlank String id) {
        return new ResponseEntity<>(this.services.changeStatus(id), HttpStatus.OK);
    }
}
