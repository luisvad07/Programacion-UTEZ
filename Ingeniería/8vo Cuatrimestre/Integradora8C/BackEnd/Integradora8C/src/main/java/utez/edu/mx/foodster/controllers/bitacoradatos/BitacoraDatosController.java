package utez.edu.mx.foodster.controllers.bitacoradatos;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.foodster.entities.bitacoradatos.BitacoraDatos;
import utez.edu.mx.foodster.services.bitacoradatos.BitacoraDatosServices;
import utez.edu.mx.foodster.utils.Response;

import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/bitacora")
@CrossOrigin(value = {"*"})
public class BitacoraDatosController {
    private final BitacoraDatosServices services;

    public BitacoraDatosController(BitacoraDatosServices services) {
        this.services = services;
    }

    @GetMapping("/")
    public ResponseEntity<Response<List<BitacoraDatos>>> getAll() {
        return new ResponseEntity<>(this.services.getAll(), HttpStatus.OK);
    }

    @GetMapping("/paginado/{page}/{size}")
    public ResponseEntity<Response<Page<BitacoraDatos>>> getAllPaginado(@PathVariable("page") @NotNull Integer page, @PathVariable("size") @NotNull Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(this.services.getAll(pageable), HttpStatus.OK);
    }

}
