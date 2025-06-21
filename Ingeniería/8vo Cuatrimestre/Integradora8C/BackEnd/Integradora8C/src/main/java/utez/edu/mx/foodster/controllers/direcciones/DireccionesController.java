package utez.edu.mx.foodster.controllers.direcciones;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.foodster.dtos.direcciones.DireccionesDto;
import utez.edu.mx.foodster.entities.direcciones.Direcciones;
import utez.edu.mx.foodster.services.direcciones.DireccionesServices;
import utez.edu.mx.foodster.utils.Response;

import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/direcciones")
@CrossOrigin(value = {"*"})
public class DireccionesController {
    private final DireccionesServices services;

    public DireccionesController(DireccionesServices services) {
        this.services = services;
    }

    @GetMapping("/")
    public ResponseEntity<Response<List<Direcciones>>> getAll(){
        return new ResponseEntity<>(
                this.services.getAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/paginado/{page}/{size}")
    public ResponseEntity<Response<Page<Direcciones>>> getAllPaginado(@PathVariable("page") @NotNull Integer page, @PathVariable("size") @NotNull Integer size){
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return new ResponseEntity<>(
                this.services.getAll(pageable),
                HttpStatus.OK
        );
    }

    @GetMapping("/{uid}")
    public ResponseEntity<Response<Direcciones>> getById(@PathVariable("uid") @NotBlank String uid) {
        return new ResponseEntity<>(this.services.getById(uid), HttpStatus.OK);
    }

    @GetMapping("/usuario/")
    public ResponseEntity<Response<List<Direcciones>>> getAllByUsuario() {
        return new ResponseEntity<>(this.services.getAllByUsuario(), HttpStatus.OK);
    }

    @GetMapping("/usuario/{uid}")
    public ResponseEntity<Response<List<Direcciones>>> getAllByIdUsuario(@PathVariable("uid") @NotBlank String id){
        return new ResponseEntity<>(
                this.services.getAllByUsuario(id),
                HttpStatus.OK
        );
    }
    @GetMapping("/status/{status}")
    public ResponseEntity<Response<List<Direcciones>>> getAllByStatus(@PathVariable("status") @NotBlank Boolean status){
        return new ResponseEntity<>(
                this.services.getAllByStatus(status),
                HttpStatus.OK
        );
    }

    @GetMapping("/status/{status}/paginado/{page}/{size}")
    public ResponseEntity<Response<Page<Direcciones>>> getAllByStatusPaginado(@PathVariable("status") @NotBlank Boolean status, @PathVariable("page") @NotNull Integer page, @PathVariable("size") @NotNull Integer size){
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return new ResponseEntity<>(
                this.services.getAllByStatus(status, pageable),
                HttpStatus.OK
        );
    }

    @PostMapping("/")
    public ResponseEntity<Response<Direcciones>> insert(@RequestBody @Valid DireccionesDto dto){
        return new ResponseEntity<>(
                this.services.insert(dto),
                HttpStatus.OK
        );
    }
    @PutMapping("/")
    public ResponseEntity<Response<Direcciones>> update(@RequestBody @Valid DireccionesDto dto){
        return new ResponseEntity<>(
                this.services.update(dto.toEntity()),
                HttpStatus.OK
        );
    }
    @DeleteMapping("/{uid}")
    public ResponseEntity<Response<Boolean>> delete(@PathVariable("uid") @NotBlank String id){
        return new ResponseEntity<>(
                this.services.delete(id),
                HttpStatus.OK
        );
    }
    @DeleteMapping("/status/{uid}")
    public ResponseEntity<Response<Boolean>> changeStatus(@PathVariable("uid") @NotBlank String id){
        return new ResponseEntity<>(
                this.services.changeStatus(id),
                HttpStatus.OK
        );
    }
}
