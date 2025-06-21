package utez.edu.mx.foodster.controllers.personal;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.foodster.dtos.personal.PersonalDto;
import utez.edu.mx.foodster.entities.personal.Personal;
import utez.edu.mx.foodster.services.personal.PersonalServices;
import utez.edu.mx.foodster.utils.Response;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/personal")
@CrossOrigin(value = {"*"})
public class PersonalController {

    private final PersonalServices services;

    public PersonalController(PersonalServices services) {
        this.services = services;
    }

    @GetMapping("/")
    public ResponseEntity<Response<List<Personal>>> getAll() {
        return new ResponseEntity<>(this.services.getAll(), HttpStatus.OK);
    }

    @GetMapping("/paginado/{page}/{size}")
    public ResponseEntity<Response<Page<Personal>>> getAllPaginado(@PathVariable("page") @NotNull Integer page, @PathVariable("size") @NotNull Integer size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return new ResponseEntity<>(this.services.getAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/disponibles/{fechaHoraInicio}/{fechaHoraFin}")
    public ResponseEntity<Response<List<Personal>>> getAllDisponibles(@PathVariable("fechaHoraInicio") @NotNull Timestamp fechaHoraInicio, @PathVariable("fechaHoraFin") @NotNull Timestamp fechaHoraFin) {
        return new ResponseEntity<>(this.services.getAllDisponibles(fechaHoraInicio, fechaHoraFin), HttpStatus.OK);
    }

    @GetMapping("/{uid}")
    public ResponseEntity<Response<Personal>> getById(@PathVariable("uid") @NotBlank String uid) {
        return new ResponseEntity<>(this.services.getById(uid), HttpStatus.OK);
    }
    @GetMapping("/status/{status}")
    public ResponseEntity<Response<List<Personal>>> getAllByStatus(@PathVariable("status") @NotNull Boolean status) {
        return new ResponseEntity<>(this.services.getAllByStatus(status), HttpStatus.OK);
    }

    @GetMapping("/status/{status}/paginado/{page}/{size}")
    public ResponseEntity<Response<Page<Personal>>> getAllByStatusPaginado(@PathVariable("status") @NotNull Boolean status, @PathVariable("page") @NotNull Integer page, @PathVariable("size") @NotNull Integer size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return new ResponseEntity<>(this.services.getAllByStatus(status, pageable), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Response<Personal>> insert(@RequestBody @Valid PersonalDto personalDto) {
        return new ResponseEntity<>(this.services.insert(personalDto.toEntity()), HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<Response<Personal>> update(@RequestBody @Valid PersonalDto personalDto) {
        return new ResponseEntity<>(this.services.update(personalDto.toEntity()), HttpStatus.OK);
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity<Response<Boolean>> delete(@PathVariable("uid") @NotBlank String uid) {
        return new ResponseEntity<>(this.services.delete(uid), HttpStatus.OK);
    }
    @DeleteMapping("/status/{uid}")
    public ResponseEntity<Response<Boolean>> changeStatus(@PathVariable("uid") @NotBlank String uid) {
        return new ResponseEntity<>(this.services.changeStatus(uid), HttpStatus.OK);
    }
}
