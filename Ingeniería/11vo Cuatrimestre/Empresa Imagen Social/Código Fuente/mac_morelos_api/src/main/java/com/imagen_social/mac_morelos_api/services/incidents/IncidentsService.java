package com.imagen_social.mac_morelos_api.services.incidents;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imagen_social.mac_morelos_api.enums.roles.RoleEnum;
import com.imagen_social.mac_morelos_api.enums.statusIncidents.StatusIncidents;
import com.imagen_social.mac_morelos_api.models.incidents.Incident;
import com.imagen_social.mac_morelos_api.models.incidents.IncidentRepository;
import com.imagen_social.mac_morelos_api.models.users.User;
import com.imagen_social.mac_morelos_api.models.users.UserRepository;
import com.imagen_social.mac_morelos_api.utils.Response;

@Service
public class IncidentsService {

    @Autowired
    private IncidentRepository incidentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // Obtener todos los incidentes
    @Transactional(readOnly = true)
    public Response<List<Incident>> getAllIncidents() {
        List<Incident> incidents = incidentRepository.findAll();
        return new Response<>(incidents, false, 200, "Incidentes obtenidos exitosamente");
    }

    // Obtener un incidente por su ID
    @Transactional(readOnly = true)
    public Response<Incident> getIncidentById(Long id) {
        return incidentRepository.findById(id)
                .map(incident -> new Response<>(incident, false, 200, "Incidente encontrado"))
                .orElse(new Response<>(null, true, 404, "Incidente no encontrado"));
    }

    // Crear un incidente
    @Transactional
    public Response<Incident> createIncident(Incident incident) {

        // Cargar el promotor desde la BD
        User promoter = userRepository.findById(incident.getPromoter().getUserId())
            .orElse(null);
        
        // Validar si el promotor existe
        if (promoter == null) {
            return new Response<>(null, false, 404, "Promotor no encontrado.");
        }

        // Cargar el administrador desde la BD
        User assignedTo = userRepository.findById(incident.getAssignedTo().getUserId())
            .orElse(null);
        
        // Validar si el administrador existe
        if (assignedTo == null) {
            return new Response<>(null, false, 404, "Administrador no encontrado.");
        }

        // Validar rol del promotor
        if (promoter.getRole() == null || !promoter.getRole().getName().equals(RoleEnum.PROMOTOR)) {
            return new Response<>(null, false, 400, "El usuario debe tener el rol de PROMOTOR.");
        }

        // Validar rol del administrador
        if (assignedTo.getRole() == null || !assignedTo.getRole().getName().equals(RoleEnum.ADMINISTRADOR)) {
            return new Response<>(null, false, 400, "El usuario asignado debe tener el rol de ADMINISTRADOR.");
        }

        // Establecer la hora de creación manualmente
        incident.setCreatedAt(Timestamp.from(Instant.now()));
        Incident savedIncident = incidentRepository.save(incident);

        // Enviar notificación al Administrador
        Long adminId = incident.getAssignedTo().getUserId();
        messagingTemplate.convertAndSend("/queue/admin-" + adminId,  "Nuevo incidente creado: " + incident.getIncidentName());

        return new Response<>(savedIncident, false, 201, "Incidente creado con éxito");
    }

    // Actualizar un incidente
    @Transactional
    public Response<Incident> updateIncident(Long id, Incident incidentDetails) {
        Optional<Incident> existingIncident = incidentRepository.findById(id);
        if (existingIncident.isPresent()) {
            Incident incident = existingIncident.get();

            StatusIncidents existingStatus = incident.getStatusIncident();
            StatusIncidents newStatus = incidentDetails.getStatusIncident();

            // Validar la transición de estado
            if (!isValidStatusTransition(existingStatus, newStatus)) {
                return new Response<>(null, true, 400, "Transición de estado inválida: No se puede cambiar de " + existingStatus + " a " + newStatus);
            }

            incident.setStatusIncident(newStatus);

            // Si el estado es RESUELTO, actualizar la fecha de resolución
            if (newStatus == StatusIncidents.RESUELTO) {
                incident.setResolvedAt(incidentDetails.getResolvedAt());
            }
            
            Incident updatedIncident = incidentRepository.save(incident);

            // 🔴 Notificar al Promotor específico
            Long promoterId = incident.getPromoter().getUserId();
            messagingTemplate.convertAndSend("/queue/promoter-" + promoterId, 
                "El incidente '" + incident.getIncidentName() + "' ha sido actualizado a " + newStatus);

            return new Response<>(updatedIncident, false, 200, "Incidente actualizado con éxito");
        }
        return new Response<>(null, true, 404, "Incidente no encontrado");
    }

