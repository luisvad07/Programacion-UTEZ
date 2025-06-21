package com.imagen_social.mac_morelos_api.controllers.users;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imagen_social.mac_morelos_api.models.users.User;
import com.imagen_social.mac_morelos_api.services.users.UserService;
import com.imagen_social.mac_morelos_api.utils.Response;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("${apiPrefix}/users")
@CrossOrigin(value = {"*"})
public class UserController {

    @Autowired
    private UserService userService;

    // Obtener todos los usuarios
    @GetMapping("/getAll")
    public ResponseEntity<Response<List<User>>> getAllUsers() {
        Response<List<User>> response = userService.getAllUsers();
        return new ResponseEntity<>(
                response,
                HttpStatus.valueOf(response.getStatus())
        );
    }

    // Obtener un usuario por ID
    @GetMapping("/{id}")
    public Response<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // Crear un usuario
    @PostMapping("/create")
    public ResponseEntity<Response<User>> create(HttpServletRequest request) throws IOException {
        StringBuilder body = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            body.append(line);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(body.toString(), User.class); // Deserializa manualmente

        Response<User> response = userService.createUser(user);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Actualizar un usuario
    @PutMapping("/update/{id}")
    public ResponseEntity<Response<User>> update(HttpServletRequest request) throws IOException {
        StringBuilder body = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            body.append(line);
        }
    
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(body.toString(), User.class); // Deserializa manualmente
    
        Response<User> response = userService.updateUser(user);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response<User>> delete(@PathVariable Long id) {
        Response<User> response = userService.deleteUser(id);
        return new ResponseEntity<>(
                response,
                HttpStatus.valueOf(response.getStatus())
        );
    }

    @PatchMapping("/change-status/{id}")
    public ResponseEntity<Response<User>> changeStatus(@PathVariable Long id) {
        Response<User> response = userService.changeStatus(id);
        return new ResponseEntity<>(
                response,
                HttpStatus.valueOf(response.getStatus())
        );
    }

    @PostMapping("/{userId}/profile-picture")
    public ResponseEntity<Response<User>> updateProfilePicture(@RequestParam("file") MultipartFile file, @PathVariable Long userId) {
        Response<User> response = userService.updateProfilePicture(file, userId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/profile_pictures/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> getProfilePicture(@PathVariable String filename) throws MalformedURLException {
        Path filePath = Paths.get("src/main/resources/static/profile_pictures/" + filename);
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists() || resource.isReadable()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)  // O cambia el tipo MIME según el tipo de archivo
                    .body(resource);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @GetMapping("/getProfile")
    public Response<User> getProfile(HttpServletRequest request) {
        String token = request.getHeader("Authorization");  // Obtener el token del header de la solicitud
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);  // Eliminar "Bearer " para obtener solo el token
        }

        return userService.getProfile(token);  // Llamas al servicio con el token
    }

    @PutMapping("/updateProfile")
    public ResponseEntity<Response<User>> updateProfile(@RequestBody User user, @RequestHeader("Authorization") String token) {
        // Asegurarse de que el token esté en formato correcto, es decir, sin "Bearer "
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);  // Eliminar "Bearer " para obtener solo el token
        }

        Response<User> response = userService.updateProfile(user, token);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Mostrar usuarios por rol
    @GetMapping("/role/{roleId}")
    public Response<List<User>> getUsersByRole(@PathVariable Long roleId) {
        return userService.getUsersByRole(roleId);
    }

    // Mostrar usuarios por estado
    @GetMapping("/status/{status}")
    public Response<List<User>> getUsersByStatus(@PathVariable Boolean status) {
        return userService.getUsersByStatus(status);
    }

    // Mostrar usuarios por correo electrónico
    @GetMapping("/email/{email}")
    public Response<User> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    // Mostrar usuarios por CURP
    @GetMapping("/curp/{curp}")
    public Response<User> getUserByCurp(@PathVariable String curp) {
        return userService.getUserByCurp(curp);
    }

    // Mostrar usuarios por RFC
    @GetMapping("/rfc/{rfc}")
    public Response<User> getUserByRfc(@PathVariable String rfc) {
        return userService.getUserByRfc(rfc);
    }
}
