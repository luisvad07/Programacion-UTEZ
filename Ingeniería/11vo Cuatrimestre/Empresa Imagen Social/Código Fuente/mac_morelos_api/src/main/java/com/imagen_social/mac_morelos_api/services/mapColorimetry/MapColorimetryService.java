package com.imagen_social.mac_morelos_api.services.mapColorimetry;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imagen_social.mac_morelos_api.models.mapColorimetries.MapColorimetry;
import com.imagen_social.mac_morelos_api.models.mapColorimetries.MapColorimetryRepository;
import com.imagen_social.mac_morelos_api.utils.Response;

@Service
public class MapColorimetryService {

    @Autowired
    private MapColorimetryRepository mapColorimetryRepository;

    // Obtener todas las colorimetrías
    @Transactional(readOnly = true)
    public Response<List<MapColorimetry>> getAllMapColorimetries() {
        List<MapColorimetry> mapColorimetries = mapColorimetryRepository.findAll();
        return new Response<>(mapColorimetries, false, 200, "Colorimetrías obtenidas exitosamente");
    }

    // Obtener una colorimetría por municipio
    @Transactional(readOnly = true)
    public Response<MapColorimetry> getMapColorimetryByMunicipality(String municipality) {
        Optional<MapColorimetry> mapColorimetry = mapColorimetryRepository.findByMunicipality(municipality);
        return mapColorimetry
                .map(colorimetry -> new Response<>(colorimetry, false, 200, "Colorimetría encontrada"))
                .orElse(new Response<>(null, true, 404, "Colorimetría no encontrada"));
    }

    // // Actualizar una colorimetría
    // @Transactional
    // public Response<MapColorimetry> updateMapColorimetry(Long id, MapColorimetry mapColorimetryDetails) {
    //     Optional<MapColorimetry> existingMapColorimetry = mapColorimetryRepository.findById(id);
    //     if (existingMapColorimetry.isPresent()) {
    //         MapColorimetry mapColorimetry = existingMapColorimetry.get();
    //         mapColorimetry.setMunicipality(mapColorimetryDetails.getMunicipality());
    //         mapColorimetry.setColorHex(mapColorimetryDetails.getColorHex());
    //         mapColorimetry.setValueArea(mapColorimetryDetails.getValueArea());
    //         mapColorimetry.setDescription(mapColorimetryDetails.getDescription());
    //         mapColorimetry.setCreatedAt(mapColorimetry.getCreatedAt());
    //         mapColorimetry.setUpdatedAt(Timestamp.from(Instant.now()));
    //         MapColorimetry updatedMapColorimetry = mapColorimetryRepository.save(mapColorimetry);
    //         return new Response<>(updatedMapColorimetry, false, 200, "Colorimetría actualizada con éxito");
    //     }
    //     return new Response<>(null, true, 404, "Colorimetría no encontrada");
    // }

