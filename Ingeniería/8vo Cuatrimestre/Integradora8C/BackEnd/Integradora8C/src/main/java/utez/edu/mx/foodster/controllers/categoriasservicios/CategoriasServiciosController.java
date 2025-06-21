package utez.edu.mx.foodster.controllers.categoriasservicios;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.foodster.dtos.categoriasservicios.CategoriasServiciosDto;
import utez.edu.mx.foodster.entities.categoriasservicios.CategoriasServicios;
import utez.edu.mx.foodster.services.categoriasservicios.CategoriasServiciosServices;
import utez.edu.mx.foodster.utils.Response;

import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/categorias-servicios")
@CrossOrigin(value = {"*"})
public class CategoriasServiciosController {
    private final CategoriasServiciosServices services;

    public CategoriasServiciosController(CategoriasServiciosServices services) {
        this.services = services;
    }

    @GetMapping("/")
    public ResponseEntity<Response<List<CategoriasServicios>>> getAll() {
        return new ResponseEntity<>(this.services.getAll(), HttpStatus.OK);
    }

    @GetMapping("/paginado/{page}/{size}")
    public ResponseEntity<Response<Page<CategoriasServicios>>> getAllPaginado(@PathVariable("page")  @NotNull Integer page, @NotNull @PathVariable("size") Integer size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return new ResponseEntity<>(this.services.getAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{uid}")
    public ResponseEntity<Response<CategoriasServicios>> getById(@PathVariable("uid") @NotBlank String uid) {
        return new ResponseEntity<>(this.services.getById(uid), HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Response<List<CategoriasServicios>>> getAllByStatus(@PathVariable("status") Boolean status) {
        return new ResponseEntity<>(this.services.getAllByStatus(status), HttpStatus.OK);
    }

    @GetMapping("/status/{status}/paginado/{page}/{size}")
    public ResponseEntity<Response<Page<CategoriasServicios>>> getAllByStatusPaginado(@PathVariable("status") Boolean status, @PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return new ResponseEntity<>(this.services.getAllByStatus(status, pageable), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Response<CategoriasServicios>> insert(@RequestBody @Valid CategoriasServiciosDto dto) {
        return new ResponseEntity<>(this.services.insert(dto.toEntity()), HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<Response<CategoriasServicios>> update(@RequestBody @Valid CategoriasServiciosDto dto) {
        return new ResponseEntity<>(this.services.update(dto.toEntity()), HttpStatus.OK);
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity<Response<Boolean>> delete(@PathVariable("uid") @NotBlank String id) {
        return new ResponseEntity<>(this.services.delete(id), HttpStatus.OK);
    }

    @DeleteMapping("/status/{uid}")
    public ResponseEntity<Response<Boolean>> changeStatus(@PathVariable("uid") @NotBlank String id) {
        return new ResponseEntity<>(this.services.changeStatus(id), HttpStatus.OK);
    }
}
