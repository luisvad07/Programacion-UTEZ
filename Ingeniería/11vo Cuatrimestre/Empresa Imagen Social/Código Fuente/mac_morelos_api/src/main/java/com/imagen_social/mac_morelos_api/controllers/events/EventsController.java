package com.imagen_social.mac_morelos_api.controllers.events;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.imagen_social.mac_morelos_api.dtos.events.StatusUpdateRequest;
import com.imagen_social.mac_morelos_api.enums.statusEvents.StatusEvents;
import com.imagen_social.mac_morelos_api.models.addresses.Address;
import com.imagen_social.mac_morelos_api.models.events.Event;
import com.imagen_social.mac_morelos_api.models.users.User;
import com.imagen_social.mac_morelos_api.services.events.EventsService;
import com.imagen_social.mac_morelos_api.utils.Response;

@RestController
@RequestMapping("${apiPrefix}/events")
@CrossOrigin(value = {"*"})
public class EventsController {

    @Autowired
    private EventsService eventsService;

    // Obtener todos los eventos
    @GetMapping("/getAll")
    public ResponseEntity<Response<List<Event>>> getAllEvents() {
        Response<List<Event>> response = eventsService.getAllEvents();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    // Obtener un evento por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Response<Event>> getEventById(@PathVariable Long id) {
        Response<Event> response = eventsService.getEventById(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    // Crear un evento
    @PostMapping("/createEvent")
    public ResponseEntity<Response<Event>> createEvent(@RequestBody Event event) {
        Response<Event> response = eventsService.createEvent(event);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    // Actualizar un evento
    @PutMapping("/{id}")
    public ResponseEntity<Response<Event>> updateEvent(@PathVariable Long id, @RequestBody Event eventDetails) {
        Response<Event> response = eventsService.updateEvent(id, eventDetails);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    // Eliminar un evento
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteEvent(@PathVariable Long id) {
        Response<Void> response = eventsService.deleteEvent(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Response<Event>> changeEventStatus(
            @PathVariable Long id,
            @RequestBody StatusUpdateRequest request) {

        Response<Event> response = eventsService.changeEventStatus(id, request.getStatusEvent());

        if (response.isError()) {
            return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    // Buscar eventos por estado
    @GetMapping("/status/{status}")
    public ResponseEntity<Response<List<Event>>> getEventsByStatus(@PathVariable StatusEvents status) {
        Response<List<Event>> response = eventsService.getEventsByStatus(status);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    // Buscar eventos por usuario creado
    @GetMapping("/user/{userId}")
    public ResponseEntity<Response<List<Event>>> getEventsByUser(@PathVariable User user) {
        Response<List<Event>> response = eventsService.getEventsByUser(user);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    // Buscar eventos que comienzan después de una fecha específica
    @GetMapping("/start-after")
    public ResponseEntity<Response<List<Event>>> getEventsByStartDateAfter(
            @RequestParam("startDate") String startDateStr) {

        try {
            // Convertir el String a Timestamp
            Instant instant = Instant.parse(startDateStr);
            Timestamp startDate = Timestamp.from(instant);

            Response<List<Event>> response = eventsService.getEventsByStartDateAfter(startDate);
            return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));

        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Buscar eventos que terminan antes de una fecha específica
    @GetMapping("/end-before")
    public ResponseEntity<Response<List<Event>>> getEventsByEndDateBefore(
            @RequestParam("endDate") String endDateStr) {
        
        try {
            // Convertir el String a Timestamp
            Instant instant = Instant.parse(endDateStr);
            Timestamp endDate = Timestamp.from(instant);

            Response<List<Event>> response = eventsService.getEventsByEndDateBefore(endDate);
            return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));

        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Buscar eventos que se están llevando a cabo (donde la fecha de inicio es antes de ahora y la fecha de fin es después de ahora)
    @GetMapping("/ongoing")
    public ResponseEntity<Response<List<Event>>> getOngoingEvents(
            @RequestParam("now") String nowStr) {

        try {
            // Convertir el String a Timestamp
            Instant instant = Instant.parse(nowStr);
            Timestamp now = Timestamp.from(instant);

            Response<List<Event>> response = eventsService.getOngoingEvents(now);
            return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));

        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Buscar eventos por nombre parcial (búsqueda flexible)
    @GetMapping("/search")
    public ResponseEntity<Response<List<Event>>> searchEventsByName(@RequestParam("name") String name) {
        Response<List<Event>> response = eventsService.searchEventsByName(name);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    // Buscar eventos por dirección
    @GetMapping("/address/{addressId}")
    public ResponseEntity<Response<List<Event>>> getEventsByAddress(@PathVariable Address address) {
        Response<List<Event>> response = eventsService.getEventsByAddress(address);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }
}