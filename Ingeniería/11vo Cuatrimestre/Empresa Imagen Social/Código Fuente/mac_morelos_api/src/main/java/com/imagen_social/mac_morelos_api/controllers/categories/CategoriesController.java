package com.imagen_social.mac_morelos_api.controllers.categories;

import com.imagen_social.mac_morelos_api.models.categories.Category;
import com.imagen_social.mac_morelos_api.services.categories.CategoryService;
import com.imagen_social.mac_morelos_api.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/categories")
@CrossOrigin(value = {"*"})
public class CategoriesController {

    @Autowired
    private CategoryService categoryService;

    // Obtener todas las categorías
    @GetMapping("/getAll")
    public ResponseEntity<Response<List<Category>>> getAllCategories() {
        Response<List<Category>> response = categoryService.getAllCategories();
        return ResponseEntity.status(HttpStatus.valueOf(response.getStatus())).body(response);
    }

    // Obtener una categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<Response<Category>> getCategoryById(@PathVariable Long id) {
        Response<Category> response = categoryService.getCategoryById(id);
        return ResponseEntity.status(HttpStatus.valueOf(response.getStatus())).body(response);
    }

    // Crear una categoría
    @PostMapping("/createCategory")
    public ResponseEntity<Response<Category>> createCategory(@RequestBody Category category) {
        Response<Category> response = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.valueOf(response.getStatus())).body(response);
    }

    // Actualizar una categoría
    @PutMapping("/{id}")
    public ResponseEntity<Response<Category>> updateCategory(@PathVariable Long id, @RequestBody Category categoryDetails) {
        Response<Category> response = categoryService.updateCategory(id, categoryDetails);
        return ResponseEntity.status(HttpStatus.valueOf(response.getStatus())).body(response);
    }

    // Eliminar una categoría
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteCategory(@PathVariable Long id) {
        Response<Void> response = categoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.valueOf(response.getStatus())).body(response);
    }
}
