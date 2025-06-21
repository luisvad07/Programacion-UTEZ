package com.imagen_social.mac_morelos_api.controllers.promoterAssignments;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imagen_social.mac_morelos_api.models.promoterAssignments.PromoterAssignment;
import com.imagen_social.mac_morelos_api.models.users.User;
import com.imagen_social.mac_morelos_api.models.users.UserRepository;
import com.imagen_social.mac_morelos_api.services.promoterAssignments.PromoterAssignmentService;
import com.imagen_social.mac_morelos_api.utils.Response;

@RestController
@RequestMapping("${apiPrefix}/promoterAssignments")
@CrossOrigin(value = {"*"})
public class PromoterAssignmentController {
    
    @Autowired
    private PromoterAssignmentService promoterAssignmentService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/getAll")
    public ResponseEntity<Response<List<PromoterAssignment>>> getAllAssignments() {
        Response<List<PromoterAssignment>> response = promoterAssignmentService.getAllAssignments();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    // Crear una asignación
    @PostMapping("/create")
    public ResponseEntity<Response<PromoterAssignment>> createPromoterAssignment(@RequestBody PromoterAssignment promoterAssignment) {
        Response<PromoterAssignment> response = promoterAssignmentService.createPromoterAssignment(promoterAssignment);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    // Obtener una asignación por ID
    @GetMapping("/get/{assignmentId}")
    public ResponseEntity<Response<PromoterAssignment>> getPromoterAssignment(@PathVariable Long assignmentId) {
        Response<PromoterAssignment> response = promoterAssignmentService.getPromoterAssignment(assignmentId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    // Obtener asignaciones por promotor
    @GetMapping("/getByPromoter")
    public ResponseEntity<Response<List<PromoterAssignment>>> getAssignmentsByPromoter(@RequestParam("promoterId") Long promoterId) {
        User promoter = userRepository.findById(promoterId).orElseThrow(() -> new RuntimeException("Promotor no encontrado"));
        Response<List<PromoterAssignment>> response = promoterAssignmentService.getAssignmentsByPromoter(promoter);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    // Obtener asignaciones por usuario
    @GetMapping("/getByUser")
    public ResponseEntity<Response<List<PromoterAssignment>>> getAssignmentsByUser(@RequestParam("userId") Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Response<List<PromoterAssignment>> response = promoterAssignmentService.getAssignmentsByUser(user);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }
}
