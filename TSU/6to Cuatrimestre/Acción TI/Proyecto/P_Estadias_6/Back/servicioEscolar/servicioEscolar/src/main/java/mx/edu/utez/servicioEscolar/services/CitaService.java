package mx.edu.utez.servicioEscolar.services;

import mx.edu.utez.servicioEscolar.models.cita.Cita;
import mx.edu.utez.servicioEscolar.models.cita.CitaRepository;
import mx.edu.utez.servicioEscolar.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Transactional(readOnly = true)
    public CustomResponse<List<Cita>> getAll(){
        return new CustomResponse<>(
                this.citaRepository.findAll(),
                false,
                200,
                "ok"
        );
    }

    ///Servicio para las citas activas
    @Transactional(readOnly = true)
    public  CustomResponse<List<Cita>> getAllActive(){
        return new CustomResponse<>(
                this.citaRepository.findAllByStatus(true),
                false,
                200,
                "ok"
        );
    }

    ///Servicio para las citas inactivas
    @Transactional(readOnly = true)
    public  CustomResponse<List<Cita>> getAllInactive(){
        return new CustomResponse<>(
                this.citaRepository.findAllByStatus(false),
                false,
                200,
                "ok"
        );
    }

    ///Servicio para buscar una cita
    @Transactional (readOnly = true)
    public CustomResponse<Cita> getOne(Long id) {
        Optional<Cita> citaOptional = this.citaRepository.findById(id);
        if (citaOptional.isPresent()) {
            return new CustomResponse<>(
                    citaOptional.get(),
                    false,
                    200,
                    "ok"
            );
        } else {
            return new CustomResponse<>(
                    null,
                    true,
                    404,
                    "Cita no encontrada"
            );
        }
    }

    ///Servicio para insertar una cita
    @Transactional (rollbackFor = {SQLException.class})
    public CustomResponse<Cita> insert(Cita cita){
        /*if (this.citaRepository.existsByCorreoAdmin(admin.getCorreoAdmin())) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "El correo ya existe"
            );
        }*/
        return new CustomResponse<>(
                this.citaRepository.saveAndFlush(cita),
                false,
                200,
                "Cita registrada correctamente"
        );
    }

    //Servicio actualizar
    @Transactional (rollbackFor = {SQLException.class})
    public  CustomResponse<Cita> update(Cita cita){
        if(!this.citaRepository.existsById(cita.getId())) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "Cita no encontrada"
            );
        }
        return new CustomResponse<>(
                this.citaRepository.saveAndFlush(cita),
                false,
                200,
                "Cita actualizada correctamente"
        );
    }

    //Servicio para cambiar status
    @Transactional (rollbackFor = {SQLException.class})
    public CustomResponse<Boolean> changeStatus(Cita cita){
        if (!this.citaRepository.existsById(cita.getId())) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "Cita no encontrada"
            );
        }
        return new CustomResponse<>(
                this.citaRepository.updateStatusById(
                        cita.getStatus(), cita.getId()
                )==1,
                false,
                200,
                "Se ha cambiado el status de la cita"
        );
    }
}
