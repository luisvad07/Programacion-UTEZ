package com.imagen_social.mac_morelos_api.services.address;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imagen_social.mac_morelos_api.models.addresses.Address;
import com.imagen_social.mac_morelos_api.models.addresses.AddressRepository;
import com.imagen_social.mac_morelos_api.utils.Response;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    // Obtener todas las direcciones
    @Transactional(readOnly = true)
    public Response<List<Address>> getAllAddresses() {
        List<Address> addresses = addressRepository.findAll();
        return new Response<>(addresses, false, 200, "Direcciones obtenidas exitosamente");
    }

    // Obtener una dirección por su ID
    @Transactional(readOnly = true)
    public Response<Address> getAddressById(Long id) {
        return addressRepository.findById(id)
                .map(address -> new Response<>(address, false, 200, "Dirección encontrada"))
                .orElse(new Response<>(null, true, 404, "Dirección no encontrada"));
    }

    // Crear una dirección
    @Transactional
    public Response<Address> createAddress(Address address) {
        Address savedAddress = addressRepository.save(address);
        return new Response<>(savedAddress, false, 201, "Dirección creada con éxito");
    }

    // Actualizar una dirección
    @Transactional
    public Response<Address> updateAddress(Long id, Address addressDetails) {
        Optional<Address> existingAddress = addressRepository.findById(id);
        if (existingAddress.isPresent()) {
            Address address = existingAddress.get();
            address.setStreet(addressDetails.getStreet());
            address.setCity(addressDetails.getCity());
            address.setState(addressDetails.getState());
            address.setZipCode(addressDetails.getZipCode());
            Address updatedAddress = addressRepository.save(address);
            return new Response<>(updatedAddress, false, 200, "Dirección actualizada con éxito");
        }
        return new Response<>(null, true, 404, "Dirección no encontrada");
    }

    // Eliminar una dirección
    @Transactional
    public Response<Void> deleteAddress(Long id) {
        if (addressRepository.existsById(id)) {
            addressRepository.deleteById(id);
            return new Response<>(null, false, 200, "Dirección eliminada con éxito");
        }
        return new Response<>(null, true, 404, "Dirección no encontrada");
    }

    // Buscar direcciones por ciudad
    @Transactional(readOnly = true)
    public Response<List<Address>> getAddressesByCity(String city) {
        List<Address> addresses = addressRepository.findByCity(city);
        return new Response<>(addresses, false, 200, "Direcciones encontradas");
    }

    // Buscar direcciones por estado
    @Transactional(readOnly = true)
    public Response<List<Address>> getAddressesByState(String state) {
        List<Address> addresses = addressRepository.findByState(state);
        return new Response<>(addresses, false, 200, "Direcciones encontradas");
    }

    // Buscar direcciones por código postal
    @Transactional(readOnly = true)
    public Response<List<Address>> getAddressesByZipCode(String zipCode) {
        List<Address> addresses = addressRepository.findByZipCode(zipCode);
        return new Response<>(addresses, false, 200, "Direcciones encontradas");
    }
}

