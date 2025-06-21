package mx.edu.utez.servicioEscolar.controllers.ventanilla;

import mx.edu.utez.servicioEscolar.dto.VentanillaDto;
import mx.edu.utez.servicioEscolar.models.ventanilla.Ventanilla;
import mx.edu.utez.servicioEscolar.services.VentanillaService;
import mx.edu.utez.servicioEscolar.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/ventanillas/")
@CrossOrigin(origins = {"*"})
public class VentanillaController {

    @Autowired
    private VentanillaService ventanillaService;

    //@PreAuthorize("hasRole('VENTANILLA')")
    @GetMapping("/")
    public ResponseEntity<CustomResponse<List<Ventanilla>>> getAll(){
        return new ResponseEntity<>(
                this.ventanillaService.getAll(),
                HttpStatus.OK
        );
    }

   @GetMapping("/getActive")
   public ResponseEntity<CustomResponse<List<Ventanilla>>>
   getAllActive(){
       return new ResponseEntity<>(
               this.ventanillaService.getAllActive(),
               HttpStatus.OK
       );
   }

    @GetMapping("/getAllInactive")
    public ResponseEntity<CustomResponse<List<Ventanilla>>>
    getAllInactive(){
        return new ResponseEntity<>(
                this.ventanillaService.getAllInactive(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Ventanilla>> getOne(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                this.ventanillaService.getOne(id),
                HttpStatus.OK
        );
    }

    @PostMapping("/")
    public ResponseEntity<CustomResponse<Ventanilla>> insert(
            @RequestBody @Valid VentanillaDto ventanillaDto,
            @Valid BindingResult result
    ){
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.ventanillaService.insert(ventanillaDto.getVentanilla()),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Ventanilla>> update(
            @RequestBody VentanillaDto ventanillaDto,
            @Valid BindingResult result
    ){
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.ventanillaService.update(ventanillaDto.getVentanilla()),
                HttpStatus.CREATED
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomResponse<Boolean>> enableOrDisable(
             @RequestBody
    VentanillaDto ventanillaDto
    ){
        return new ResponseEntity<>(
                this.ventanillaService.changeStatus(ventanillaDto.getVentanilla()),
                HttpStatus.OK
        );
    }

}
