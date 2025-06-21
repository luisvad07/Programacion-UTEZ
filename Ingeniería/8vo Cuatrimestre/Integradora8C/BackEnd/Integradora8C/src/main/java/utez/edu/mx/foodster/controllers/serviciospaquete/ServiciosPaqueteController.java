package utez.edu.mx.foodster.controllers.serviciospaquete;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.foodster.dtos.serviciospaquete.ServiciosPaqueteDto;
import utez.edu.mx.foodster.services.serviciospaquete.ServiciosPaqueteServices;
import utez.edu.mx.foodster.entities.serviciospaquete.ServiciosPaquete;
import utez.edu.mx.foodster.utils.Response;

import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/servicios-paquete")
@CrossOrigin(value = {"*"})
public class ServiciosPaqueteController {
    private final ServiciosPaqueteServices services;

    public ServiciosPaqueteController(ServiciosPaqueteServices services) {
        this.services = services;
    }

    @GetMapping("/")
    public ResponseEntity<Response<List<ServiciosPaquete>>> getAll(){
        return new ResponseEntity<>(
                this.services.getAll(),
                HttpStatus.OK
        );
    }
    @GetMapping("/paginado/{page}/{size}")
    public ResponseEntity<Response<Page<ServiciosPaquete>>> getAllPaginado(@PathVariable("page") @NotNull Integer page, @PathVariable("size") @NotNull Integer size){
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return new ResponseEntity<>(
                this.services.getAll(pageable),
                HttpStatus.OK
        );
    }
    @GetMapping("/status/{status}")
    public ResponseEntity<Response<List<ServiciosPaquete>>> getAllByStatus(@PathVariable("status") @NotNull Boolean status){
        return new ResponseEntity<>(
                this.services.getAllByStatus(status),
                HttpStatus.OK
        );
    }

    @GetMapping("/status/{status}/paginado/{page}/{size}")
    public ResponseEntity<Response<Page<ServiciosPaquete>>>
    getAllByStatusPaginado(@PathVariable("status") @NotNull Boolean status, @PathVariable("page") @NotNull Integer page, @PathVariable("size") @NotNull Integer size){
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return new ResponseEntity<>(
                this.services.getAllByStatus(status, pageable),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<ServiciosPaquete>> getById(@PathVariable("id") @NotBlank String id){
        return new ResponseEntity<>(
                this.services.getById(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/paquete/{id}")
    public ResponseEntity<Response<List<ServiciosPaquete>>> getAllByIdPaquete(@PathVariable("id") @NotBlank String id){
        return new ResponseEntity<>(
                this.services.getAllByIdPaquete(id),
                HttpStatus.OK
        );
    }

    @PostMapping("/")
    public ResponseEntity<Response<ServiciosPaquete>> insert(@RequestBody @Valid ServiciosPaqueteDto dto){
        return new ResponseEntity<>(
                this.services.insert(dto.toEntity()),
                HttpStatus.OK
        );
    }
    @PutMapping("/")
    public ResponseEntity<Response<ServiciosPaquete>> update(@RequestBody @Valid ServiciosPaqueteDto dto){
        return new ResponseEntity<>(
                this.services.update(dto.toEntity()),
                HttpStatus.OK
        );
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Boolean>> delete(@PathVariable("id") @NotBlank String id){
        return new ResponseEntity<>(
                this.services.delete(id),
                HttpStatus.OK
        );
    }
    @DeleteMapping("/status/{id}")
    public ResponseEntity<Response<ServiciosPaquete>> changeStatus(@PathVariable("id") @NotBlank String id){
        return new ResponseEntity<>(
                this.services.changeStatus(id),
                HttpStatus.OK
        );
    }
}
