package com.imagen_social.mac_morelos_api.models.addresses;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    // Encuentra direcciones por ciudad/municipio
    List<Address> findByCity(String city);

    // Encuentra direcciones por estado
    List<Address> findByState(String state);

    // Encuentra direcciones por código postal
    List<Address> findByZipCode(String zipCode);
}
