package mx.edu.utez.redre.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import mx.edu.utez.redre.dto.ResponsablesDto;
import mx.edu.utez.redre.models.responsables.Responsables;
import mx.edu.utez.redre.services.ResponsablesServices;
import mx.edu.utez.redre.utils.CustomResponse;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/redre/responsable/")
@CrossOrigin(origins = {"*"})
public class ResponsablesController {
    @Autowired
    private ResponsablesServices service;

    @GetMapping("/getAll")
    public ResponseEntity<CustomResponse<List<Responsables>>> getAll() {
        return new ResponseEntity<>(
                this.service.getAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/")
    public ResponseEntity<CustomResponse<List<Responsables>>>
    getAllActive(){
        return new ResponseEntity<>(
                this.service.getAllActive(),
                HttpStatus.OK
        );
    }

    @PostMapping("/")
    public ResponseEntity<CustomResponse<Responsables>> insert(
            @RequestBody @Valid ResponsablesDto responsableDto, @Valid BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.service.insert(responsableDto.getResponsable()),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Responsables>> getOne(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                this.service.getOne(id),
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Responsables>> update(
            @RequestBody ResponsablesDto responsableDto, @Valid BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.service.update(responsableDto.getResponsable()),
                HttpStatus.CREATED
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomResponse<Boolean>> enableOrDisable(
            @RequestBody ResponsablesDto responsableDto) {
        return new ResponseEntity<>(
                this.service.changeStatus(responsableDto.getResponsable()),
                HttpStatus.OK
        );
    }
}
