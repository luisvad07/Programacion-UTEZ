package mx.edu.utez.redre.controller;

import mx.edu.utez.redre.models.reportesFinales.ReportesFinales;
import mx.edu.utez.redre.services.ReportesFinalesService;
import mx.edu.utez.redre.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/redre/reportesFinales/")
@CrossOrigin({"*"})
public class ReportesFinalesController {
    @Autowired
    ReportesFinalesService service;

    @GetMapping("/get/{division}")
    public ResponseEntity<CustomResponse<List<ReportesFinales>>> getAllByDivisionAcademica(@PathVariable String division){
        return new ResponseEntity<>(
                this.service.getAllByDivisionAcademica(division),
                HttpStatus.OK
        );
    }

    @GetMapping("/get/{matricula}")
    public ResponseEntity<CustomResponse<ReportesFinales>> getReporteByMatricula(@PathVariable String matricula){
        return new ResponseEntity<>(
                this.service.getReporteByMatricula(matricula),
                HttpStatus.OK
        );
    }

    @GetMapping("/ing/{division}")
    public ResponseEntity<CustomResponse<List<ReportesFinales>>> getAllByDivisionAndReporteING(@PathVariable String division){
        return new ResponseEntity<>(
                this.service.getAllByDivisionAndReporteING(division),
                HttpStatus.OK
        );
    }
}
