package utez.edu.mx.foodster.controllers.roles;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.foodster.dtos.roles.RolesDto;
import utez.edu.mx.foodster.entities.roles.Roles;
import utez.edu.mx.foodster.services.roles.RolesServices;
import utez.edu.mx.foodster.utils.Response;

import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/roles")
@CrossOrigin(value = {"*"})
public class RolesController {
    private final RolesServices services;

    public RolesController(RolesServices services) {
        this.services = services;
    }

    @GetMapping("/")
    public ResponseEntity<Response<List<Roles>>> getAll() {
        return new ResponseEntity<>(this.services.getAll(), HttpStatus.OK);
    }


    @GetMapping("/{uid}")
    public ResponseEntity<Response<Roles>> getById(@PathVariable("uid") @NotNull String uid) {
        return new ResponseEntity<>(this.services.getById(uid), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Response<Roles>> insert(@RequestBody @Valid RolesDto rolesDto) {
        return new ResponseEntity<>(this.services.insert(rolesDto.toEntity()), HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<Response<Roles>> update(@RequestBody @Valid RolesDto rolesDto) {
        return new ResponseEntity<>(this.services.update(rolesDto.toEntity()), HttpStatus.OK);
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity<Response<Boolean>> delete(@PathVariable("uid") @NotBlank String uid) {
        return new ResponseEntity<>(this.services.delete(uid), HttpStatus.OK);
    }
}
