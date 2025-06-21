package com.imagen_social.mac_morelos_api.controllers.incidents;

import com.imagen_social.mac_morelos_api.enums.statusIncidents.StatusIncidents;
import com.imagen_social.mac_morelos_api.models.incidents.Incident;
import com.imagen_social.mac_morelos_api.models.users.User;
import com.imagen_social.mac_morelos_api.services.incidents.IncidentsService;
import com.imagen_social.mac_morelos_api.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/incidents")
@CrossOrigin(value = {"*"})
public class IncidentsController {
    
    @Autowired
    private IncidentsService incidentsService;

    // Obtener todos los incidentes
    @GetMapping("/getAll")
    public ResponseEntity<Response<List<Incident>>> getAllIncidents() {
        Response<List<Incident>> response = incidentsService.getAllIncidents();
        return ResponseEntity.status(HttpStatus.valueOf(response.getStatus())).body(response);
    }

    // Obtener un incidente por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Response<Incident>> getIncidentById(@PathVariable Long id) {
        Response<Incident> response = incidentsService.getIncidentById(id);
        return ResponseEntity.status(HttpStatus.valueOf(response.getStatus())).body(response);
    }

    // Crear un incidente
    @PostMapping("/createIncident")
    public ResponseEntity<Response<Incident>> createIncident(@RequestBody Incident incident) {
        Response<Incident> response = incidentsService.createIncident(incident);
        return ResponseEntity.status(HttpStatus.valueOf(response.getStatus())).body(response);
    }

    // Actualizar un incidente
    @PutMapping("/{id}")
    public ResponseEntity<Response<Incident>> updateIncident(@PathVariable Long id, @RequestBody Incident incidentDetails) {
        Response<Incident> response = incidentsService.updateIncident(id, incidentDetails);
        return ResponseEntity.status(HttpStatus.valueOf(response.getStatus())).body(response);
    }

    // Eliminar un incidente
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteIncident(@PathVariable Long id) {
        Response<Void> response = incidentsService.deleteIncident(id);
        return ResponseEntity.status(HttpStatus.valueOf(response.getStatus())).body(response);
    }

    // Buscar incidentes por promotor
    @GetMapping("/promoter")
    public ResponseEntity<Response<List<Incident>>> getIncidentsByPromoter(@RequestBody User promoter) {
        Response<List<Incident>> response = incidentsService.getIncidentsByPromoter(promoter);
        return ResponseEntity.status(HttpStatus.valueOf(response.getStatus())).body(response);
    }

    // Buscar incidentes por usuario asignado
    @GetMapping("/assigned-to")
    public ResponseEntity<Response<List<Incident>>> getIncidentsByAssignedTo(@RequestBody User assignedTo) {
        Response<List<Incident>> response = incidentsService.getIncidentsByAssignedTo(assignedTo);
        return ResponseEntity.status(HttpStatus.valueOf(response.getStatus())).body(response);
    }

    // Buscar incidentes por estado
    @GetMapping("/status")
    public ResponseEntity<Response<List<Incident>>> getIncidentsByStatus(@RequestParam StatusIncidents statusIncident) {
        Response<List<Incident>> response = incidentsService.getIncidentsByStatus(statusIncident);
        return ResponseEntity.status(HttpStatus.valueOf(response.getStatus())).body(response);
    }

    // Buscar incidentes creados antes de una fecha específica
    @GetMapping("/created-before")
    public ResponseEntity<Response<List<Incident>>> getIncidentsByCreatedAtBefore(@RequestParam Timestamp createdAt) {
        Response<List<Incident>> response = incidentsService.getIncidentsByCreatedAtBefore(createdAt);
        return ResponseEntity.status(HttpStatus.valueOf(response.getStatus())).body(response);
    }

    // Buscar incidentes resueltos después de una fecha específica
    @GetMapping("/resolved-after")
    public ResponseEntity<Response<List<Incident>>> getIncidentsByResolvedAtAfter(@RequestParam Timestamp resolvedAt) {
        Response<List<Incident>> response = incidentsService.getIncidentsByResolvedAtAfter(resolvedAt);
        return ResponseEntity.status(HttpStatus.valueOf(response.getStatus())).body(response);
    }

    // Buscar incidentes por promotor y estado
    @GetMapping("/promoter-status")
    public ResponseEntity<Response<List<Incident>>> getIncidentsByPromoterAndStatus(@RequestBody User promoter, @RequestParam StatusIncidents statusIncident) {
        Response<List<Incident>> response = incidentsService.getIncidentsByPromoterAndStatus(promoter, statusIncident);
        return ResponseEntity.status(HttpStatus.valueOf(response.getStatus())).body(response);
    }

    // Verificar si existe un incidente asignado a un usuario y con un estado específico
    @GetMapping("/exists-assigned-status")
    public ResponseEntity<Response<Boolean>> existsIncidentByAssignedToAndStatus(@RequestBody User assignedTo, @RequestParam StatusIncidents statusIncident) {
        Response<Boolean> response = incidentsService.existsIncidentByAssignedToAndStatus(assignedTo, statusIncident);
        return ResponseEntity.status(HttpStatus.valueOf(response.getStatus())).body(response);
    }
}
