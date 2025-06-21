package com.imagen_social.mac_morelos_api.models.eventsRegistrations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imagen_social.mac_morelos_api.enums.statusEvents.StatusRegistrationEvent;
import com.imagen_social.mac_morelos_api.models.events.Event;
import com.imagen_social.mac_morelos_api.models.users.User;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface EventRegistrationRepository extends JpaRepository<EventRegistration, Long> {

    // Encuentra todas las inscripciones para un evento específico
    List<EventRegistration> findByEvent(Event event);

    // Encuentra todas las inscripciones de un usuario específico
    List<EventRegistration> findByUser(User user);

    // Encuentra todas las inscripciones con un estado específico
    List<EventRegistration> findByStatusRegisterEvent(StatusRegistrationEvent statusRegisterEvent);

    // Encuentra todas las inscripciones de un evento que están pendientes
    List<EventRegistration> findByEventAndStatusRegisterEvent(Event event, StatusRegistrationEvent statusRegisterEvent);

    // Verifica si existe una inscripción para un usuario en un evento
    boolean existsByEventAndUser(Event event, User user);

    // Encuentra todas las inscripciones de un usuario en un estado específico
    List<EventRegistration> findByUserAndStatusRegisterEvent(User user, StatusRegistrationEvent statusRegisterEvent);

    // Encuentra todas las inscripciones realizadas después de una fecha específica
    List<EventRegistration> findByCreatedAtAfter(Timestamp createdAt);
}

