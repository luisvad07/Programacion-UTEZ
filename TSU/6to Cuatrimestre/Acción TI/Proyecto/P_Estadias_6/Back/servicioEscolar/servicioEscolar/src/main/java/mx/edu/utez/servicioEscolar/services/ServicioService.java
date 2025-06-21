package mx.edu.utez.servicioEscolar.services;

import mx.edu.utez.servicioEscolar.models.servicio.Servicio;
import mx.edu.utez.servicioEscolar.models.servicio.ServicioRepository;
import mx.edu.utez.servicioEscolar.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    //Servicio para obtener todos los servicios
    @Transactional(readOnly = true)
    public CustomResponse<List<Servicio>> getAll(){
        return new CustomResponse<>(
                this.servicioRepository.findAll(),
                false,
                200,
                "Ok"
        );
    }

    ///Servicio para los activos
    @Transactional(readOnly = true)
    public  CustomResponse<List<Servicio>> getAllActive(){
        return new CustomResponse<>(
                this.servicioRepository.findAllByStatus(true),
                false,
                200,
                "ok"
        );
    }

    ///Servicio para los inactivos
    @Transactional(readOnly = true)
    public  CustomResponse<List<Servicio>> getAllInactive(){
        return new CustomResponse<>(
                this.servicioRepository.findAllByStatus(false),
                false,
                200,
                "ok"
        );
    }

    ///Servicio para buscar uno
    @Transactional(readOnly = true)
    public CustomResponse<Servicio> getOne(Long id) {
        Optional<Servicio> servicioOptional = this.servicioRepository.findById(id);
        if (servicioOptional.isPresent()) {
            return new CustomResponse<>(
                    servicioOptional.get(),
                    false,
                    200,
                    "ok"
            );
        } else {
            return new CustomResponse<>(
                    null,
                    true,
                    404,
                    "Servicio no encontrado"
            );
        }
    }

    //Servicio para insertar
    @Transactional (rollbackFor = {SQLException.class})
    public CustomResponse<Servicio> insert(Servicio servicio){

        return new CustomResponse<>(
                this.servicioRepository.saveAndFlush(servicio),
                false,
                200,
                "Servicio registrado correctamente"
        );
    }

    //Servicio para actualizar
    @Transactional (rollbackFor = {SQLException.class})
    public  CustomResponse<Servicio> update(Servicio servicio){
        if (!this.servicioRepository.existsById(servicio.getId())) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "El servicio no existe"
            );
        }
        return new CustomResponse<>(
                this.servicioRepository.saveAndFlush(servicio),
                false,
                200,
                "Servicio actualizado correctamente"
        );
    }

    //Servicio para cambiar el status
    @Transactional (rollbackFor = {SQLException.class})
    public CustomResponse<Boolean> changeStatus(Servicio servicio){
        if (!this.servicioRepository.existsById(servicio.getId())) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "El servicio no existe"
            );
        }
        return new CustomResponse<>(
                this.servicioRepository.updateStatusById(servicio.getStatus(), servicio.getId()
                ) == 1,
                false,
                200,
                "Se ha cambiado el status del servicio"
        );
    }
}
