package com.imagen_social.mac_morelos_api.models.mapColorimetries;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "map_colorimetries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MapColorimetry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "colorimetry_id")
    private Long colorimetryId;

    @Column(name = "municipality", nullable = false, unique = true, length = 50)
    private String municipality;

    @Column(name = "color_hex", nullable = false, length = 10)
    private String colorHex;

    @Column(name = "value_area", precision = 10, scale = 2)
    private BigDecimal valueArea;

    @Column(name = "description")
    private String description;

    @Column(name = "year_created", nullable = false)
    private Integer yearCreated;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp updatedAt;
}

