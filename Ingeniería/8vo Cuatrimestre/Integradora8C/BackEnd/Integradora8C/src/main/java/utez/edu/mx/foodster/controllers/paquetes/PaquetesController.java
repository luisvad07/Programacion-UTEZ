package utez.edu.mx.foodster.controllers.paquetes;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.foodster.dtos.paquetes.PaquetesDto;
import utez.edu.mx.foodster.entities.paquetes.Paquetes;
import utez.edu.mx.foodster.services.paquetes.PaquetesServices;
import utez.edu.mx.foodster.utils.Response;

import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/paquetes")
@CrossOrigin(value = {"*"})
public class PaquetesController {
    private final PaquetesServices services;

    public PaquetesController(PaquetesServices services) {
        this.services = services;
    }

    @GetMapping("/")
    public ResponseEntity<Response<List<Paquetes>>> getAll() {
        return new ResponseEntity<>(
                this.services.getAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/paginado/{page}/{size}")
    public ResponseEntity<Response<Page<Paquetes>>> getAllPaginado(@PathVariable("page") @NotNull Integer page, @PathVariable("size") @NotNull Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(
                this.services.getAll(pageable),
                HttpStatus.OK
        );
    }

    @GetMapping("/{uid}")
    public ResponseEntity<Response<Paquetes>> getById(@PathVariable("uid") @NotBlank String uid) {
        return new ResponseEntity<>(this.services.getById(uid), HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Response<List<Paquetes>>> getAllByStatus(@PathVariable("status") @NotNull Boolean status) {
        return new ResponseEntity<>(
                this.services.getAllByStatus(status),
                HttpStatus.OK
        );
    }

    @GetMapping("/status/{status}/paginado/{page}/{size}")
    public ResponseEntity<Response<Page<Paquetes>>> getAllByStatusPaginado(@PathVariable("status") @NotNull Boolean status, @PathVariable("page") @NotNull Integer page, @PathVariable("size") @NotNull Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(
                this.services.getAllByStatus(status, pageable),
                HttpStatus.OK
        );
    }

    @PostMapping("/")
    public ResponseEntity<Response<Paquetes>> insert(@RequestBody @Valid PaquetesDto dto) {
        return new ResponseEntity<>(
                this.services.insert(dto.toEntity()),
                HttpStatus.OK
        );
    }

    @PutMapping("/")
    public ResponseEntity<Response<Paquetes>> update(@RequestBody @Valid PaquetesDto dto) {
        return new ResponseEntity<>(
                this.services.update(dto.toEntity()),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity<Response<Boolean>> delete(@PathVariable("uid") @NotBlank String id) {
        return new ResponseEntity<>(
                this.services.delete(id),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/status/{uid}")
    public ResponseEntity<Response<Paquetes>> changeStatus(@PathVariable("uid") @NotBlank String id) {
        return new ResponseEntity<>(
                this.services.changeStatus(id),
                HttpStatus.OK
        );
    }

}
