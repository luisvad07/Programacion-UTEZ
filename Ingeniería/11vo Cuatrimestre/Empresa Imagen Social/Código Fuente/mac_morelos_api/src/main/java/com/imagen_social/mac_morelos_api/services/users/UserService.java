package com.imagen_social.mac_morelos_api.services.users;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

import com.imagen_social.mac_morelos_api.models.roles.Role;
import com.imagen_social.mac_morelos_api.models.roles.RoleRepository;
import com.imagen_social.mac_morelos_api.models.users.User;
import com.imagen_social.mac_morelos_api.models.addresses.Address;
import com.imagen_social.mac_morelos_api.models.users.UserRepository;
import com.imagen_social.mac_morelos_api.security.jwt.JwtProvider;
import com.imagen_social.mac_morelos_api.utils.Response;

import io.jsonwebtoken.Claims;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender emailSender;  // Servicio de envío de correo

    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;  // Renderiza plantillas de Template

    @Autowired
    private JwtProvider jwtProvider;

    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

    // Obtener todos los usuarios
    @Transactional(readOnly = true)
    public Response<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return new Response<>(users, false, 200, "Usuarios obtenidos exitosamente");
    }

    // Obtener un usuario por su ID
    @Transactional(readOnly = true)
    public Response<User> getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return new Response<>(user.get(), false, 200, "Usuario encontrado");
        }
        return new Response<>(null, true, 404, "Usuario no encontrado");
    }

    // Crear un usuario
    @Transactional(rollbackFor = {SQLException.class})
    public Response<User> createUser(User user) {

        // Verificar si el correo ya está registrado
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return new Response<>(null, true, 400, "El correo ya está registrado en el sistema. ¡Vuelve a llenar los campos!");
        }

        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return new Response<>(null, true, 400, "La contraseña es obligatoria.");
        }

        // Verificar si el username ya está en uso
        if (user.getUsername() != null && userRepository.findByUsername(user.getUsername()).isPresent()) {
            return new Response<>(null, true, 400, "El nombre de usuario ya está en uso.");
        }

        try {
            String rawPassword = user.getPassword(); // Guardar la contraseña antes de encriptarla

            // Configurar valores por defecto
            user.setCreatedAt(Timestamp.from(Instant.now()));
            user.setStatus(true); // Usuario activo por defecto

            // Encriptar contraseña
            user.setPassword(passwordEncoder.encode(rawPassword));

            User savedUser = userRepository.save(user);

            // Intentar enviar el correo de bienvenida, pero no interrumpir el registro si falla
            try {
                sendWelcomeEmail(savedUser, rawPassword);
            } catch (Exception e) {
                LOGGER.warning("Error al enviar el correo de bienvenida: " + e.getMessage());
                e.printStackTrace();
            }

            return new Response<>(savedUser, false, 200, "Usuario registrado con éxito.");

        } catch (DataAccessException e) {
            return new Response<>(null, true, 500, "Error en la base de datos: " + e.getMessage());
        } catch (Exception e) {
            return new Response<>(null, true, 500, "Error inesperado: " + e.getMessage());
        }
    }

    private void sendWelcomeEmail(User user, String rawPassword) throws MessagingException {
        // Crear el contexto de Thymeleaf con los datos necesarios
        Map<String, Object> model = new HashMap<>();
        model.put("email", user.getEmail());
        model.put("password", rawPassword); 
        model.put("username", user.getUsername() != null ? user.getUsername() : "No aplica");

        int roleId = user.getRole().getRoleId().intValue(); // Convertir Long a int
        // Mapear el rol manualmente
        String roleName;
        switch (roleId) {
            case 1:
                roleName = "CIUDADANO";
                break;
            case 2:
                roleName = "PROMOTOR";
                break;
            case 3:
                roleName = "ADMINISTRADOR";
                break;
            case 4:
                roleName = "SUPERVISOR";
                break;
            case 5:
                roleName = "PERIODISTA";
                break;
            default:
                roleName = "No especificado";
                break;
        }
        model.put("role", roleName);

        LOGGER.info("Enviando correo con modelo: " + model);

        // Renderizar la plantilla Thymeleaf con los datos del usuario
        String htmlContent = renderTemplate("user-created", model);

        // Crear el mensaje de correo
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(user.getEmail());
        helper.setSubject("¡Usuario creado Exitosamente!");
        helper.setText(htmlContent, true); // true indica que el contenido es HTML

        // Enviar el correo
        emailSender.send(message);
    }

    private String renderTemplate(String templateName, Map<String, Object> model) {
        try {
            // Crear el contexto para Thymeleaf
            Context thymeleafModel = new Context();
            thymeleafModel.setVariables(model); // Pasar las variables al contexto de Thymeleaf
    
            // Procesar la plantilla con el contexto
            return thymeleafViewResolver.getTemplateEngine().process(templateName, thymeleafModel);
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar la plantilla Thymeleaf", e);
        }
    }
    
    // Actualizar un usuario
    @Transactional(rollbackFor = {SQLException.class})
    public Response<User> updateUser(User user) {

        // Verificar si el userId está presente
        if (user.getUserId() == null) {
            return new Response<>(null, true, 400, "El ID de usuario es obligatorio.");
        }

        // Verificar si el correo ya está registrado y si no es el correo del mismo usuario
        if (userRepository.findByEmail(user.getEmail()).isPresent() && 
            !userRepository.findByEmail(user.getEmail()).get().getUserId().equals(user.getUserId())) {
            return new Response<>(null, true, 400, "El correo ya está registrado.");
        }

        // Verificar si el username ya está en uso y si no es el mismo username del usuario actual
        if (user.getUsername() != null && userRepository.findByUsername(user.getUsername()).isPresent() && 
            !userRepository.findByUsername(user.getUsername()).get().getUserId().equals(user.getUserId())) {
            return new Response<>(null, true, 400, "El nombre de usuario ya está en uso.");
        }

        // Buscar el usuario por ID
        Optional<User> existingUser = userRepository.findById(user.getUserId());
        if (!existingUser.isPresent()) {
            return new Response<>(null, true, 404, "Usuario no encontrado.");
        }

        User updatedUser = existingUser.get();

        // Si el nacimiento no se ha modificado, mantenemos la fecha original
        if (user.getBirthdate() != null && !user.getBirthdate().equals(existingUser.get().getBirthdate())) {
            // Si la fecha de nacimiento ha cambiado, actualizamos
            user.setBirthdate(user.getBirthdate());
        } else {
            // Si no ha cambiado, mantenemos la fecha original
            user.setBirthdate(existingUser.get().getBirthdate());
        }

        // Mantener la contraseña encriptada si no se proporciona una nueva
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword())); // Si la contraseña es nueva, encriptarla
        } else {
            // Si no se proporciona una nueva contraseña, mantener la contraseña existente
            user.setPassword(updatedUser.getPassword());
        }

        // Verificar si la dirección fue proporcionada
        if (user.getAddress() != null) {
            // Verificar si algún campo de la dirección ha cambiado
            boolean addressChanged = false;
            
            if (!user.getAddress().getStreet().equals(updatedUser.getAddress().getStreet())) {
                addressChanged = true;
            }
            if (!user.getAddress().getNumber().equals(updatedUser.getAddress().getNumber())) {
                addressChanged = true;
            }
            // (Repetir la misma lógica para los otros campos de la dirección: neighborhood, city, state, etc.)

            // Si la dirección ha cambiado, se actualiza
            if (addressChanged) {
                user.setAddress(user.getAddress());
            }
        } else {
            // Si no se proporcionó una nueva dirección, mantener la dirección anterior
            user.setAddress(updatedUser.getAddress());
        }

        user.setCreatedAt(updatedUser.getCreatedAt());

        try {
            userRepository.saveAndFlush(user);
            return new Response<>(updatedUser, false, 200, "Usuario actualizado con éxito.");
        } catch (DataAccessException e) {
            return new Response<>(null, true, 500, "Error en la base de datos: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.warning("Error al actualizar: " + e.getMessage());
            return new Response<>(null, true, 500, "Error inesperado: " + e.getMessage());
        }
    }

    // Eliminar un usuario
    @Transactional(rollbackFor = {SQLException.class})
    public Response<User> deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return new Response<>(null, false, 200, "Usuario eliminado exitosamente");
        }
        return new Response<>(null, true, 404, "Usuario no encontrado");
    }

    // Cambiar el estado de un usuario
    @Transactional(rollbackFor = {SQLException.class})
    public Response<User> changeStatus(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            user.get().setStatus(!user.get().getStatus());
            return new Response<>(
                    userRepository.saveAndFlush(user.get()),
                    false,
                    200,
                    "Se actualizo el estado"
            );
        }
        return new Response<>(
                null,
                true,
                400,
                "Usuario no encontrado"
        );
    }

    @Transactional
    public Response<User> updateProfilePicture(MultipartFile file, Long userId) {
        // Encuentra el usuario por ID
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            return new Response<>(null, true, 404, "Usuario no encontrado.");
        }
    
        User user = optionalUser.get();

        // Si el usuario ya tiene una foto de perfil, eliminar la imagen anterior
        if (user.getImgProfile() != null && !user.getImgProfile().isEmpty()) {
            Path oldImagePath = Paths.get("src/main/resources/static/profile_pictures/" + user.getImgProfile());
            try {
                Files.deleteIfExists(oldImagePath);  // Elimina la imagen anterior si existe
            } catch (IOException e) {
                return new Response<>(null, true, 500, "Error al eliminar la foto de perfil anterior.");
            }
        }
    
        // Asigna el nombre de archivo a imgProfile
        String filename = userId + "_" + file.getOriginalFilename();  // Crear un nombre único para la foto
        user.setImgProfile(filename);  // Asigna el nombre al campo imgProfile
    
        // Guarda la imagen en la carpeta "profile_pictures"
        try {
            Path path = Paths.get("src/main/resources/static/profile_pictures/" + filename);
            Files.write(path, file.getBytes());  // Guarda el archivo en el sistema
        } catch (IOException e) {
            return new Response<>(null, true, 500, "Error al guardar la foto de perfil.");
        }
    
        // Guarda el usuario con la nueva imagen en la base de datos
        userRepository.save(user);
    
        return new Response<>(user, false, 200, "Foto de perfil actualizada con éxito.");
    }    

    @Transactional(readOnly = true)
    public Response<User> getProfile(String token) {

        // Llamar al método que resuelve las claims
        Claims claims = jwtProvider.resolveClaims(token);

        String emailOrUsername = jwtProvider.getEmailOrUsernameFromToken(claims);

        Optional<User> optionalUser;

        // Verificamos si el usuario es CIUDADANO (se identifica por username)
        if (userRepository.existsByUsername(emailOrUsername)) {
            optionalUser = userRepository.findByUsername(emailOrUsername);
        } else {
            optionalUser = userRepository.findByEmail(emailOrUsername);
        }

        if (!optionalUser.isPresent()) {
            return new Response<>(null, true, 404, "Usuario no encontrado.");
        }

        User user = optionalUser.get();

        // Aquí le agregamos la URL de la imagen de perfil
        String profilePictureUrl = "/profile_pictures/" + user.getImgProfile();  // Ruta pública para acceder a la foto
        user.setImgProfile(profilePictureUrl); 

        return new Response<>(user, false, 200, "Perfil obtenido con éxito.");
    }

    @Transactional(rollbackFor = {SQLException.class})
    public Response<User> updateProfile(User updatedUser, String token) {
        // Decodificar el token para obtener las claims
        Claims claims = jwtProvider.resolveClaims(token);

        if (claims == null) {
            return new Response<>(null, true, 400, "Token inválido.");
        }

        // Obtener email o username desde el token JWT
        String emailOrUsername = jwtProvider.getEmailOrUsernameFromToken(claims);
        
        Optional<User> optionalUser;
        
        // Verificar si el usuario en sesión es CIUDADANO
        if (userRepository.existsByUsername(emailOrUsername)) {
            // Buscar usuario por username (para CIUDADANO)
            optionalUser = userRepository.findByUsername(emailOrUsername);
        } else {
            // Buscar usuario por email (para los demás roles)
            optionalUser = userRepository.findByEmail(emailOrUsername);
        }

        if (!optionalUser.isPresent()) {
            return new Response<>(null, true, 404, "Usuario no encontrado.");
        }

        User user = optionalUser.get();
        String oldEmail = user.getEmail();

        // Actualizar solo los datos permitidos
        if (updatedUser.getFirstName() != null) user.setFirstName(updatedUser.getFirstName());
        if (updatedUser.getLastName() != null) user.setLastName(updatedUser.getLastName());
        if (updatedUser.getPhone() != null) user.setPhone(updatedUser.getPhone());

        // Verificar si la dirección fue proporcionada en la actualización
        if (updatedUser.getAddress() != null) {
            Address updatedAddress = updatedUser.getAddress();

            // Verificar si la dirección está asociada al usuario
            if (user.getAddress() != null) {
                // Si la dirección ya existe, se actualiza
                user.getAddress().setStreet(updatedAddress.getStreet());
                user.getAddress().setNumber(updatedAddress.getNumber());
                user.getAddress().setInteriorNumber(updatedAddress.getInteriorNumber());
                user.getAddress().setNeighborhood(updatedAddress.getNeighborhood());
                user.getAddress().setZipCode(updatedAddress.getZipCode());
                user.getAddress().setCity(updatedAddress.getCity());
                user.getAddress().setState(updatedAddress.getState());
                user.getAddress().setCountry(updatedAddress.getCountry());
                user.getAddress().setLatitude(updatedAddress.getLatitude());
                user.getAddress().setLongitude(updatedAddress.getLongitude());
            } else {
                // Si no hay dirección asociada, asignamos la nueva dirección
                user.setAddress(updatedAddress);
            }
        }

        // Manejo de nombre de usuario (solo para usuarios CIUDADANO)
        if (updatedUser.getUsername() != null && !updatedUser.getUsername().equals(user.getUsername())) {
            // Verificar si el nombre de usuario ya está en uso
            if (userRepository.findByUsername(updatedUser.getUsername()).isPresent()) {
                return new Response<>(null, true, 400, "El nombre de usuario ya está en uso.");
            }
            user.setUsername(updatedUser.getUsername());
        }

        // Actualización del correo electrónico (sin importar el rol, se puede actualizar siempre que el token sea válido)
        if (updatedUser.getEmail() != null && !updatedUser.getEmail().equals(user.getEmail())) {
            // Verificar si el correo ya está en uso
            if (userRepository.findByEmail(updatedUser.getEmail()).isPresent()) {
                return new Response<>(null, true, 400, "El correo electrónico ya está en uso.");
            }
            user.setEmail(updatedUser.getEmail());
        }

        // Si el usuario quiere actualizar la contraseña
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        // Actualizar fecha de modificación
        user.setUpdatedAt(Timestamp.from(Instant.now()));

        try {
            userRepository.saveAndFlush(user);
            // Enviar correo de notificación si se cambió el correo electrónico
            if (!oldEmail.equals(user.getEmail())) {
                try {
                    sendEmailUpdateEmail(user, oldEmail); //Envia correo con el correo electronico antiguo
                } catch (MessagingException e) {
                    LOGGER.warning("Error al enviar el correo de actualización de correo electrónico: " + e.getMessage());
                    e.printStackTrace();
                }
            }
            return new Response<>(user, false, 200, "Perfil actualizado con éxito.");
        } catch (Exception e) {
            return new Response<>(null, true, 500, "Error al actualizar el perfil: " + e.getMessage());
        }
    }

    // Método para enviar el correo de notificación de cambio de correo electrónico
    private void sendEmailUpdateEmail(User user, String oldEmail) throws MessagingException {
        // Crear el modelo para Thymeleaf
        Map<String, Object> model = new HashMap<>();
        model.put("firstName", user.getFirstName());
        model.put("oldEmail", oldEmail);
        model.put("newEmail", user.getEmail());

        // Renderizar la plantilla Thymeleaf
        String htmlContent = renderTemplate("update-email", model);

        // Crear el mensaje de correo
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(user.getEmail());
        helper.setSubject("¡Tu correo electrónico ha sido actualizado!");
        helper.setText(htmlContent, true); // true indica que el contenido es HTML

        // Enviar el correo
        emailSender.send(message);
    }

    // Mostrar usuarios por rol
    @Transactional(readOnly = true)
    public Response<List<User>> getUsersByRole(Long roleId) {
        Role role = roleRepository.findById(roleId)
            .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        List<User> users = userRepository.findByRole(role);
        return new Response<>(users, false, 200, "Usuarios recuperados con éxito");
    }

    // Mostrar usuarios por estado
    @Transactional(readOnly = true)
    public Response<List<User>> getUsersByStatus(Boolean status) {
        List<User> users = userRepository.findByStatus(status);
        return new Response<>(users, false, 200, "Usuarios recuperados con éxito");
    }

    // Mostrar usuarios por correo electrónico
    @Transactional(readOnly = true)
    public Response<User> getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return new Response<>(user.get(), false, 200, "Usuario encontrado");
        }
        return new Response<>(null, true, 404, "Usuario no encontrado");
    }

    // Mostrar usuarios por CURP
    @Transactional(readOnly = true)
    public Response<User> getUserByCurp(String curp) {
        Optional<User> user = userRepository.findByCurp(curp);
        if (user.isPresent()) {
            return new Response<>(user.get(), false, 200, "Usuario encontrado");
        }
        return new Response<>(null, true, 404, "Usuario no encontrado");
    }

    // Mostrar usuarios por RFC
    @Transactional(readOnly = true)
    public Response<User> getUserByRfc(String rfc) {
        Optional<User> user = userRepository.findByRfc(rfc);
        if (user.isPresent()) {
            return new Response<>(user.get(), false, 200, "Usuario encontrado");
        }
        return new Response<>(null, true, 404, "Usuario no encontrado");
    }
}
