package com.imagen_social.mac_morelos_api.services.eventsRegistrations;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imagen_social.mac_morelos_api.enums.roles.RoleEnum;
import com.imagen_social.mac_morelos_api.enums.statusEvents.StatusRegistrationEvent;
import com.imagen_social.mac_morelos_api.models.events.Event;
import com.imagen_social.mac_morelos_api.models.eventsRegistrations.EventRegistration;
import com.imagen_social.mac_morelos_api.models.eventsRegistrations.EventRegistrationRepository;
import com.imagen_social.mac_morelos_api.models.users.User;
import com.imagen_social.mac_morelos_api.models.users.UserRepository;
import com.imagen_social.mac_morelos_api.utils.Response;

@Service
public class EventRegistrationService {

    @Autowired
    private EventRegistrationRepository eventRegistrationRepository;

    @Autowired
    private UserRepository userRepository;

    // Obtener todas las inscripciones
    @Transactional(readOnly = true)
    public Response<List<EventRegistration>> getAllEventRegistrations() {
        List<EventRegistration> eventRegistrations = eventRegistrationRepository.findAll();
        return new Response<>(eventRegistrations, false, 200, "Inscripciones obtenidas exitosamente");
    }

    // Obtener una inscripción por su ID
    @Transactional(readOnly = true)
    public Response<EventRegistration> getEventRegistrationById(Long id) {
        return eventRegistrationRepository.findById(id)
                .map(eventRegistration -> new Response<>(eventRegistration, false, 200, "Inscripción encontrada"))
                .orElse(new Response<>(null, true, 404, "Inscripción no encontrada"));
    }

    // Crear una inscripción
    @Transactional
    public Response<EventRegistration> createEventRegistration(EventRegistration eventRegistration) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // este debería ser el "sub" del JWT

        System.out.println("🔐 Username autenticado: " + username);

        User citizenUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (citizenUser.getRole().getName() != RoleEnum.CIUDADANO) {
            return new Response<>(null, true, 403, "Solo los ciudadanos pueden registrarse en eventos");
        }

        eventRegistration.setUser(citizenUser);
        eventRegistration.setCreatedAt(Timestamp.from(Instant.now()));

        EventRegistration savedEventRegistration = eventRegistrationRepository.save(eventRegistration);
        return new Response<>(savedEventRegistration, false, 201, "Inscripción creada con éxito");
    }

    //Modifica el estado de un registro de evento
    @Transactional
    public Response<EventRegistration> updateStatus(Long id, StatusRegistrationEvent newStatus) {
        Optional<EventRegistration> optionalRegistration = eventRegistrationRepository.findById(id);

        if (optionalRegistration.isEmpty()) {
            return new Response<>(null, true, 404, "Inscripción no encontrada");
        }

        EventRegistration registration = optionalRegistration.get();

        // Validación de estado actual (opcional)
        if (registration.getStatusRegisterEvent() == StatusRegistrationEvent.APROBADO && newStatus == StatusRegistrationEvent.PENDIENTE) {
            return new Response<>(null, true, 400, "No se puede cambiar de APROBADO a PENDIENTE");
        }

        registration.setStatusRegisterEvent(newStatus);
        registration.setCreatedAt(registration.getCreatedAt()); // opcional si tienes campos que no quieres tocar

        EventRegistration updated = eventRegistrationRepository.save(registration);
        return new Response<>(updated, false, 200, "Estado actualizado correctamente");
    }

    // Buscar inscripciones por evento
    @Transactional(readOnly = true)
    public Response<List<EventRegistration>> getEventRegistrationsByEvent(Event event) {
        List<EventRegistration> eventRegistrations = eventRegistrationRepository.findByEvent(event);
        return new Response<>(eventRegistrations, false, 200, "Inscripciones encontradas");
    }

    // Buscar inscripciones por usuario
    @Transactional(readOnly = true)
    public Response<List<EventRegistration>> getEventRegistrationsByUser(User user) {
        List<EventRegistration> eventRegistrations = eventRegistrationRepository.findByUser(user);
        return new Response<>(eventRegistrations, false, 200, "Inscripciones encontradas");
    }

    // Buscar inscripciones por estado de registro
    @Transactional(readOnly = true)
    public Response<List<EventRegistration>> getEventRegistrationsByStatus(StatusRegistrationEvent statusRegisterEvent) {
        List<EventRegistration> eventRegistrations = eventRegistrationRepository.findByStatusRegisterEvent(statusRegisterEvent);
        return new Response<>(eventRegistrations, false, 200, "Inscripciones encontradas");
    }

    // Buscar inscripciones por evento y estado de registro
    @Transactional(readOnly = true)
    public Response<List<EventRegistration>> getEventRegistrationsByEventAndStatus(Event event, StatusRegistrationEvent statusRegisterEvent) {
        List<EventRegistration> eventRegistrations = eventRegistrationRepository.findByEventAndStatusRegisterEvent(event, statusRegisterEvent);
        return new Response<>(eventRegistrations, false, 200, "Inscripciones encontradas");
    }

    // Verificar si existe una inscripción para un usuario en un evento
    @Transactional(readOnly = true)
    public Response<Boolean> existsEventRegistration(Event event, User user) {
        boolean exists = eventRegistrationRepository.existsByEventAndUser(event, user);
        return new Response<>(exists, false, 200, exists ? "Inscripción encontrada" : "Inscripción no encontrada");
    }

    // Buscar inscripciones por usuario y estado de registro
    @Transactional(readOnly = true)
    public Response<List<EventRegistration>> getEventRegistrationsByUserAndStatus(User user, StatusRegistrationEvent statusRegisterEvent) {
        List<EventRegistration> eventRegistrations = eventRegistrationRepository.findByUserAndStatusRegisterEvent(user, statusRegisterEvent);
        return new Response<>(eventRegistrations, false, 200, "Inscripciones encontradas");
    }

    // Buscar inscripciones realizadas después de una fecha específica
    @Transactional(readOnly = true)
    public Response<List<EventRegistration>> getEventRegistrationsByCreatedAtAfter(Timestamp createdAt) {
        List<EventRegistration> eventRegistrations = eventRegistrationRepository.findByCreatedAtAfter(createdAt);
        return new Response<>(eventRegistrations, false, 200, "Inscripciones encontradas");
    }
}

