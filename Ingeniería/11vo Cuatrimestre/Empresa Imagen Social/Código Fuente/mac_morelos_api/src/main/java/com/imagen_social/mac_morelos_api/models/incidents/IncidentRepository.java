package com.imagen_social.mac_morelos_api.models.incidents;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.imagen_social.mac_morelos_api.enums.statusIncidents.StatusIncidents;
import com.imagen_social.mac_morelos_api.models.users.User;

import java.util.List;
import java.util.Optional;
import java.sql.Timestamp;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {

    @Query("SELECT i FROM Incident i " +
           "JOIN FETCH i.promoter p " +
           "JOIN FETCH p.role r " +
           "JOIN FETCH i.assignedTo a " +
           "JOIN FETCH a.role ar " +
           "WHERE i.id = :id")
    Optional<Incident> findByIdWithUsers(@Param("id") Long id);

    // Encuentra todos los incidentes asignados a un promotor específico
    List<Incident> findByPromoter(User promoter);

    // Encuentra todos los incidentes asignados a un usuario específico (assignedTo)
    List<Incident> findByAssignedTo(User assignedTo);

    // Encuentra todos los incidentes con un estado específico
    List<Incident> findByStatusIncident(StatusIncidents statusIncident);

    // Encuentra todos los incidentes creados antes de una fecha específica
    List<Incident> findByCreatedAtBefore(Timestamp createdAt);

    // Encuentra todos los incidentes resueltos después de una fecha específica
    List<Incident> findByResolvedAtAfter(Timestamp resolvedAt);

    // Encuentra todos los incidentes creados por un promotor con un estado específico
    List<Incident> findByPromoterAndStatusIncident(User promoter, StatusIncidents statusIncident);

    // Verifica si existe un incidente asignado a un usuario específico y con un estado específico
    boolean existsByAssignedToAndStatusIncident(User assignedTo, StatusIncidents statusIncident);
}
