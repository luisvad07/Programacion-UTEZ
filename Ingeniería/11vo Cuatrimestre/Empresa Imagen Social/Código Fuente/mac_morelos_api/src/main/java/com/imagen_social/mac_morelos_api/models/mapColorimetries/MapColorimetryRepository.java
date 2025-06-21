package com.imagen_social.mac_morelos_api.models.mapColorimetries;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MapColorimetryRepository extends JpaRepository<MapColorimetry, Long> {

    // Encuentra la colorimetría de un municipio específico
    Optional<MapColorimetry> findByMunicipality(String municipality);

    // Verifica si existe una colorimetría para un municipio específico
    boolean existsByMunicipality(String municipality);
}

