package com.imagen_social.mac_morelos_api.models.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Encuentra una categoría por su nombre
    Optional<Category> findByName(String name); 

    // Encuentra una categoría existente
    boolean existsByName(String name); 
}