    // Cargar colorimetrias iniciales para 36 municipios
    @Transactional
    public Response<List<MapColorimetry>> loadInitialMapColorimetries() {
        
        // Verificar si ya existen colorimetrias cargadas
        if (!mapColorimetryRepository.findAll().isEmpty()) {
            return new Response<>(mapColorimetryRepository.findAll(),false, 200, "Las colorimetrias ya han sido cargadas");
        }

        List<MapColorimetry> initialColorimetries = List.of(
            createMapColorimetryObject("Amacuzac", "#800080", 117.2, "En el río de amarillos amates", 1868),
            createMapColorimetryObject("Atlatlahucan", "#0000FF", 79.4, "Donde hay agua rojiza o colorada", 1932),
            createMapColorimetryObject("Axochiapan", "#008000", 141.5, "Río de ayoxochiles o flor de calabaza", 1898),
            createMapColorimetryObject("Ayala", "#FFFF00", 368.3, "Nombrado así en honor del insurgente Francisco Ayala.", 1868),
            createMapColorimetryObject("Coatetelco", "#FF0000", 51.6, "En el montón de serpientes", 2019),
            createMapColorimetryObject("Coatlán del Río", "#FFA500", 83.4, "Lugar donde abundan las serpientes", 1861),
            createMapColorimetryObject("Cuautla", "#808080", 121.9, "Arboleda o bosque", 1824),
            createMapColorimetryObject("Cuernavaca", "#00FFFF", 199.7, "Lugar donde hay árboles juntos", 1824),
            createMapColorimetryObject("Emiliano Zapata", "#000080", 68.37, "Nombrado así en honor al cuadillo revolucionario Emiliano Zapata.", 1932),
            createMapColorimetryObject("Hueyapan", "#A52A2A", 19.2, "En el río grande", 2019),
            createMapColorimetryObject("Huitzilac", "#D2691E", 189.1, "En agua de colibríes", 1921),
            createMapColorimetryObject("Jantetelco", "#00FF00", 102.3, "En el montón de adobes", 1826),
            createMapColorimetryObject("Jiutepec", "#FF00FF", 55.9, "En el cerro verde o turquesa", 1826),
            createMapColorimetryObject("Jojutla", "#8B008B", 149.0, "Lugar abundante en pintura", 1847),
            createMapColorimetryObject("Jonacatepec de Leandro Valle", "#2E8B57", 90.3, "En el cerro de las cebollas", 1825),
            createMapColorimetryObject("Mazatepec", "#B8860B", 57.9, "En el cerro de los venados", 1849),
            createMapColorimetryObject("Miacatlán", "#6495ED", 162.9, "Lugar abundante en cañas para flechas", 1826),
            createMapColorimetryObject("Ocuituco", "#DC143C", 86.5, "En donde hay gorgojos", 1826),
            createMapColorimetryObject("Puente de Ixtla", "#008B8B", 237.2, "Lugar donde hay obsidiana en abundancia", 1826),
            createMapColorimetryObject("Temixco", "#2F4F4F", 102.89, "En el león de la piedra", 1933),
            createMapColorimetryObject("Temoac", "#4682B4", 37.1, "En agua baja", 1977),
            createMapColorimetryObject("Tepalcingo", "#D2B48C", 368.6, "Abajo o detrás de los pedernales", 1826),
            createMapColorimetryObject("Tepoztlán", "#00CED1", 242.4, "Lugar donde hay mucho hierro o cobre", 1826),
            createMapColorimetryObject("Tetecala", "#9400D3", 67.7, "Donde hay casas de piedra", 1826),
            createMapColorimetryObject("Tetela del Volcán", "#708090", 79.3, "Lugar donde hay muchas piedras del volcán (Lugar entre piedras del volcán, Donde hay muchas piedras, pedregal)", 1937),
            createMapColorimetryObject("Tlalnepantla", "#FF7F50", 107.9, "En medio de las tierras", 1848),
            createMapColorimetryObject("Tlaltizapán de Zapata", "#696969", 238.5, "Sobre blanca tierra", 1826),
            createMapColorimetryObject("Tlaquiltenango", "#000000", 543.9, "En los muros bruñidos o escalados", 1826),
            createMapColorimetryObject("Tlayacapan", "#8FBC8F", 57.2, "Sobre la punta de la tierra", 1826),
            createMapColorimetryObject("Totolapan", "#8B0000", 60.08, "Sobre agua, gallaretas", 1826),
            createMapColorimetryObject("Xochitepec", "#556B2F", 93.2, "En el cerro de las flores", 1826),
            createMapColorimetryObject("Xoxocotla", "#483D8B", 61.7, "Lugar de ciruelos agrios", 2019),
            createMapColorimetryObject("Yautepec", "#B0E0E6", 179.6, "En el cerro del pericón", 1826),
            createMapColorimetryObject("Yecapixtla", "#ADD8E6", 173.2, "Lugar de sutiles aires", 1826),
            createMapColorimetryObject("Zacatepec de Hidalgo", "#E0FFFF", 30.7, "En el cerro del zacate o grama", 1938),
            createMapColorimetryObject("Zacualpan de Amilpas", "#FAF0E6", 53.8, "Sobre cosa tapada", 1826)
        );

        List<MapColorimetry> savedColorimetries = mapColorimetryRepository.saveAll(initialColorimetries);
        return new Response<>(savedColorimetries, false, 201, "Colorimetrías iniciales cargadas con éxito");
    }

    private MapColorimetry createMapColorimetryObject(String municipality, String colorHex, double value, String description, int yearCreated) {
        MapColorimetry mapColorimetry = new MapColorimetry();
            mapColorimetry.setMunicipality(municipality);
            mapColorimetry.setColorHex(colorHex);
            mapColorimetry.setValueArea(BigDecimal.valueOf(value));
            mapColorimetry.setDescription(description);
            mapColorimetry.setYearCreated(yearCreated);
            mapColorimetry.setCreatedAt(Timestamp.from(Instant.now()));
            //mapColorimetry.setUpdatedAt(Timestamp.from(Instant.now()));
        return mapColorimetry;
    }

}

