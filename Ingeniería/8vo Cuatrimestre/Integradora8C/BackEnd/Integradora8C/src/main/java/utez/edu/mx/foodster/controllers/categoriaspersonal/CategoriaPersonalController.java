package utez.edu.mx.foodster.controllers.categoriaspersonal;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.foodster.dtos.categoriaspersonal.CategoriasPersonalDto;
import utez.edu.mx.foodster.entities.categoriaspersonal.CategoriasPersonal;
import utez.edu.mx.foodster.services.categoriaspersonal.CategoriasPersonalServices;
import utez.edu.mx.foodster.utils.Response;

import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/categorias-personal")
@CrossOrigin(value = {"*"})
public class CategoriaPersonalController {
    private final CategoriasPersonalServices services;

    public CategoriaPersonalController(CategoriasPersonalServices services) {
        this.services = services;
    }

    @GetMapping("/")
    public ResponseEntity<Response<List<CategoriasPersonal>>> getAll() {
        return new ResponseEntity<>(this.services.getAll(), HttpStatus.OK);
    }

    @GetMapping("/paginado/{page}/{size}")
    public ResponseEntity<Response<Page<CategoriasPersonal>>> getAllPaginado(@PathVariable("page") @NotNull Integer page, @PathVariable("size") @NotNull Integer size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return new ResponseEntity<>(this.services.getAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{uid}")
    public ResponseEntity<Response<CategoriasPersonal>> getById(@PathVariable("uid") @NotBlank String uid) {
        return new ResponseEntity<>(this.services.getById(uid), HttpStatus.OK);
    }
    @GetMapping("/status/{status}")
    public ResponseEntity<Response<List<CategoriasPersonal>>> getAllByStatus(@PathVariable("status") @NotNull Boolean status){
        return new ResponseEntity<>(
                this.services.getAllByStatus(status),
                HttpStatus.OK
        );
    }

    @GetMapping("/status/{status}/paginado/{page}/{size}")
    public ResponseEntity<Response<Page<CategoriasPersonal>>> getAllByStatusPaginado(@PathVariable("status") @NotNull Boolean status, @PathVariable("page") @NotNull Integer page, @PathVariable("size") @NotNull Integer size){
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return new ResponseEntity<>(
                this.services.getAllByStatus(status, pageable),
                HttpStatus.OK
        );
    }


    @PostMapping("/")
    public ResponseEntity<Response<CategoriasPersonal>> insert(@RequestBody @Valid CategoriasPersonalDto categoriasPersonalDto) {
        return new ResponseEntity<>(this.services.insert(categoriasPersonalDto.toEntity()), HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<Response<CategoriasPersonal>> update(@RequestBody @Valid CategoriasPersonalDto categoriasPersonalDto) {
        return new ResponseEntity<>(this.services.update(categoriasPersonalDto.toEntity()), HttpStatus.OK);
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity<Response<Boolean>> delete(@PathVariable("uid") @NotBlank String uid) {
        return new ResponseEntity<>(this.services.delete(uid), HttpStatus.OK);
    }
}
