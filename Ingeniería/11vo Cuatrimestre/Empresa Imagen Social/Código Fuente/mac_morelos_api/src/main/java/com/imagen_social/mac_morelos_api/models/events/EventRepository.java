package com.imagen_social.mac_morelos_api.models.events;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imagen_social.mac_morelos_api.enums.statusEvents.StatusEvents;
import com.imagen_social.mac_morelos_api.models.addresses.Address;
import com.imagen_social.mac_morelos_api.models.users.User;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    // Encuentra todos los eventos con un estado específico
    List<Event> findByStatusEvent(StatusEvents statusEvent);

    // Encuentra todos los eventos creados por un usuario específico
    List<Event> findByCreatedBy(User createdBy);

    // Encuentra todos los eventos que comienzan después de una fecha específica
    List<Event> findByStartDateAfter(Timestamp startDate);

    // Encuentra todos los eventos que terminan antes de una fecha específica
    List<Event> findByEndDateBefore(Timestamp endDate);

    // Encuentra todos los eventos que se están llevando a cabo (donde la fecha de inicio es antes de ahora y la fecha de fin es después de ahora)
    List<Event> findByStartDateBeforeAndEndDateAfter(Timestamp now, Timestamp now2);

    // Encuentra eventos por nombre parcial (búsqueda flexible)
    List<Event> findByNameContainingIgnoreCase(String name);

    // Encuentra eventos por dirección
    List<Event> findByAddress(Address address);

    // Encuentra eventos con estado específico y fecha de inicio anterior a la fecha proporcionada
    List<Event> findByStatusEventAndStartDateBefore(StatusEvents statusEvent, Timestamp startDate);
}

