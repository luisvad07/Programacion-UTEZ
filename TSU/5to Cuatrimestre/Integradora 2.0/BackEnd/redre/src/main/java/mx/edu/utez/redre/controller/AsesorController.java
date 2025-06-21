package mx.edu.utez.redre.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import mx.edu.utez.redre.dto.AsesorDto;
import mx.edu.utez.redre.models.asesor.Asesor;
import mx.edu.utez.redre.services.AsesorServices;
import mx.edu.utez.redre.utils.CustomResponse;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/redre/asesor/")
@CrossOrigin(origins = {"*"})
public class AsesorController {
    @Autowired
    private AsesorServices service;

    @GetMapping("/getAll")
    public ResponseEntity<CustomResponse<List<Asesor>>> getAll() {
        return new ResponseEntity<>(
                this.service.getAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/")
    public ResponseEntity<CustomResponse<List<Asesor>>>
    getAllActive(){
        return new ResponseEntity<>(
                this.service.getAllActive(),
                HttpStatus.OK
        );
    }

    @PostMapping("/")
    public ResponseEntity<CustomResponse<Asesor>> insert(
            @RequestBody @Valid AsesorDto asesorDto, @Valid BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.service.insert(asesorDto.getAsesor()),
                HttpStatus.CREATED
        );
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Asesor>> getOne(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                this.service.getOne(id),
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Asesor>> update(
            @RequestBody AsesorDto asesorDtoDto, @Valid BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.service.update(asesorDtoDto.getAsesor()),
                HttpStatus.CREATED
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomResponse<Boolean>> enableOrDisable(
            @RequestBody AsesorDto asesorDto) {
        return new ResponseEntity<>(
                this.service.changeStatus(asesorDto.getAsesor()),
                HttpStatus.OK
        );
    }
}
