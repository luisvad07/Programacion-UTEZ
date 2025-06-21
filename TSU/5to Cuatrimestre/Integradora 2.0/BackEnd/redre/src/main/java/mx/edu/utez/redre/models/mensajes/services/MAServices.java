package mx.edu.utez.redre.models.mensajes.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.redre.models.mensajes.models.mensajeAsesor.MARepository;
import mx.edu.utez.redre.models.mensajes.models.mensajeAsesor.MensajeAsesor;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MAServices {
    @Autowired
    private MARepository mensajeRepository;

    public List<MensajeAsesor> obtenerTodosLosMensajes() {
        return mensajeRepository.findAll();
    }

    public MensajeAsesor crearMensaje(MensajeAsesor mensaje) {
        return mensajeRepository.save(mensaje);
    }

    public List<MensajeAsesor> obtenerMensajePorNombre(String nombre) {
        return mensajeRepository.findByNombreA(nombre);
    }

    public List<MensajeAsesor> obtenerPorMatricula(String matricula) {
        return mensajeRepository.findByNombre(matricula);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public void deleteByNombre(String matricula){
        mensajeRepository.deleteAllByNombre(matricula);
    }
}
