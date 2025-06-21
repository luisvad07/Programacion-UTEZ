package com.imagen_social.mac_morelos_api.services.categories; // Adjust package if needed

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imagen_social.mac_morelos_api.models.categories.Category;
import com.imagen_social.mac_morelos_api.models.categories.CategoryRepository;
import com.imagen_social.mac_morelos_api.models.news.NewsRepository;
import com.imagen_social.mac_morelos_api.utils.Response;

import org.springframework.dao.DataIntegrityViolationException; // For potential unique constraint issues

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private NewsRepository newsRepository; // Inject NewsRepository to check associations

    // Obtener todas las categorías
    @Transactional(readOnly = true)
    public Response<List<Category>> getAllCategories() {
        try {
            List<Category> categories = categoryRepository.findAll();
            return new Response<>(categories, false, 200, "Categorías obtenidas exitosamente");
        } catch (Exception e) {
            // Log the exception e
            return new Response<>(null, true, 500, "Error interno al obtener categorías: " + e.getMessage());
        }
    }

    // Obtener una categoría por su ID
    @Transactional(readOnly = true)
    public Response<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(category -> new Response<>(category, false, 200, "Categoría encontrada"))
                .orElse(new Response<>(null, true, 404, "Categoría no encontrada con ID: " + id));
    }

    // Crear una categoría
    @Transactional
    public Response<Category> createCategory(Category category) { // Or use a DTO
        // Basic validation
        if (category.getName() == null || category.getName().trim().isEmpty()) {
             return new Response<>(null, true, 400, "El nombre de la categoría es obligatorio.");
        }

        // Check if category name already exists (assuming unique constraint or repository method)
         if (categoryRepository.existsByName(category.getName())) { // Add existsByName to your repo if needed
              return new Response<>(null, true, 409, "Ya existe una categoría con el nombre: " + category.getName()); // 409 Conflict
         }

        try {
            // Description is optional, no need to set defaults explicitly here unless required
            Category savedCategory = categoryRepository.save(category);
            return new Response<>(savedCategory, false, 201, "Categoría creada exitosamente");
        } catch (DataIntegrityViolationException e) {
             // Catch potential unique constraint violations if existsByName check isn't sufficient
             return new Response<>(null, true, 409, "Error de integridad de datos, posiblemente el nombre ya existe: " + category.getName());
        } catch (Exception e) {
            // Log the exception e
            return new Response<>(null, true, 500, "Error interno al crear la categoría: " + e.getMessage());
        }
    }

    // Actualizar una categoría
    @Transactional
    public Response<Category> updateCategory(Long id, Category categoryDetails) { // Or use a DTO
        Optional<Category> existingCategoryOpt = categoryRepository.findById(id);
        if (!existingCategoryOpt.isPresent()) {
            return new Response<>(null, true, 404, "Categoría no encontrada con ID: " + id);
        }

        Category existingCategory = existingCategoryOpt.get();

        // Validate input details
        if (categoryDetails.getName() == null || categoryDetails.getName().trim().isEmpty()) {
            return new Response<>(null, true, 400, "El nombre de la categoría no puede estar vacío.");
        }

         // Check if the new name conflicts with *another* existing category
         if (!existingCategory.getName().equalsIgnoreCase(categoryDetails.getName()) &&
             categoryRepository.existsByName(categoryDetails.getName())) {
              return new Response<>(null, true, 409, "Ya existe otra categoría con el nombre: " + categoryDetails.getName());
         }


        try {
            existingCategory.setName(categoryDetails.getName());
            existingCategory.setDescription(categoryDetails.getDescription()); // Update description
            Category updatedCategory = categoryRepository.save(existingCategory);
            return new Response<>(updatedCategory, false, 200, "Categoría actualizada exitosamente");
         } catch (DataIntegrityViolationException e) {
              return new Response<>(null, true, 409, "Error de integridad de datos al actualizar, posiblemente el nombre ya existe: " + categoryDetails.getName());
         } catch (Exception e) {
            // Log the exception e
            return new Response<>(null, true, 500, "Error interno al actualizar la categoría: " + e.getMessage());
        }
    }

    // Eliminar una categoría
    @Transactional
    public Response<Void> deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            return new Response<>(null, true, 404, "Categoría no encontrada con ID: " + id);
        }
        if (newsRepository.existsByCategories_CategoryId(id)) { // Add this method to NewsRepository
             return new Response<>(null, true, 400, "No se puede eliminar la categoría porque está asociada a noticias existentes.");
        }


        try {
            categoryRepository.deleteById(id);
            return new Response<>(null, false, 200, "Categoría eliminada exitosamente");
        } catch (Exception e) {
            // Log the exception e
            // Catch potential FK constraint issues if the check above fails or isn't present
             if (e instanceof DataIntegrityViolationException) {
                 return new Response<>(null, true, 400, "No se puede eliminar la categoría, está en uso.");
             }
            return new Response<>(null, true, 500, "Error interno al eliminar la categoría: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Response<Category> getCategoryByName(String name) {
        return categoryRepository.findByName(name) // Assuming findByName exists in repo
               .map(category -> new Response<>(category, false, 200, "Categoría encontrada por nombre"))
               .orElse(new Response<>(null, true, 404, "Categoría no encontrada con nombre: " + name));
    }

}