package mx.edu.utez.redre.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import mx.edu.utez.redre.dto.EstudiantesDto;
import mx.edu.utez.redre.models.estudiantes.Estudiantes;
import mx.edu.utez.redre.services.EstudiantesServices;
import mx.edu.utez.redre.utils.CustomResponse;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/redre/estudiante/")
@CrossOrigin(origins = {"*"})
public class EstudiantesController {
    @Autowired
    private EstudiantesServices service;

    @GetMapping("/getAll")
    public ResponseEntity<CustomResponse<List<Estudiantes>>> getAll() {
        return new ResponseEntity<>(
                this.service.getAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/")
    public ResponseEntity<CustomResponse<List<Estudiantes>>>
    getAllActive(){
        return new ResponseEntity<>(
                this.service.getAllActive(),
                HttpStatus.OK
        );
    }

    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<CustomResponse<Estudiantes>> getByMatricula(@PathVariable String matricula){
        return new ResponseEntity<>(
                this.service.getByMatricula(matricula),
                HttpStatus.OK
        );
    }

    @GetMapping("/asesor/{id}")
    public ResponseEntity<CustomResponse<List<Estudiantes>>>
    getAllByAsesor(@PathVariable("id") Long id){
        return new ResponseEntity<>(
                this.service.getAllByAsesor(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/asesor/{id}/hasReport")
    public ResponseEntity<CustomResponse<List<Estudiantes>>>
    findAllByAsesorAndUrlReporteStatus(@PathVariable("id") Long id){
        return new ResponseEntity<>(
                this.service.findAllByAsesorAndUrlReporteStatus(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/responsable/{id}/hasReport")
    public ResponseEntity<CustomResponse<List<Estudiantes>>>
    findAllByAsesor_ResponsableAndReportStatus(@PathVariable("id") Long id){
        return new ResponseEntity<>(
                this.service.findAllByAsesor_ResponsableAndReportStatus(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/departamento/{id}/hasReport")
    public ResponseEntity<CustomResponse<List<Estudiantes>>>
    findAllByAsesor_Responsable_DepartamentoAndReportStatus(@PathVariable("id") Long id){
        return new ResponseEntity<>(
                this.service.findAllByAsesor_Responsable_DepartamentoAndReportStatus(id),
                HttpStatus.OK
        );
    }

    @PostMapping("/")
    public ResponseEntity<CustomResponse<Estudiantes>> insert(
            @RequestBody @Valid EstudiantesDto estudianteDto, @Valid BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.service.insert(estudianteDto.getEstudiantes()),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Estudiantes>> getOne(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                this.service.getOne(id),
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Estudiantes>> update(
            @RequestBody EstudiantesDto estudianteDto, @Valid BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.service.update(estudianteDto.getEstudiantes()),
                HttpStatus.CREATED
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomResponse<Boolean>> enableOrDisable(
            @RequestBody EstudiantesDto estudianteDto) {
        return new ResponseEntity<>(
                this.service.changeStatus(estudianteDto.getEstudiantes()),
                HttpStatus.OK
        );
    }
}
