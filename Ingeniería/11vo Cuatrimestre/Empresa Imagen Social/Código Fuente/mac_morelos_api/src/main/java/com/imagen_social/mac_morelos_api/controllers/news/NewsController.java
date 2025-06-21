package com.imagen_social.mac_morelos_api.controllers.news;

import com.imagen_social.mac_morelos_api.models.news.News;
import com.imagen_social.mac_morelos_api.services.news.NewsService;
import com.imagen_social.mac_morelos_api.utils.Response;
import com.imagen_social.mac_morelos_api.enums.statusNews.StatusNews;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("${apiPrefix}/news")
@CrossOrigin(value = {"*"})
public class NewsController {

    @Autowired
    private NewsService newsService;

    // Obtener todas las noticias
    @GetMapping("/getAll")
    public ResponseEntity<Response<List<News>>> getAllNews() {
        Response<List<News>> response = newsService.getAllNews();
        return ResponseEntity.status(HttpStatus.valueOf(response.getStatus())).body(response);
    }

    // Obtener una noticia por ID
    @GetMapping("/{id}")
    public ResponseEntity<Response<News>> getNewsById(@PathVariable Long id) {
        Response<News> response = newsService.getNewsById(id);
        return ResponseEntity.status(HttpStatus.valueOf(response.getStatus())).body(response);
    }

    // Crear una noticia
    @PostMapping("/createNews")
    public ResponseEntity<Response<News>> createNews(@RequestBody News news) {
        Response<News> response = newsService.createNews(news);
        return ResponseEntity.status(HttpStatus.valueOf(response.getStatus())).body(response);
    }

    // Actualizar una noticia
    @PutMapping("/{id}")
    public ResponseEntity<Response<News>> updateNews(@PathVariable Long id, @RequestBody News newsDetails) {
        Response<News> response = newsService.updateNews(id, newsDetails);
        return ResponseEntity.status(HttpStatus.valueOf(response.getStatus())).body(response);
    }

    // Subir y actualizar la imagen de una noticia existente
    @PostMapping("/{id}/upload-image")
    public ResponseEntity<Response<News>> updateNewsImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        Response<News> response = newsService.updateNewsImage(id, file);
        return ResponseEntity.status(HttpStatus.valueOf(response.getStatus())).body(response);
    }

    // Eliminar una noticia
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteNews(@PathVariable Long id) {
        Response<Void> response = newsService.deleteNews(id);
        return ResponseEntity.status(HttpStatus.valueOf(response.getStatus())).body(response);
    }

    // Obtener noticias por estado
    @GetMapping("/status")
    public ResponseEntity<Response<List<News>>> getNewsByStatus(@RequestParam StatusNews status) {
        Response<List<News>> response = newsService.getNewsByStatus(status);
        return ResponseEntity.status(HttpStatus.valueOf(response.getStatus())).body(response);
    }

    // Actualizar solo el estado de una noticia
    @PutMapping("/{id}/status")
    public ResponseEntity<Response<News>> updateNewsStatus(@PathVariable Long id, @RequestParam StatusNews newStatus) {
        Response<News> response = newsService.updateNewsStatus(id, newStatus);
        return ResponseEntity.status(HttpStatus.valueOf(response.getStatus())).body(response);
    }

    @PutMapping("/{id}/schedule")
    public ResponseEntity<Response<News>> scheduleNews(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        String dateString = body.get("scheduledDate");
        LocalDateTime date = LocalDateTime.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        Timestamp timestamp = Timestamp.valueOf(date);

        Response<News> response = newsService.scheduleNews(id, timestamp);
        return ResponseEntity.status(HttpStatus.valueOf(response.getStatus())).body(response);
    }


}