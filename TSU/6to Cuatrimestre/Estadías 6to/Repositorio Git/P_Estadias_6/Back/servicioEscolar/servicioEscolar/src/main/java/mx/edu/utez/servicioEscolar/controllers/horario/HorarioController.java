package mx.edu.utez.servicioEscolar.controllers.horario;

import mx.edu.utez.servicioEscolar.dto.HorarioDto;
import mx.edu.utez.servicioEscolar.models.horario.Horario;
import mx.edu.utez.servicioEscolar.models.horario.HorarioRepository;
import mx.edu.utez.servicioEscolar.services.HorarioService;
import mx.edu.utez.servicioEscolar.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/horarios/")
@CrossOrigin(origins = {"*"})
public class HorarioController {

    @Autowired
    private HorarioService horarioService;

    ///Obtener todos los horarios
    @GetMapping("/getAll")
    public ResponseEntity<CustomResponse<List<Horario>>> getAll() {
        return new ResponseEntity<>(
                this.horarioService.getAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/getActive")
    public ResponseEntity<CustomResponse<List<Horario>>>
    getAllActive(){
        return new ResponseEntity<>(
                this.horarioService.getAllActive(),
                HttpStatus.OK
        );
    }

    @GetMapping("/getAllInactive")
    public ResponseEntity<CustomResponse<List<Horario>>>
    getAllInactive(){
        return new ResponseEntity<>(
                this.horarioService.getAllInactive(),
                HttpStatus.OK
        );
    }

    //Obtener un registro de horario por id
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Horario>> getOne(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                this.horarioService.getOne(id),
                HttpStatus.OK
        );
    }

    //Insertar un horario
    @PostMapping("/")
    public ResponseEntity<CustomResponse<Horario>> insert(
            @RequestBody HorarioDto horarioDto, @Valid BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.horarioService.insert(horarioDto.getHorario()),
                HttpStatus.CREATED
        );
    }

    //Modificar un horario
    @PutMapping("/")
    public ResponseEntity<CustomResponse<Horario>> update(
            @RequestBody HorarioDto horarioDto, @Valid BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.horarioService.update(horarioDto.getHorario()),
                HttpStatus.CREATED
        );
    }

    //Modificar el status de una categoría
    @PatchMapping("/")
    public ResponseEntity<CustomResponse<Boolean>> enableOrDisable(
            @RequestBody HorarioDto horarioDto) {
        return new ResponseEntity<>(
                this.horarioService.changeStatus(horarioDto.getHorario()),
                HttpStatus.OK
        );
    }


}
