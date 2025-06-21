package mx.edu.utez.servicioEscolar.controllers.admin;

import mx.edu.utez.servicioEscolar.dto.AdminDto;
import mx.edu.utez.servicioEscolar.models.admin.Admin;
import mx.edu.utez.servicioEscolar.services.AdminService;
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
@RequestMapping("/api/admin/")
@CrossOrigin(origins = {"*"})
public class AdminController {

    @Autowired
    private AdminService adminService;
    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<CustomResponse<List<Admin>>> getAll(){
        return new ResponseEntity<>(
                this.adminService.getAll(),
                HttpStatus.OK
        );
    }

   @GetMapping("/getActive")
   public ResponseEntity<CustomResponse<List<Admin>>>
   getAllActive(){
       return new ResponseEntity<>(
               this.adminService.getAllActive(),
               HttpStatus.OK
       );
   }

    @GetMapping("/getAllInactive")
    public ResponseEntity<CustomResponse<List<Admin>>>
    getAllInactive(){
        return new ResponseEntity<>(
                this.adminService.getAllInactive(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Admin>> getOne(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                this.adminService.getOne(id),
                HttpStatus.OK
        );
    }

    @PostMapping("/")
    public ResponseEntity<CustomResponse<Admin>> insert(
            @RequestBody @Valid AdminDto adminDto,
            @Valid BindingResult result
    ){
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.adminService.insert(adminDto.getAdmin()),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Admin>> update(
            @RequestBody AdminDto adminDto,
            @Valid BindingResult result
    ){
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.adminService.update(adminDto.getAdmin()),
                HttpStatus.CREATED
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomResponse<Boolean>> enableOrDisable(
             /*@Valid*/ @RequestBody AdminDto adminDto
    ){
        return new ResponseEntity<>(
                this.adminService.changeStatus(adminDto.getAdmin()),
                HttpStatus.OK
        );
    }

}
