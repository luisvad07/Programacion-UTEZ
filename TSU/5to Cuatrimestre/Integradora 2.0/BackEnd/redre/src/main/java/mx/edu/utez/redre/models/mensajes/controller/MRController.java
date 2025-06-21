package mx.edu.utez.redre.models.mensajes.controller;

import java.net.URI;
import java.util.List;

import mx.edu.utez.redre.models.estudiantes.Estudiantes;
import mx.edu.utez.redre.models.estudiantes.EstudiantesRespository;
import mx.edu.utez.redre.models.mensajes.models.mensajeAsesor.MensajeAsesor;
import mx.edu.utez.redre.services.EstudiantesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import mx.edu.utez.redre.models.mensajes.models.mensajeResponsable.MensajeResponsable;
import mx.edu.utez.redre.models.mensajes.services.MRServices;

@RestController
@RequestMapping("/redre/mensajesResponsables/")
@CrossOrigin({"*"})
public class MRController {
    @Autowired
    private MRServices mensajeService;

    @Autowired
    private EstudiantesServices estudiantesServices;
    @Autowired
    private EstudiantesRespository estudiantesRespository;

    @PostMapping("/Rs")
    public ResponseEntity<MensajeResponsable> enviarMensaje(@RequestBody MensajeResponsable mensaje) {
        MensajeResponsable nuevoMensaje = mensajeService.crearMensaje(mensaje);
        Estudiantes estudiante = estudiantesRespository.getEstudiantesByMatricula(nuevoMensaje.getNombre());
        estudiante.setReportStatus("rechazadoPorResponsable");
        estudiantesServices.update(estudiante);
        return ResponseEntity.created(URI.create("/mensajes/" + nuevoMensaje.getId())).body(nuevoMensaje);
    }

    @PutMapping("/aprobado/{matricula}")
    public ResponseEntity<String> aprobar(@PathVariable String matricula){
        Estudiantes estudiante = estudiantesRespository.getEstudiantesByMatricula(matricula);
        estudiante.setReportStatus("aprobadoPorResponsable");
        estudiantesServices.update(estudiante);
        return ResponseEntity.ok("Reporte Aprobado");
    }

    @GetMapping("/{responsable}")
    public ResponseEntity<List<MensajeResponsable>> buscarMensajesPorReceptor(@PathVariable String responsable) {
        List<MensajeResponsable> mensaje = mensajeService.obtenerMensajePorNombre(responsable);
        if (mensaje == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mensaje);
    }

    @GetMapping("/estudiante/{matricula}")
    public ResponseEntity<List<MensajeResponsable>> buscarMensajePorMatricula(@PathVariable String matricula) {
        List<MensajeResponsable> mensaje = mensajeService.obtenerMensajePorNombre(matricula);
        if (mensaje == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mensaje);
    }
}