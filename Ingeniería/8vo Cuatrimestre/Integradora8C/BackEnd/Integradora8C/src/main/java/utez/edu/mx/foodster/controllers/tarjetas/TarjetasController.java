package utez.edu.mx.foodster.controllers.tarjetas;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.foodster.dtos.tarjetas.TarjetasDto;
import utez.edu.mx.foodster.entities.tarjetas.Tarjetas;
import utez.edu.mx.foodster.services.tarjetas.TarjetasService;
import utez.edu.mx.foodster.utils.Response;

import java.security.GeneralSecurityException;
import java.util.List;

//@RestController
//@RequestMapping("${apiPrefix}/tarjetas")
//@CrossOrigin(value = {"*"})
public class TarjetasController {

    private final TarjetasService services;

    public TarjetasController(TarjetasService services) {
        this.services = services;
    }

    @GetMapping("/")
    public ResponseEntity<Response<List<Tarjetas>>> getAll() {
        return new ResponseEntity<>(this.services.getAll(), HttpStatus.OK);
    }

    @GetMapping("/paginado/{page}/{size}")
    public ResponseEntity<Response<Page<Tarjetas>>> getAllPaginado(@PathVariable("page") @NotNull Integer page, @PathVariable("size") @NotNull Integer size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return new ResponseEntity<>(this.services.getAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Response<List<Tarjetas>>> getAllByStatus(@PathVariable("status") @NotNull Boolean status) {
        return new ResponseEntity<>(this.services.getAllByStatus(status), HttpStatus.OK);
    }

    @GetMapping("/status/{status}/paginado/{page}/{size}")
    public ResponseEntity<Response<Page<Tarjetas>>> getAllByStatusPaginado(@PathVariable("status") @NotNull Boolean status, @PathVariable("page") @NotNull Integer page, @PathVariable("size") @NotNull Integer size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return new ResponseEntity<>(this.services.getAllByStatus(status, pageable), HttpStatus.OK);
    }

    @GetMapping("/usuario/")
    public ResponseEntity<Response<List<Tarjetas>>> getAllByIdUsuario() throws GeneralSecurityException {
        return new ResponseEntity<>(this.services.getAllByIdUsuario(), HttpStatus.OK);
    }


    @GetMapping("/{uid}")
    public ResponseEntity<Response<Tarjetas>> getById(@PathVariable("uid") @NotNull String id) {
        return new ResponseEntity<>(this.services.getById(id), HttpStatus.OK);
    }


    @PostMapping("/")
    public ResponseEntity<Response<Tarjetas>> insert(@RequestBody @Valid TarjetasDto tarjetas) throws GeneralSecurityException {
        return new ResponseEntity<>(this.services.save(tarjetas.toEntity()), HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<Response<Tarjetas>> update(@RequestBody @Valid TarjetasDto tarjetas) throws GeneralSecurityException {
        return new ResponseEntity<>(this.services.update(tarjetas.toEntity()), HttpStatus.OK);
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity<Response<Tarjetas>> delete(@PathVariable("uid") @NotNull String id) {
        return new ResponseEntity<>(this.services.delete(id), HttpStatus.OK);
    }
}
