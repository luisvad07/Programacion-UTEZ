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

import mx.edu.utez.redre.models.mensajes.models.mensajeDepartamento.MensajeDepartamento;
import mx.edu.utez.redre.models.mensajes.services.MDServices;

@RestController
@RequestMapping("/redre/mensajesDepartamento/")
@CrossOrigin({"*"})
public class MDController {
    @Autowired
    private MDServices mensajeService;

    @Autowired
    private EstudiantesRespository estudiantesRespository;

    @Autowired
    private EstudiantesServices estudiantesServices;

    @PostMapping("/Dp/")
    public ResponseEntity<MensajeDepartamento> enviarMensaje(@RequestBody MensajeDepartamento mensaje) {
        MensajeDepartamento nuevoMensaje = mensajeService.crearMensaje(mensaje);
        Estudiantes estudiante = estudiantesRespository.getEstudiantesByMatricula(nuevoMensaje.getNombre());
        estudiante.setReportStatus("rechazadoPorDepartamento");
        estudiantesServices.update(estudiante);
        return ResponseEntity.created(URI.create("/mensajes/" + nuevoMensaje.getId())).body(nuevoMensaje);
    }

    @PutMapping("/aprobado/{matricula}")
    public ResponseEntity<String> aprobar(@PathVariable String matricula){
        Estudiantes estudiante = estudiantesRespository.getEstudiantesByMatricula(matricula);
        estudiante.setReportStatus(null);
        estudiante.setUrlReporte(null);
        estudiante.setGrado(estudiante.getGrado() + 1);
        estudiantesServices.update(estudiante);
        return ResponseEntity.ok("Reporte Aprobado");
    }

    @GetMapping("/{departamento}")
    public ResponseEntity<List<MensajeDepartamento>> buscarMensajesPorReceptor(@PathVariable String departamento) {
        List<MensajeDepartamento> mensaje = mensajeService.obtenerMensajePorNombre(departamento);
        if (mensaje == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mensaje);
    }

    @GetMapping("/estudiante/{matricula}")
    public ResponseEntity<List<MensajeDepartamento>> buscarMensajePorMatricula(@PathVariable String matricula) {
        List<MensajeDepartamento> mensaje = mensajeService.obtenerMensajePorNombre(matricula);
        if (mensaje == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mensaje);
    }
}
