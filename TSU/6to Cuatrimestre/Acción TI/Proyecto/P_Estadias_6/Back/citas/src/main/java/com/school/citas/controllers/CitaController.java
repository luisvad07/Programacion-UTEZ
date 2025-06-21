package com.school.citas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.school.citas.dtos.CitaDto;
import com.school.citas.models.Cita.Cita;
import com.school.citas.services.CitaService;
import com.school.citas.utils.CustomResponse;

import java.util.List;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/citas/")
@CrossOrigin(origins = {"*"})
public class CitaController {
    @Autowired
    private CitaService citaService;

    @GetMapping("/")
    public ResponseEntity<CustomResponse<List<Cita>>> getAll(){
        return new ResponseEntity<>(
                this.citaService.getAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/getActive")
    public ResponseEntity<CustomResponse<List<Cita>>>
    getAllActive(){
        return new ResponseEntity<>(
                this.citaService.getAllActive(),
                HttpStatus.OK
        );
    }

    @GetMapping("/getAllInactive")
    public ResponseEntity<CustomResponse<List<Cita>>>
    getAllInactive(){
        return new ResponseEntity<>(
                this.citaService.getAllInactive(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Cita>> getOne(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                this.citaService.getOne(id),
                HttpStatus.OK
        );
    }

    @PostMapping("/")
    public ResponseEntity<CustomResponse<Cita>> insert(@RequestBody @Valid CitaDto citaDto, @Valid BindingResult result)
            throws MessagingException{
        if (result.hasErrors()){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>( this.citaService.insert(citaDto.getCita()),HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Cita>> update(@RequestBody CitaDto citadto,@Valid BindingResult result){
        if (result.hasErrors()){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(this.citaService.update(citadto.getCita()),HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomResponse<Boolean>> enableOrDisable(
            @RequestBody CitaDto citaDto
    ){
        return new ResponseEntity<>(
                this.citaService.changeStatus(citaDto.getCita()),
                HttpStatus.OK
        );
    }
}
