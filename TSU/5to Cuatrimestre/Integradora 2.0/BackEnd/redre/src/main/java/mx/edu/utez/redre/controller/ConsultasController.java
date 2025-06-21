package mx.edu.utez.redre.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.utez.redre.models.consultas.Consultas;
import mx.edu.utez.redre.services.consulta.ConsultaServices;

@RestController
@RequestMapping("/redre/consultas/")
public class ConsultasController {
    @Autowired
    private ConsultaServices consultaService;

    @PostMapping("/")
    public ResponseEntity<Consultas> realizarConsulta(@RequestBody Consultas consulta) {
        Consultas nuevaConsulta = consultaService.crearConsulta(consulta);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaConsulta);
    }

    @GetMapping("/")
    public ResponseEntity<List<Consultas>> obtenerTodasLasConsultas() {
        List<Consultas> consultas = consultaService.obtenerConsultas();
        return ResponseEntity.ok(consultas);
    }
}
