package com.imagen_social.mac_morelos_api.models.news;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imagen_social.mac_morelos_api.enums.statusNews.StatusNews;
import com.imagen_social.mac_morelos_api.models.users.User;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    // Encuentra todas las noticias por su estado
    List<News> findByStatusNews(StatusNews status);
    
    // Encuentra todas las noticias creadas por un usuario específico "periodista"
    List<News> findByCreatedBy(User user);

    //Encuenta una categoria existente en la noticia
    boolean existsByCategories_CategoryId(Long id);

    List<News> findByStatusNewsAndScheduledDateBefore(StatusNews status, Timestamp timestamp);

}

