package mx.edu.utez.redre.models.mensajes.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.redre.models.mensajes.models.mensajeResponsable.MRRepository;
import mx.edu.utez.redre.models.mensajes.models.mensajeResponsable.MensajeResponsable;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MRServices {
    @Autowired
    private MRRepository mensajeRepository;

    public List<MensajeResponsable> obtenerTodosLosMensajes() {
        return mensajeRepository.findAll();
    }

    public MensajeResponsable crearMensaje(MensajeResponsable mensaje) {
        return mensajeRepository.save(mensaje);
    }

    public List<MensajeResponsable> obtenerMensajePorNombre(String nombre) {
        return mensajeRepository.findByNombre(nombre);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public void deleteByNombre(String matricula){
        mensajeRepository.deleteAllByNombre(matricula);
    }
}
