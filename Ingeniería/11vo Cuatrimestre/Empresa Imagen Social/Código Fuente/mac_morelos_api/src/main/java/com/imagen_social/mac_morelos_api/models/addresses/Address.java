package com.imagen_social.mac_morelos_api.models.addresses;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "street", nullable = false, length = 255)
    private String street;

    @Column(name = "number", length = 20)
    private String number;

    @Column(name = "interior_number", length = 20)
    private String interiorNumber;

    @Column(name = "neighborhood", length = 100)
    private String neighborhood;

    @Column(name = "zip_code", length = 10)
    private String zipCode;

    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @Column(name = "state", nullable = false, length = 100)
    private String state;

    @Column(name = "country", nullable = false, length = 100)
    private String country;

    @Column(name = "latitude", precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 11, scale = 8)
    private BigDecimal longitude;
}

