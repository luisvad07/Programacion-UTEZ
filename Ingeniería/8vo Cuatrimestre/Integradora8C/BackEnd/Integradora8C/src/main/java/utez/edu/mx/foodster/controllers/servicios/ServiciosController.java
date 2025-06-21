package utez.edu.mx.foodster.controllers.servicios;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.foodster.dtos.servicios.ServiciosDto;
import utez.edu.mx.foodster.entities.servicios.Servicios;
import utez.edu.mx.foodster.services.servicios.ServiciosServices;
import utez.edu.mx.foodster.utils.Response;

import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/servicios")
@CrossOrigin(value = {"*"})
public class ServiciosController {
    private final ServiciosServices services;

    public ServiciosController(ServiciosServices services) {
        this.services = services;
    }

    @GetMapping("/")
    public ResponseEntity<Response<List<Servicios>>> getAll(){
        return new ResponseEntity<>(
                this.services.getAll(),
                HttpStatus.OK
        );
    }
    @GetMapping("/paginado/{page}/{size}")
    public ResponseEntity<Response<Page<Servicios>>> getAllPaginado(@PathVariable("page") @NotNull Integer page, @PathVariable("size") @NotNull Integer size){
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(
                this.services.getAll(pageable),
                HttpStatus.OK
        );
    }
    @GetMapping("/status/{status}")
    public ResponseEntity<Response<List<Servicios>>> getAllByStatus(@PathVariable("status") @NotNull Boolean status){
        return new ResponseEntity<>(
                this.services.getAllByStatus(status),
                HttpStatus.OK
        );
    }

    @GetMapping("/status/{status}/paginado/{page}/{size}")
    public ResponseEntity<Response<Page<Servicios>>>
    getAllByStatusPaginado(@PathVariable("status") @NotNull Boolean status, @PathVariable("page") @NotNull Integer page, @PathVariable("size") @NotNull Integer size){
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(
                this.services.getAllByStatus(status, pageable),
                HttpStatus.OK
        );
    }

    @GetMapping("/{uid}")
    public ResponseEntity<Response<Servicios>> getById(@PathVariable("uid") @NotBlank String id){
        return new ResponseEntity<>(
                this.services.getById(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/categoria/{uid}")
    public ResponseEntity<Response<List<Servicios>>> getAllByIdCategoria(@PathVariable("uid") @NotBlank String id){
        return new ResponseEntity<>(
                this.services.getAllByIdCategoria(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/categoria/{uid}/paginado/{page}/{size}")
    public ResponseEntity<Response<Page<Servicios>>> getAllByIdCategoria(@PathVariable("uid") @NotBlank String id, @PathVariable("page") @NotNull Integer page, @PathVariable("size") @NotNull Integer size){
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(
                this.services.getAllByIdCategoria(id, pageable),
                HttpStatus.OK
        );
    }

    @PostMapping("/")
    public ResponseEntity<Response<Servicios>> insert(@RequestBody @Valid ServiciosDto dto){
        return new ResponseEntity<>(
                this.services.insert(dto.toEntity()),
                HttpStatus.OK
        );
    }
    @PutMapping("/")
    public ResponseEntity<Response<Servicios>> update(@RequestBody @Valid ServiciosDto dto){
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
    public ResponseEntity<Response<Servicios>> changeStatus(@PathVariable("uid") @NotBlank String id){
        return new ResponseEntity<>(
                this.services.changeStatus(id),
                HttpStatus.OK
        );
    }
}
