package com.imagen_social.mac_morelos_api.controllers.mapColorimetry;

import com.imagen_social.mac_morelos_api.models.mapColorimetries.MapColorimetry;
import com.imagen_social.mac_morelos_api.services.mapColorimetry.MapColorimetryService;
import com.imagen_social.mac_morelos_api.utils.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/map-colorimetries")
@CrossOrigin(value = {"*"})
public class MapColorimetryController {

    @Autowired
    private MapColorimetryService mapColorimetryService;

    @GetMapping("/getAll")
    public ResponseEntity<Response<List<MapColorimetry>>> getAllMapColorimetries() {
        Response<List<MapColorimetry>> response = mapColorimetryService.getAllMapColorimetries();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @GetMapping("/municipality/{municipality}")
    public ResponseEntity<Response<MapColorimetry>> getMapColorimetryByMunicipality(@PathVariable String municipality) {
        Response<MapColorimetry> response = mapColorimetryService.getMapColorimetryByMunicipality(municipality);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    // @PutMapping("/{id}")
    // public ResponseEntity<Response<MapColorimetry>> updateMapColorimetry(@PathVariable Long id, @RequestBody MapColorimetry mapColorimetryDetails) {
    //     Response<MapColorimetry> response = mapColorimetryService.updateMapColorimetry(id, mapColorimetryDetails);
    //     return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    // }

    @PostMapping("/load-initial")
    public ResponseEntity<Response<List<MapColorimetry>>> loadInitialMapColorimetries() {
        Response<List<MapColorimetry>> response = mapColorimetryService.loadInitialMapColorimetries();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }
}