package com.imagen_social.mac_morelos_api.services.promoterAssignments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imagen_social.mac_morelos_api.enums.roles.RoleEnum;
import com.imagen_social.mac_morelos_api.models.promoterAssignments.PromoterAssignment;
import com.imagen_social.mac_morelos_api.models.promoterAssignments.PromoterAssignmentRepository;
import com.imagen_social.mac_morelos_api.models.users.User;
import com.imagen_social.mac_morelos_api.models.users.UserRepository;
import com.imagen_social.mac_morelos_api.utils.Response;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class PromoterAssignmentService {

    @Autowired
    private PromoterAssignmentRepository promoterAssignmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Response<List<PromoterAssignment>> getAllAssignments() {
        List<PromoterAssignment> assignments = promoterAssignmentRepository.findAll();
        return new Response<>(assignments, false, 200, "Todas las asignaciones obtenidas con éxito");
    }

    @Transactional
    public Response<PromoterAssignment> createPromoterAssignment(PromoterAssignment promoterAssignment) {
        // Validar que el promotor existe
        User promoter = userRepository.findById(promoterAssignment.getPromoter().getUserId())
                .orElseThrow(() -> new RuntimeException("Promotor no encontrado"));

        if (promoter.getRole().getName() != RoleEnum.PROMOTOR) {
            return new Response<>(null, true, 400, "El usuario proporcionado no tiene rol de promotor");
        }

        // Validar que el ciudadano existe
        User user = userRepository.findById(promoterAssignment.getUser().getUserId())
                .orElseThrow(() -> new RuntimeException("Ciudadano no encontrado"));

        if (user.getRole().getName() != RoleEnum.CIUDADANO) {
            return new Response<>(null, true, 400, "El usuario proporcionado no tiene rol de ciudadano");
        }

        // Validar duplicidad
        if (promoterAssignmentRepository.existsByPromoterAndUser(promoter, user)) {
            return new Response<>(null, true, 409, "El ciudadano ya está asignado a este promotor");
        }

        // Guardar asignación
        promoterAssignment.setPromoter(promoter);
        promoterAssignment.setUser(user);
        promoterAssignment.setAssignedAt(Timestamp.from(Instant.now()));

        PromoterAssignment saved = promoterAssignmentRepository.save(promoterAssignment);
        return new Response<>(saved, false, 201, "Asignación creada con éxito");
    }

    public Response<PromoterAssignment> getPromoterAssignment(Long assignmentId) {
        Optional<PromoterAssignment> assignment = promoterAssignmentRepository.findById(assignmentId);
        if (assignment.isPresent()) {
            return new Response<>(assignment.get(), false, 200, "Asignación encontrada");
        } else {
            return new Response<>(null, true, 404, "Asignación no encontrada");
        }
    }

    public Response<List<PromoterAssignment>> getAssignmentsByPromoter(User promoter) {
        List<PromoterAssignment> assignments = promoterAssignmentRepository.findByPromoter(promoter);
        return new Response<>(assignments, false, 200, "Asignaciones obtenidas con éxito");
    }

    public Response<List<PromoterAssignment>> getAssignmentsByUser(User user) {
        List<PromoterAssignment> assignments = promoterAssignmentRepository.findByUser(user);
        return new Response<>(assignments, false, 200, "Asignaciones obtenidas con éxito");
    }
}
