package mx.edu.utez.redre.upload;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.redre.models.reportes.FilesMostrar.FileRepository;
import mx.edu.utez.redre.models.reportes.FilesMostrar.FilesDtos;

@Service
public class FilesServices {
    @Autowired
    private FileRepository repository;

    public List<FilesDtos> obtenerRegistros() {
        return repository.findAll();
    }

    public FilesDtos crearRegistroMovil(FilesDtos files) {
        return repository.save(files);
    }

    public List<FilesDtos> obtenerReportesporCarrera(String carrera) {
        return repository.findByCarrera(carrera);
    }
}
