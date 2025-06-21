package com.imagen_social.mac_morelos_api.controllers.eventsRegistrations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.imagen_social.mac_morelos_api.enums.statusEvents.StatusRegistrationEvent;
import com.imagen_social.mac_morelos_api.models.events.Event;
import com.imagen_social.mac_morelos_api.models.eventsRegistrations.EventRegistration;
import com.imagen_social.mac_morelos_api.models.users.User;
import com.imagen_social.mac_morelos_api.services.eventsRegistrations.EventRegistrationService;
import com.imagen_social.mac_morelos_api.utils.Response;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/event-registrations")
@CrossOrigin(value = {"*"})
public class EventsRegistrationsController {

    @Autowired
    private EventRegistrationService eventRegistrationService;

    // Obtener todas las inscripciones
    @GetMapping("/getAll")
    public ResponseEntity<Response<List<EventRegistration>>> getAllEventRegistrations() {
        Response<List<EventRegistration>> response = eventRegistrationService.getAllEventRegistrations();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    // Obtener una inscripción por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Response<EventRegistration>> getEventRegistrationById(@PathVariable Long id) {
        Response<EventRegistration> response = eventRegistrationService.getEventRegistrationById(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    // Crear una inscripción
    @PostMapping("/createEventInscription")
    public ResponseEntity<Response<EventRegistration>> createEventRegistration(@RequestBody EventRegistration eventRegistration) {
        Response<EventRegistration> response = eventRegistrationService.createEventRegistration(eventRegistration);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @PutMapping("/updateRegistrationStatus/{id}")
    public ResponseEntity<Response<EventRegistration>> updateRegistrationStatus(
            @PathVariable Long id,
            @RequestBody EventRegistration incomingRegistration) {

        Response<EventRegistration> response = eventRegistrationService.updateStatus(id, incomingRegistration.getStatusRegisterEvent());
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    // Buscar inscripciones por evento
    @GetMapping("/event/{eventId}")
    public ResponseEntity<Response<List<EventRegistration>>> getEventRegistrationsByEvent(@PathVariable Event event) {
        Response<List<EventRegistration>> response = eventRegistrationService.getEventRegistrationsByEvent(event);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    // Buscar inscripciones por usuario
    @GetMapping("/user/{userId}")
    public ResponseEntity<Response<List<EventRegistration>>> getEventRegistrationsByUser(@PathVariable User user) {
        Response<List<EventRegistration>> response = eventRegistrationService.getEventRegistrationsByUser(user);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    // Buscar inscripciones por estado de registro
    @GetMapping("/status/{status}")
    public ResponseEntity<Response<List<EventRegistration>>> getEventRegistrationsByStatus(@PathVariable StatusRegistrationEvent status) {
        Response<List<EventRegistration>> response = eventRegistrationService.getEventRegistrationsByStatus(status);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    // Buscar inscripciones por evento y estado de registro
    @GetMapping("/event/{eventId}/status/{status}")
    public ResponseEntity<Response<List<EventRegistration>>> getEventRegistrationsByEventAndStatus(
            @PathVariable Event event,
            @PathVariable StatusRegistrationEvent status) {
        Response<List<EventRegistration>> response = eventRegistrationService.getEventRegistrationsByEventAndStatus(event, status);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    // Verificar si existe una inscripción para un usuario en un evento
    @GetMapping("/exists/event/{eventId}/user/{userId}")
    public ResponseEntity<Response<Boolean>> existsEventRegistration(
            @PathVariable Event event,
            @PathVariable User user) {
        Response<Boolean> response = eventRegistrationService.existsEventRegistration(event, user);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    // Buscar inscripciones por usuario y estado de registro
    @GetMapping("/user/{userId}/status/{status}")
    public ResponseEntity<Response<List<EventRegistration>>> getEventRegistrationsByUserAndStatus(
            @PathVariable User user,
            @PathVariable StatusRegistrationEvent status) {
        Response<List<EventRegistration>> response = eventRegistrationService.getEventRegistrationsByUserAndStatus(user, status);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    // Buscar inscripciones realizadas después de una fecha específica
    @GetMapping("/created-after")
    public ResponseEntity<Response<List<EventRegistration>>> getEventRegistrationsByCreatedAtAfter(
            @RequestParam("createdAt") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Timestamp createdAt) {
        Response<List<EventRegistration>> response = eventRegistrationService.getEventRegistrationsByCreatedAtAfter(createdAt);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }
}
