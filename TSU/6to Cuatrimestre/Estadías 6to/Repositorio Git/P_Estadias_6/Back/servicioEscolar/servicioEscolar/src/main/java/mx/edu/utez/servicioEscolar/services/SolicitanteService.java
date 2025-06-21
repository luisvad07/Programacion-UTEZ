package mx.edu.utez.servicioEscolar.services;

import mx.edu.utez.servicioEscolar.models.solicitante.Solicitante;
import mx.edu.utez.servicioEscolar.models.solicitante.SolicitanteRepository;
import mx.edu.utez.servicioEscolar.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SolicitanteService {

    @Autowired
    private SolicitanteRepository solicitanteRepository;

    ///Todos los solicitantes
    @Transactional(readOnly = true)
    public CustomResponse<List<Solicitante>> getAll(){
        return new CustomResponse<>(
                this.solicitanteRepository.findAll(),
                false,
                200,
                "Ok"
        );
    }

    ///Servicio para los activos
    @Transactional(readOnly = true)
    public  CustomResponse<List<Solicitante>> getAllActive(){
        return new CustomResponse<>(
                this.solicitanteRepository.findAllByStatus(true),
                false,
                200,
                "ok"
        );
    }

    ///Servicio para los inactivos
    @Transactional(readOnly = true)
    public  CustomResponse<List<Solicitante>> getAllInactive(){
        return new CustomResponse<>(
                this.solicitanteRepository.findAllByStatus(false),
                false,
                200,
                "ok"
        );
    }

    ///Solicitante por id
    @Transactional(readOnly = true)
    public CustomResponse<Solicitante> getOne(Long id) {
        Optional<Solicitante> soliOptional = this.solicitanteRepository.findById(id);
        if (soliOptional.isPresent()) {
            return new CustomResponse<>(
                    soliOptional.get(),
                    false,
                    200,
                    "ok"
            );
        } else {
            return new CustomResponse<>(
                    null,
                    true,
                    404,
                    "El solicitante no existe"
            );
        }
    }

    //Insertar un solicitante
    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Solicitante> insert(Solicitante solicitante){
         if (this.solicitanteRepository.existsByCorreoSoli(solicitante.getCorreoSoli())) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "El correo ya existe"
            );
        }
        return new CustomResponse<>(
                this.solicitanteRepository.saveAndFlush(solicitante),
                false,
                200,
                "Solicitante registrado correctamente"
        );
    }

    //Actualizar un horario
    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Solicitante> update(Solicitante solicitante){
        if(!this.solicitanteRepository.existsById(solicitante.getId()))
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "Solicitante no encontrado"
            );
        return new CustomResponse<>(
                this.solicitanteRepository.saveAndFlush(solicitante),
                false,
                200,
                "Solicitante actualizado correctamente"
        );
    }

    //Cambiar Status
    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Boolean> changeStatus(Solicitante solicitante){
        if(!this.solicitanteRepository.existsById(solicitante.getId())){
            return new CustomResponse<>(
                    false,
                    true,
                    400,
                    "Solicitante no encontrado"
            );
        }
        return new CustomResponse<>(
                this.solicitanteRepository.updateStatusById(
                        solicitante.getStatus(), solicitante.getId()
                ) == 1,
                false,
                200,
                "Status actualizado correctamente"
        );
    }
}
