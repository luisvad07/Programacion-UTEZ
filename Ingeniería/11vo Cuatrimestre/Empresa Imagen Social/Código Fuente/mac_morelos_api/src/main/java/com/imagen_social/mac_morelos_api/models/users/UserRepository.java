package com.imagen_social.mac_morelos_api.models.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.imagen_social.mac_morelos_api.models.roles.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Encuentra un usuario del gobierno por su correo electrónico
    Optional<User> findByEmail(String email);

    // Encuentra un usuario ciudadano por su nombre de usuario
    Optional<User> findByUsername(String username);

    // Encuentra todos los usuarios con un rol específico
    List<User> findByRole(Role role);

    // Encuentra un usuario por su CURP
    Optional<User> findByCurp(String curp);

    // Encuentra un usuario por su RFC
    Optional<User> findByRfc(String rfc);

    // Encuentra todos los usuarios con un estado específico
    List<User> findByStatus(Boolean status);

    // Encuentra usuarios por nombre
    List<User> findByFirstNameContaining(String firstName);

    // Encuentra usuarios por apellido
    List<User> findByLastNameContaining(String lastName);

    // Método para encontrar un usuario del gobierno por su email y verificar si está activo
    @Query("SELECT u FROM User u WHERE u.email = :email AND u.status = :status")
    User findByEmailAndStatus(@Param("email") String email, @Param("status") boolean status);

    // Método para encontrar un usuario ciudadano por su nombre de usuario y verificar si está activo
    User findByUsernameAndStatus(String username, Boolean status);

    // Verificar si ya existe un usuario con ese email
    boolean existsByEmail(String email);

    // Verificar si ya existe un usuario ciudadano con el nombre de usuario
    boolean existsByUsername(String emailOrUsername);
}
