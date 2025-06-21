package com.imagen_social.mac_morelos_api.services.events;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.imagen_social.mac_morelos_api.enums.statusEvents.StatusEvents;
import com.imagen_social.mac_morelos_api.models.addresses.Address;
import com.imagen_social.mac_morelos_api.models.events.Event;
import com.imagen_social.mac_morelos_api.models.events.EventRepository;
import com.imagen_social.mac_morelos_api.models.users.User;
import com.imagen_social.mac_morelos_api.models.users.UserRepository;
import com.imagen_social.mac_morelos_api.utils.Response;

@Service
public class EventsService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(EventsService.class);

    @Scheduled(cron = "0 0 0 * * ?")
    public void updatePendingEventsToActive() {
        Timestamp now = Timestamp.from(Instant.now());

        List<Event> eventsToUpdate = eventRepository.findByStatusEventAndStartDateBefore(StatusEvents.PENDIENTE, now);

        logger.info("Eventos pendientes encontrados: " + eventsToUpdate.size()); // Log de eventos encontrados

        for (Event event : eventsToUpdate) {
            event.setStatusEvent(StatusEvents.ACTIVO);
            event.setUpdatedAt(Timestamp.from(Instant.now()));
            eventRepository.save(event);
        }

        logger.info("Eventos actualizados a 'ACTIVO'."); // Log después de actualizar los eventos
    }

    // Obtener todos los eventos
    @Transactional(readOnly = true)
    public Response<List<Event>> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return new Response<>(events, false, 200, "Eventos obtenidos exitosamente");
    }

    // Obtener un evento por su ID
    @Transactional(readOnly = true)
    public Response<Event> getEventById(Long id) {
        return eventRepository.findById(id)
                .map(event -> new Response<>(event, false, 200, "Evento encontrado"))
                .orElse(new Response<>(null, true, 404, "Evento no encontrado"));
    }

    // Crear un evento
    @Transactional
    public Response<Event> createEvent(Event event) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        

        User adminUser = userRepository.findByEmail(email).orElse(null);
        if (adminUser == null) {
            return new Response<>(null, true, 403, "Usuario no encontrado");
        }

        String roleName = adminUser.getRole().getName().toString();

        if (!roleName.equals("ADMINISTRADOR")) {
            return new Response<>(null, true, 403, "No tienes permisos para crear eventos");
        }

        Instant now = Instant.now();
    
        // Validar que las fechas no sean anteriores al momento actual
        if (event.getStartDate().toInstant().isBefore(now)) {
            return new Response<>(null, true, 400, "La fecha de inicio no puede ser en el pasado.");
        }
        if (event.getEndDate().toInstant().isBefore(now)) {
            return new Response<>(null, true, 400, "La fecha de fin no puede ser en el pasado.");
        }

        // Validar que la fecha de inicio sea antes que la de fin
        if (event.getStartDate().toInstant().isAfter(event.getEndDate().toInstant())) {
            return new Response<>(null, true, 400, "La fecha de inicio debe ser anterior a la fecha de fin.");
        }

        event.setCreatedBy(adminUser);

        // Establecer la hora de creación manualmente
        event.setCreatedAt(Timestamp.from(Instant.now()));
        Event savedEvent = eventRepository.save(event);
        return new Response<>(savedEvent, false, 201, "Evento creado con éxito");
    }

    // Actualizar un evento
    @Transactional
    public Response<Event> updateEvent(Long id, Event eventDetails) {
        Optional<Event> existingEvent = eventRepository.findById(id);
        if (existingEvent.isEmpty()) {
            return new Response<>(null, true, 404, "Evento no encontrado");
        }

        Event event = existingEvent.get();
        Instant now = Instant.now();

        // Validar que las fechas no sean anteriores al momento actual
        if (eventDetails.getStartDate().toInstant().isBefore(now)) {
            return new Response<>(null, true, 400, "La fecha de inicio no puede ser en el pasado.");
        }
        if (eventDetails.getEndDate().toInstant().isBefore(now)) {
            return new Response<>(null, true, 400, "La fecha de fin no puede ser en el pasado.");
        }

        // Validar que la fecha de inicio sea antes que la de fin
        if (eventDetails.getStartDate().toInstant().isAfter(eventDetails.getEndDate().toInstant())) {
            return new Response<>(null, true, 400, "La fecha de inicio debe ser anterior a la fecha de fin.");
        }

        event.setName(eventDetails.getName());
        event.setDescription(eventDetails.getDescription());
        event.setStartDate(eventDetails.getStartDate());
        event.setEndDate(eventDetails.getEndDate());
        event.setAddress(eventDetails.getAddress());
        event.setLocation(eventDetails.getLocation());
        event.setStatusEvent(eventDetails.getStatusEvent());
        event.setUpdatedAt(Timestamp.from(now));

        Event updatedEvent = eventRepository.save(event);
        return new Response<>(updatedEvent, false, 200, "Evento actualizado con éxito");
    }

    // Eliminar un evento
    @Transactional
    public Response<Void> deleteEvent(Long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            return new Response<>(null, false, 200, "Event deleted successfully");
        }
        return new Response<>(null, true, 404, "Event not found");
    }

    @Transactional
    public Response<Event> changeEventStatus(Long id, StatusEvents newStatus) {
        Optional<Event> existingEvent = eventRepository.findById(id);
        if (existingEvent.isPresent()) {
            Event event = existingEvent.get();

            // Verificación de permisos (opcional, pero recomendable)
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            User adminUser = userRepository.findByEmail(email).orElse(null);

            if (adminUser == null) {
                return new Response<>(null, true, 403, "Usuario no encontrado.");
            }

            String roleName = adminUser.getRole().getName().toString();
            if (!roleName.equals("ADMINISTRADOR")) {
                return new Response<>(null, true, 403, "No tienes permisos para cambiar el estado del evento.");
            }

            // Mapa de transiciones no permitidas
            Map<StatusEvents, Map<StatusEvents, String>> invalidTransitions = new HashMap<>();
            invalidTransitions.put(StatusEvents.FINALIZADO, Map.of(
                StatusEvents.ACTIVO, "No se puede reactivar un evento finalizado.",
                StatusEvents.CANCELADO, "No se puede cancelar un evento finalizado."
            ));
            invalidTransitions.put(StatusEvents.CANCELADO, Map.of(
                StatusEvents.ACTIVO, "No se puede reactivar un evento cancelado.",
                StatusEvents.FINALIZADO, "No se puede finalizar un evento cancelado."
            ));
            invalidTransitions.put(StatusEvents.ACTIVO, Map.of(
                StatusEvents.PENDIENTE, "No se puede cambiar de 'ACTIVO' a 'PENDIENTE'.",
                StatusEvents.CANCELADO, "No se puede cancelar un evento que ya ha comenzado."  // se verifica después
            ));
            invalidTransitions.put(StatusEvents.PENDIENTE, Map.of(
                StatusEvents.FINALIZADO, "No se puede finalizar un evento pendiente."
            ));

            // Verificar si la transición es válida
            if (invalidTransitions.containsKey(event.getStatusEvent())) {
                String message = invalidTransitions.get(event.getStatusEvent()).get(newStatus);
                if (message != null) {
                    return new Response<>(null, true, 400, message);
                }
            }

            // Verificación de condición adicional para "ACTIVO" -> "CANCELADO"
            if (event.getStatusEvent() == StatusEvents.ACTIVO && newStatus == StatusEvents.CANCELADO) {
                if (event.getStartDate().before(Timestamp.from(Instant.now()))) {
                    return new Response<>(null, true, 400, "No se puede cancelar un evento que ya ha comenzado.");
                }
            }

            event.setStatusEvent(newStatus);
            event.setUpdatedAt(Timestamp.from(Instant.now()));
            Event updatedEvent = eventRepository.save(event);
            return new Response<>(updatedEvent, false, 200, "Estado del evento actualizado con éxito a: " + newStatus);
        }
        return new Response<>(null, true, 404, "Evento no encontrado.");
    }

    // Buscar eventos por estado
    @Transactional(readOnly = true)
    public Response<List<Event>> getEventsByStatus(StatusEvents statusEvent) {
        List<Event> events = eventRepository.findByStatusEvent(statusEvent);
        return new Response<>(events, false, 200, "Ok");
    }

    // Buscar eventos por usuario creado
    @Transactional(readOnly = true)
    public Response<List<Event>> getEventsByUser(User user) {
        List<Event> events = eventRepository.findByCreatedBy(user);
        return new Response<>(events, false, 200, "Ok");
    }

    // Buscar eventos que comienzan después de una fecha específica
    @Transactional(readOnly = true)
    public Response<List<Event>> getEventsByStartDateAfter(Timestamp startDate) {
        List<Event> events = eventRepository.findByStartDateAfter(startDate);
        return new Response<>(events, false, 200, "Ok");
    }

    // Buscar eventos que terminan antes de una fecha específica
    @Transactional(readOnly = true)
    public Response<List<Event>> getEventsByEndDateBefore(Timestamp endDate) {
        List<Event> events = eventRepository.findByEndDateBefore(endDate);
        return new Response<>(events, false, 200, "Ok");
    }

    // Buscar eventos que se están llevando a cabo (donde la fecha de inicio es antes de ahora y la fecha de fin es después de ahora)
    @Transactional(readOnly = true)
    public Response<List<Event>> getOngoingEvents(Timestamp now) {
        List<Event> events = eventRepository.findByStartDateBeforeAndEndDateAfter(now, now);
        return new Response<>(events, false, 200, "Ok");
    }

    // Buscar eventos por nombre parcial (búsqueda flexible)
    @Transactional(readOnly = true)
    public Response<List<Event>> searchEventsByName(String name) {
        List<Event> events = eventRepository.findByNameContainingIgnoreCase(name);
        return new Response<>(events, false, 200, "Ok");
    }

    // Buscar eventos por dirección
    @Transactional(readOnly = true)
    public Response<List<Event>> getEventsByAddress(Address address) {
        List<Event> events = eventRepository.findByAddress(address);
        return new Response<>(events, false, 200, "Ok");
    }
}

