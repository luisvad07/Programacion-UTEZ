package com.imagen_social.mac_morelos_api.models.roles;

import com.imagen_social.mac_morelos_api.enums.allowedPlatforms.AllowedPlatforms;
import com.imagen_social.mac_morelos_api.enums.roles.RoleEnum;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "name", nullable = false, unique = true, length = 20)
    @Enumerated(EnumType.STRING)
    private RoleEnum name; // Mapeo del nombre del rol

    @Column(name = "description")
    private String description; // Descripción del rol

    @Column(name = "allowed_platforms", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private AllowedPlatforms allowedPlatforms; // Plataformas permitidas como String separado por comas
}
