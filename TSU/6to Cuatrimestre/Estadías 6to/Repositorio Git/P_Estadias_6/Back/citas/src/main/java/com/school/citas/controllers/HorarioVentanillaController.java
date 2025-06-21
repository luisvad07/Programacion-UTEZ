package com.school.citas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.school.citas.dtos.HorarioDto;
import com.school.citas.models.Horario.HorarioVentanilla;
import com.school.citas.services.HorarioVentanillaService;
import com.school.citas.utils.CustomResponse;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/horarios/")
@CrossOrigin(origins = {"*"})
public class HorarioVentanillaController {

    @Autowired
    private HorarioVentanillaService horarioVentanillaService;

    ///Obtener todos los horarios
    @GetMapping("/")
    public ResponseEntity<CustomResponse<List<HorarioVentanilla>>> getAll() {
        return new ResponseEntity<>(
                this.horarioVentanillaService.getAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/getActive")
    public ResponseEntity<CustomResponse<List<HorarioVentanilla>>>
    getAllActive(){
        return new ResponseEntity<>(
                this.horarioVentanillaService.getAllActive(),
                HttpStatus.OK
        );
    }

    @GetMapping("/getAllInactive")
    public ResponseEntity<CustomResponse<List<HorarioVentanilla>>>
    getAllInactive(){
        return new ResponseEntity<>(
                this.horarioVentanillaService.getAllInactive(),
                HttpStatus.OK
        );
    }

    //Obtener un registro de horario por id
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<HorarioVentanilla>> getOne(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                this.horarioVentanillaService.getOne(id),
                HttpStatus.OK
        );
    }

    //Insertar un horario
    @PostMapping("/")
    public ResponseEntity<CustomResponse<HorarioVentanilla>> insert(
            @RequestBody HorarioDto horarioDto, @Valid BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.horarioVentanillaService.insert(horarioDto.getHorario()),
                HttpStatus.CREATED
        );
    }

    //Modificar un horario
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<HorarioVentanilla>> update(
            @RequestBody HorarioDto horarioDto, @Valid BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.horarioVentanillaService.update(horarioDto.getHorario()),
                HttpStatus.CREATED
        );
    }

    //Modificar el status de una categoría
    @PatchMapping("/{id}")
    public ResponseEntity<CustomResponse<Boolean>> enableOrDisable(
            @RequestBody HorarioDto horarioDto) {
        return new ResponseEntity<>(
                this.horarioVentanillaService.changeStatus(horarioDto.getHorario()),
                HttpStatus.OK
        );
    }
}
