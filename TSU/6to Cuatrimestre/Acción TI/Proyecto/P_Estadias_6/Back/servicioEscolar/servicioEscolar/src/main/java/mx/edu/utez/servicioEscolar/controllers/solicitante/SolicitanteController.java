package mx.edu.utez.servicioEscolar.controllers.solicitante;

import mx.edu.utez.servicioEscolar.dto.SolicitanteDto;
import mx.edu.utez.servicioEscolar.models.solicitante.Solicitante;
import mx.edu.utez.servicioEscolar.services.SolicitanteService;
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
@RequestMapping("/api/solicitantes/")
@CrossOrigin(origins = {"*"})
public class SolicitanteController {

    @Autowired
    private SolicitanteService solicitanteService;

    //@PreAuthorize("hasRole('SOLICITANTE')")
    @GetMapping("/")
    public ResponseEntity<CustomResponse<List<Solicitante>>> getAll(){
        return new ResponseEntity<>(
                this.solicitanteService.getAll(),
                HttpStatus.OK
        );
    }

   @GetMapping("/getActive")
   public ResponseEntity<CustomResponse<List<Solicitante>>>
   getAllActive(){
       return new ResponseEntity<>(
               this.solicitanteService.getAllActive(),
               HttpStatus.OK
       );
   }

    @GetMapping("/getAllInactive")
    public ResponseEntity<CustomResponse<List<Solicitante>>>
    getAllInactive(){
        return new ResponseEntity<>(
                this.solicitanteService.getAllInactive(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Solicitante>> getOne(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                this.solicitanteService.getOne(id),
                HttpStatus.OK
        );
    }

    @PostMapping("/")
    public ResponseEntity<CustomResponse<Solicitante>> insert(
            @RequestBody @Valid SolicitanteDto solicitanteDto,
            @Valid BindingResult result
    ){
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.solicitanteService.insert(solicitanteDto.getSolicitante()),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Solicitante>> update(
            @RequestBody SolicitanteDto solicitanteDto,
            @Valid BindingResult result
    ){
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.solicitanteService.update(solicitanteDto.getSolicitante()),
                HttpStatus.CREATED
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomResponse<Boolean>> enableOrDisable(
             @RequestBody
    SolicitanteDto solicitanteDto
    ){
        return new ResponseEntity<>(
                this.solicitanteService.changeStatus(solicitanteDto.getSolicitante()),
                HttpStatus.OK
        );
    }

}
