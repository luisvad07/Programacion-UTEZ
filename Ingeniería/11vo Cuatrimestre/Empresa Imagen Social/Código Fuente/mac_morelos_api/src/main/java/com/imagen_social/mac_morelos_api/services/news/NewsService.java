package com.imagen_social.mac_morelos_api.services.news;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.imagen_social.mac_morelos_api.models.news.News;
import com.imagen_social.mac_morelos_api.models.news.NewsRepository;
import com.imagen_social.mac_morelos_api.utils.Response;
import com.imagen_social.mac_morelos_api.enums.statusNews.StatusNews;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Scheduled(fixedRate = 60000) // Cada minuto
    public void publishScheduledNews() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        List<News> scheduledNews = newsRepository.findByStatusNewsAndScheduledDateBefore(StatusNews.PROGRAMADO, now);

        for (News news : scheduledNews) {
            news.setStatusNews(StatusNews.PUBLICADO);
            news.setPublishDate(now);
            news.setScheduledDate(null);
            news.setUpdatedAt(now);
            newsRepository.save(news);
        }
    }

    // Obtener todas las noticias
    @Transactional(readOnly = true)
    public Response<List<News>> getAllNews() {
        try {
            List<News> newsList = newsRepository.findAll();
            return new Response<>(newsList, false, 200, "Noticias obtenidas exitosamente");
        } catch (Exception e) {
            return new Response<>(null, true, 500, "Error al obtener noticias: " + e.getMessage());
        }
    }

    // Obtener una noticia por ID
    @Transactional(readOnly = true)
    public Response<News> getNewsById(Long id) {
        return newsRepository.findById(id)
                .map(news -> new Response<>(news, false, 200, "Noticia encontrada"))
                .orElse(new Response<>(null, true, 404, "Noticia no encontrada con ID: " + id));
    }

    // Crear una noticia
    @Transactional
    public Response<News> createNews(News news) {
        if (news.getTitle() == null || news.getTitle().trim().isEmpty()) {
            return new Response<>(null, true, 400, "El título de la noticia es obligatorio.");
        }
        try {

            // Establecer la fecha de creación
            news.setCreatedAt(new Timestamp(System.currentTimeMillis()));

            // Guardar la noticia con imágenes
            News savedNews = newsRepository.save(news);

            return new Response<>(savedNews, false, 201, "Noticia creada exitosamente");
        } catch (Exception e) {
            return new Response<>(null, true, 500, "Error al crear la noticia: " + e.getMessage());
        }
    }

    // Actualizar una noticia
    @Transactional
    public Response<News> updateNews(Long id, News newsDetails) {
        Optional<News> existingNewsOpt = newsRepository.findById(id);
        if (!existingNewsOpt.isPresent()) {
            return new Response<>(null, true, 404, "Noticia no encontrada con ID: " + id);
        }

        News existingNews = existingNewsOpt.get();

        if (newsDetails.getTitle() == null || newsDetails.getTitle().trim().isEmpty()) {
            return new Response<>(null, true, 400, "El título de la noticia no puede estar vacío.");
        }

        try {
            existingNews.setTitle(newsDetails.getTitle());
            existingNews.setContent(newsDetails.getContent());
            //existingNews.setStatusNews(newsDetails.getStatusNews());
            existingNews.setPublishDate(newsDetails.getPublishDate());
            existingNews.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            News updatedNews = newsRepository.save(existingNews);
            return new Response<>(updatedNews, false, 200, "Noticia actualizada exitosamente");
        } catch (Exception e) {
            return new Response<>(null, true, 500, "Error al actualizar la noticia: " + e.getMessage());
        }
    }

    // Subir imágenes a una noticia existente
    @Transactional
    public Response<News> updateNewsImage(Long newsId, MultipartFile file) {
        // Buscar la noticia por su ID
        Optional<News> optionalNews = newsRepository.findById(newsId);
        if (!optionalNews.isPresent()) {
            return new Response<>(null, true, 404, "Noticia no encontrada con ID: " + newsId);
        }

        News news = optionalNews.get();

        // Si la noticia ya tiene una imagen, eliminar la imagen anterior
        if (news.getImageNews() != null && !news.getImageNews().isEmpty()) {
            Path oldImagePath = Paths.get("src/main/resources/static/news_images/" + news.getImageNews());
            try {
                Files.deleteIfExists(oldImagePath);  // Elimina la imagen anterior si existe
            } catch (IOException e) {
                return new Response<>(null, true, 500, "Error al eliminar la imagen anterior.");
            }
        }

        // Asigna el nombre de archivo a imageNews
        String filename = newsId + "_" + file.getOriginalFilename();  // Crear un nombre único para la imagen
        news.setImageNews(filename);  // Asigna el nombre al campo imageNews

        // Guarda la imagen en la carpeta "news_images"
        try {
            Path path = Paths.get("src/main/resources/static/news_images/" + filename);
            Files.write(path, file.getBytes());  // Guarda el archivo en el sistema
        } catch (IOException e) {
            return new Response<>(null, true, 500, "Error al guardar la imagen.");
        }

        // Guarda la noticia con la nueva imagen en la base de datos
        newsRepository.save(news);

        return new Response<>(news, false, 200, "Imagen de la noticia actualizada con éxito.");
    }

    // Eliminar una noticia
    @Transactional
    public Response<Void> deleteNews(Long id) {
        if (!newsRepository.existsById(id)) {
            return new Response<>(null, true, 404, "Noticia no encontrada con ID: " + id);
        }

        try {
            newsRepository.deleteById(id);
            return new Response<>(null, false, 200, "Noticia eliminada exitosamente");
        } catch (Exception e) {
            return new Response<>(null, true, 500, "Error al eliminar la noticia: " + e.getMessage());
        }
    }

    // Obtener noticias por estado
    @Transactional(readOnly = true)
    public Response<List<News>> getNewsByStatus(StatusNews status) {
        try {
            List<News> newsList = newsRepository.findByStatusNews(status);
            return new Response<>(newsList, false, 200, "Noticias filtradas por estado");
        } catch (Exception e) {
            return new Response<>(null, true, 500, "Error al filtrar noticias: " + e.getMessage());
        }
    }

    // Actualizar solo el estado de una noticia
    @Transactional
    public Response<News> updateNewsStatus(Long newsId, StatusNews newStatus) {
        Optional<News> optionalNews = newsRepository.findById(newsId);
        if (!optionalNews.isPresent()) {
            return new Response<>(null, true, 404, "Noticia no encontrada con ID: " + newsId);
        }

        News news = optionalNews.get();
        StatusNews currentStatus = news.getStatusNews();

        // Validaciones de transición
        if ((currentStatus == StatusNews.PUBLICADO || currentStatus == StatusNews.PROGRAMADO) && newStatus == StatusNews.BORRADOR) {
            return new Response<>(null, true, 400, "No se puede regresar una noticia publicada o programada a borrador.");
        }

        if (currentStatus == StatusNews.PROGRAMADO && newStatus == StatusNews.PROGRAMADO) {
            return new Response<>(null, true, 400, "La noticia ya ha sido programada.");
        }

        // Si pasa a PUBLICADO directamente, publicar inmediatamente
        if (newStatus == StatusNews.PUBLICADO) {
            news.setPublishDate(new Timestamp(System.currentTimeMillis()));
            news.setScheduledDate(null); // Limpiar programación previa
        }

        // Si pasa a PROGRAMADO, se debe establecer desde otra función con fecha
        if (newStatus == StatusNews.PROGRAMADO) {
            // Evita que esta función programe fechas, debe hacerse en otro método
            return new Response<>(null, true, 400, "Debe utilizarse el método de programación con fecha.");
        }

        news.setStatusNews(newStatus);
        news.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        newsRepository.save(news);

        return new Response<>(news, false, 200, "Estado de la noticia actualizado a: " + newStatus);
    }

    @Transactional
    public Response<News> scheduleNews(Long newsId, Timestamp futureDate) {
        Optional<News> optionalNews = newsRepository.findById(newsId);
        if (!optionalNews.isPresent()) {
            return new Response<>(null, true, 404, "Noticia no encontrada con ID: " + newsId);
        }

        News news = optionalNews.get();

        if (news.getStatusNews() == StatusNews.PUBLICADO) {
            return new Response<>(null, true, 400, "No se puede programar una noticia ya publicada.");
        }

        if (news.getStatusNews() == StatusNews.PROGRAMADO) {
            return new Response<>(null, true, 400, "La noticia ya ha sido programada.");
        }

        news.setStatusNews(StatusNews.PROGRAMADO);
        news.setScheduledDate(futureDate);
        news.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        newsRepository.save(news);

        return new Response<>(news, false, 200, "Noticia programada correctamente para: " + futureDate);
    }



}

