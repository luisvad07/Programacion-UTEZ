package mx.edu.utez.redre.services.auth;

import mx.edu.utez.redre.models.asesor.AsesorRepository;
import mx.edu.utez.redre.models.departamento.DepartamentoRepository;
import mx.edu.utez.redre.models.estudiantes.EstudiantesRespository;
import mx.edu.utez.redre.models.responsables.ResponsablesRepository;
import mx.edu.utez.redre.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService<T> {
    @Autowired
    EstudiantesRespository estudiantesRespository;
    @Autowired
    AsesorRepository asesorRepository;
    @Autowired
    ResponsablesRepository responsablesRepository;
    @Autowired
    DepartamentoRepository departamentoRepository;

    @Transactional(readOnly = true)
    public CustomResponse<T> findUser(String correo, String password){
        return null;
    }

}