    private boolean isValidStatusTransition(StatusIncidents existingStatus, StatusIncidents newStatus) {
        if (existingStatus == newStatus) {
            return true; // Permitir mantener el mismo estado
        }

        switch (existingStatus) {
            case PENDIENTE:
                return newStatus == StatusIncidents.EN_PROGRESO || newStatus == StatusIncidents.CANCELADO;
            case EN_PROGRESO:
                return newStatus == StatusIncidents.RESUELTO || newStatus == StatusIncidents.NO_RESUELTO || newStatus == StatusIncidents.CANCELADO;
            case RESUELTO:
                return false; // No se puede cambiar de RESUELTO a otro estado (a menos que se reabra)
            case NO_RESUELTO:
                return newStatus == StatusIncidents.RESUELTO; // Podría intentar resolverse nuevamente
            case CANCELADO:
                return false; // No se puede cambiar de CANCELADO (a menos que se permita reabrir)
            default:
                return false;
        }
    }

    // Eliminar un incidente
    @Transactional
    public Response<Void> deleteIncident(Long id) {
        if (incidentRepository.existsById(id)) {
            incidentRepository.deleteById(id);
            return new Response<>(null, false, 200, "Incidente eliminado con éxito");
        }
        return new Response<>(null, true, 404, "Incidente no encontrado");
    }

    // Buscar incidentes por promotor
    @Transactional(readOnly = true)
    public Response<List<Incident>> getIncidentsByPromoter(User promoter) {
        List<Incident> incidents = incidentRepository.findByPromoter(promoter);
        return new Response<>(incidents, false, 200, "Incidentes encontrados");
    }

    // Buscar incidentes por usuario asignado
    @Transactional(readOnly = true)
    public Response<List<Incident>> getIncidentsByAssignedTo(User assignedTo) {
        List<Incident> incidents = incidentRepository.findByAssignedTo(assignedTo);
        return new Response<>(incidents, false, 200, "Incidentes encontrados");
    }

    // Buscar incidentes por estado
    @Transactional(readOnly = true)
    public Response<List<Incident>> getIncidentsByStatus(StatusIncidents statusIncident) {
        List<Incident> incidents = incidentRepository.findByStatusIncident(statusIncident);
        return new Response<>(incidents, false, 200, "Incidentes encontrados");
    }

    // Buscar incidentes creados antes de una fecha específica
    @Transactional(readOnly = true)
    public Response<List<Incident>> getIncidentsByCreatedAtBefore(Timestamp createdAt) {
        List<Incident> incidents = incidentRepository.findByCreatedAtBefore(createdAt);
        return new Response<>(incidents, false, 200, "Incidentes encontrados");
    }

    // Buscar incidentes resueltos después de una fecha específica
    @Transactional(readOnly = true)
    public Response<List<Incident>> getIncidentsByResolvedAtAfter(Timestamp resolvedAt) {
        List<Incident> incidents = incidentRepository.findByResolvedAtAfter(resolvedAt);
        return new Response<>(incidents, false, 200, "Incidentes encontrados");
    }

    // Buscar incidentes por promotor y estado
    @Transactional(readOnly = true)
    public Response<List<Incident>> getIncidentsByPromoterAndStatus(User promoter, StatusIncidents statusIncident) {
        List<Incident> incidents = incidentRepository.findByPromoterAndStatusIncident(promoter, statusIncident);
        return new Response<>(incidents, false, 200, "Incidentes encontrados");
    }

    // Verificar si existe un incidente asignado a un usuario y con un estado específico
    @Transactional(readOnly = true)
    public Response<Boolean> existsIncidentByAssignedToAndStatus(User assignedTo, StatusIncidents statusIncident) {
        boolean exists = incidentRepository.existsByAssignedToAndStatusIncident(assignedTo, statusIncident);
        return new Response<>(exists, false, 200, exists ? "Incidente encontrado" : "Incidente no encontrado");
    }
}

