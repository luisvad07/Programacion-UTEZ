package mx.edu.utez.redre.services.consulta;

import java.util.List;

import mx.edu.utez.redre.models.consultas.Consultas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.edu.utez.redre.models.consultas.ConsultasRepository;

@Service

public class ConsultaServices {
    @Autowired
    private ConsultasRepository consultaRepository;

    public List<Consultas> obtenerConsultas() {
        return consultaRepository.findAll();
    }

    public Consultas crearConsulta(Consultas mensaje) {
        return consultaRepository.save(mensaje);
    }

    public List<Consultas> obtenerUsuario(String usuario) {
        return consultaRepository.findByUsuario(usuario);
    }

}
