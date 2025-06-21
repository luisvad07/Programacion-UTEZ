package mx.edu.utez.redre.upload;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.utez.redre.models.reportes.FilesMostrar.FilesDtos;

@RestController
@RequestMapping("/redremovil/archivos")
public class FilesController {

    @Autowired
    private FilesServices filesService;

    @PostMapping("/")
    public ResponseEntity<FilesDtos> realizarConsulta(@RequestBody FilesDtos consulta) {
        FilesDtos nuevaConsulta = filesService.crearRegistroMovil(consulta);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaConsulta);
    }

    @GetMapping("/{carrera}")
    public ResponseEntity<List<FilesDtos>> buscarMensajesPorReceptor(@PathVariable String carrera) {
        List<FilesDtos> mensaje = filesService.obtenerReportesporCarrera(carrera);
        return ResponseEntity.ok(mensaje);
    }
}
