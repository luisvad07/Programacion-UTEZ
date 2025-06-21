package mx.edu.utez.redre.models.mensajes.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.redre.models.mensajes.models.mensajeDepartamento.MDRepository;
import mx.edu.utez.redre.models.mensajes.models.mensajeDepartamento.MensajeDepartamento;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MDServices {
    @Autowired
    private MDRepository mensajeRepository;

    public List<MensajeDepartamento> obtenerTodosLosMensajes() {
        return mensajeRepository.findAll();
    }

    public MensajeDepartamento crearMensaje(MensajeDepartamento mensaje) {
        return mensajeRepository.save(mensaje);
    }

    public List<MensajeDepartamento> obtenerMensajePorNombre(String nombre) {
        return mensajeRepository.findByNombre(nombre);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public void deleteByNombre(String matricula){
        mensajeRepository.deleteAllByNombre(matricula);
    }
}
