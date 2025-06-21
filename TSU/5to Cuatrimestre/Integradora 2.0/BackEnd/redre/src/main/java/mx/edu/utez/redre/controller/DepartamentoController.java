package mx.edu.utez.redre.controller;

import mx.edu.utez.redre.dto.DepartamentoDto;
import mx.edu.utez.redre.models.departamento.Departamento;
import mx.edu.utez.redre.services.DepartamentoServices;
import mx.edu.utez.redre.utils.CustomResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/redre/departamento/")
@CrossOrigin(origins = {"*"})
public class DepartamentoController {
    @Autowired
    private DepartamentoServices service;

    @GetMapping("/getAll")
    public ResponseEntity<CustomResponse<List<Departamento>>>
    getAll(){
        return new ResponseEntity<>(
                this.service.getAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/")
    public ResponseEntity<CustomResponse<List<Departamento>>>
    getAllActive(){
        return new ResponseEntity<>(
                this.service.getAllActive(),
                HttpStatus.OK
        );
    }

    @GetMapping("/getByDivision/{division}")
    public ResponseEntity<CustomResponse<List<Departamento>>>
    getAllByDivision(@PathVariable("division") String division){
        return new ResponseEntity<>(
                this.service.getByDivision(division),
                HttpStatus.OK
        );
    }

    @PostMapping("/")
    public ResponseEntity<CustomResponse<Departamento>> insert(
            @RequestBody @Valid DepartamentoDto departamento,
            @Valid BindingResult result
    ){
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.service.insert(departamento.getDepartamento()),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Departamento>> getOne(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                this.service.getOne(id),
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Departamento>> update(
            @RequestBody DepartamentoDto departamento,
            @Valid BindingResult result
    ){
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.service.update(departamento.getDepartamento()),
                HttpStatus.CREATED
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomResponse<Boolean>> enableOrDisable(
            @Valid @RequestBody DepartamentoDto departamento
    ) {
        return new ResponseEntity<>(
                this.service.changeStatus(departamento.getDepartamento()),
                HttpStatus.OK
        );
    }
}
