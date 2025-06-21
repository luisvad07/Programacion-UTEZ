package com.imagen_social.mac_morelos_api.models.promoterAssignments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imagen_social.mac_morelos_api.models.users.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface PromoterAssignmentRepository extends JpaRepository<PromoterAssignment, Long> {

    // Encuentra todas las asignaciones de un promotor específico
    List<PromoterAssignment> findByPromoter(User promoter);

    // Encuentra todas las asignaciones de un ciudadano específico
    List<PromoterAssignment> findByUser(User user);

    // Encuentra una asignación específica entre un promotor y un ciudadano
    Optional<PromoterAssignment> findByPromoterAndUser(User promoter, User user);

    // Verifica si existe una asignación entre un promotor y un ciudadano
    boolean existsByPromoterAndUser(User promoter, User user);
}
