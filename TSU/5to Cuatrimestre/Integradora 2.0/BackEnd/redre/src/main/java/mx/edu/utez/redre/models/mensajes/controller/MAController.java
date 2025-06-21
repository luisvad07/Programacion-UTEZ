package mx.edu.utez.redre.models.mensajes.controller;

import java.net.URI;
import java.util.List;

import mx.edu.utez.redre.models.estudiantes.Estudiantes;
import mx.edu.utez.redre.models.estudiantes.EstudiantesRespository;
import mx.edu.utez.redre.services.EstudiantesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import mx.edu.utez.redre.models.mensajes.models.mensajeAsesor.MensajeAsesor;
import mx.edu.utez.redre.models.mensajes.services.MAServices;

@RestController
@RequestMapping("/redre/mensajesAsesor/")
@CrossOrigin({"*"})
public class MAController {
    @Autowired
    private MAServices mensajeService;
    @Autowired
    private EstudiantesServices estudiantesServices;
    @Autowired
    private EstudiantesRespository estudiantesRespository;

    @PostMapping("/As/")
    public ResponseEntity<MensajeAsesor> enviarMensaje(@RequestBody MensajeAsesor mensaje) {
        MensajeAsesor nuevoMensaje = mensajeService.crearMensaje(mensaje);
        Estudiantes estudiante = estudiantesRespository.getEstudiantesByMatricula(nuevoMensaje.getNombre());
        estudiante.setReportStatus("rechazadoPorAsesor");
        estudiantesServices.update(estudiante);
        return ResponseEntity.created(URI.create("/mensajes/" + nuevoMensaje.getId())).body(nuevoMensaje);
    }

    @PutMapping("/aprobado/{matricula}")
    public ResponseEntity<String> aprobar(@PathVariable String matricula){
        Estudiantes estudiante = estudiantesRespository.getEstudiantesByMatricula(matricula);
        estudiante.setReportStatus("aprobadoPorAsesor");
        estudiantesServices.update(estudiante);
        return ResponseEntity.ok("Reporte Aprobado");
    }

    @GetMapping("/{asesor}")
    public ResponseEntity<List<MensajeAsesor>> buscarMensajesPorReceptor(@PathVariable String asesor) {
        List<MensajeAsesor> mensaje = mensajeService.obtenerMensajePorNombre(asesor);
        if (mensaje == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mensaje);
    }

    @GetMapping("/estudiante/{matricula}")
    public ResponseEntity<List<MensajeAsesor>> buscarMensajePorMatricula(@PathVariable String matricula) {
        List<MensajeAsesor> mensaje = mensajeService.obtenerPorMatricula(matricula);
        if (mensaje == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mensaje);
    }
}
