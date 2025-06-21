package utez.edu.mx.foodster.controllers.personalevento;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.foodster.dtos.personalevento.PersonalEventoDto;
import utez.edu.mx.foodster.entities.personalevento.PersonalEvento;
import utez.edu.mx.foodster.services.personalevento.PersonalEventoServices;
import utez.edu.mx.foodster.utils.Response;

import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/personal-evento")
@CrossOrigin(value = {"*"})
public class PersonalEventoController {
    private final PersonalEventoServices services;

    public PersonalEventoController(PersonalEventoServices services) {
        this.services = services;
    }

    @GetMapping("/")
    public ResponseEntity<Response<List<PersonalEvento>>> getAll() {
        return new ResponseEntity<>(this.services.getAll(), HttpStatus.OK);
    }

    @GetMapping("/paginado/{page}/{size}")
    public ResponseEntity<Response<Page<PersonalEvento>>> getAllPaginado(@PathVariable("page") @NotNull Integer page, @PathVariable("size") @NotNull Integer size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return new ResponseEntity<>(this.services.getAll(pageable), HttpStatus.OK);
    }
    @GetMapping("/{uid}")
    public ResponseEntity<Response<PersonalEvento>> getById(@PathVariable("uid") @NotBlank String uid) {
        return new ResponseEntity<>(this.services.getById(uid), HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Response<List<PersonalEvento>>> getAllByStatus(@PathVariable("status") @NotNull Boolean status) {
        return new ResponseEntity<>(this.services.getAllByStatus(status), HttpStatus.OK);
    }

    @GetMapping("/status/{status}/paginado/{page}/{size}")
    public ResponseEntity<Response<Page<PersonalEvento>>>
    getAllByStatusPaginado(@PathVariable("status") @NotNull Boolean status, @PathVariable("page") @NotNull Integer page, @PathVariable("size") @NotNull Integer size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return new ResponseEntity<>(this.services.getAllByStatus(status, pageable), HttpStatus.OK);
    }

    @GetMapping("/evento/{uid}")
    public ResponseEntity<Response<List<PersonalEvento>>> getAllByEvento(@PathVariable("uid") @NotBlank String uid) {
        return new ResponseEntity<>(this.services.getAllByIdEvento(uid), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Response<PersonalEvento>> insert(@RequestBody @Valid PersonalEventoDto personalEventoDto) {
        return new ResponseEntity<>(this.services.insert(personalEventoDto.toEntity()), HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<Response<PersonalEvento>> update(@RequestBody @Valid PersonalEventoDto personalEventoDto) {
        return new ResponseEntity<>(this.services.update(personalEventoDto.toEntity()), HttpStatus.OK);
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity<Response<Boolean>> delete(@PathVariable("uid") @NotBlank String uid) {
        return new ResponseEntity<>(this.services.delete(uid), HttpStatus.OK);
    }
}
