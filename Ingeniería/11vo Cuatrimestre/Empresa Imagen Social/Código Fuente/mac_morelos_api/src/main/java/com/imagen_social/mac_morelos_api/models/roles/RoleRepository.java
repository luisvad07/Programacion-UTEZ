package com.imagen_social.mac_morelos_api.models.roles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.imagen_social.mac_morelos_api.enums.roles.RoleEnum;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    // Encuentra un rol por su nombre
    Optional<Role> findByName(RoleEnum name);

    // Encuentra un rol por su ID
    @SuppressWarnings("null")
    Optional<Role> findById(Long id);

    // Encuentra todos los roles por plataforma permitida
    List<Role> findByAllowedPlatformsContaining(String platform);

    // Verificar si ya existe un rol con ese nombre
    boolean existsByName(RoleEnum name);
}
