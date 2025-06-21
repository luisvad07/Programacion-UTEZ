package com.imagen_social.mac_morelos_api.models.news;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.imagen_social.mac_morelos_api.enums.statusNews.StatusNews;
import com.imagen_social.mac_morelos_api.models.addresses.Address;
import com.imagen_social.mac_morelos_api.models.categories.Category;
import com.imagen_social.mac_morelos_api.models.users.User;

@Entity
@Table(name = "news", indexes = {
    @Index(name = "idx_news_created_by", columnList = "created_by"),
    @Index(name = "idx_news_publish_date", columnList = "publish_date")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class News {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long newsId;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "status_news", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private StatusNews statusNews = StatusNews.BORRADOR;

    @Column(name = "publish_date")
    private Timestamp publishDate;

    @Column(name = "scheduled_date")
    private Timestamp scheduledDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", referencedColumnName = "user_id", nullable = false)
    @JsonBackReference
    private User createdBy;  // El periodista que crea la noticia

    @Column(name = "img_news", length = 255)
    private String imageNews;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address; // Ubicación de la noticia

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp updatedAt;

    @ManyToMany
    @JoinTable(
        name = "news_categories",
        joinColumns = @JoinColumn(name = "news_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @Builder.Default
    private List<Category> categories = new ArrayList<>();
}
