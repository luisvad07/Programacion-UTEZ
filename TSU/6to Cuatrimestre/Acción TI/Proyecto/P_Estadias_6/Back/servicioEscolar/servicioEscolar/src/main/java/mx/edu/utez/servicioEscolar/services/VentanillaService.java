package mx.edu.utez.servicioEscolar.services;

import mx.edu.utez.servicioEscolar.models.ventanilla.Ventanilla;
import mx.edu.utez.servicioEscolar.models.ventanilla.VentanillaRepository;
import mx.edu.utez.servicioEscolar.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VentanillaService {

    @Autowired
    private VentanillaRepository ventanillaRepository;

    ///Todos las ventanillas
    @Transactional(readOnly = true)
    public CustomResponse<List<Ventanilla>> getAll(){
        return new CustomResponse<>(
                this.ventanillaRepository.findAll(),
                false,
                200,
                "Ok"
        );
    }

    ///Servicio para los activos
    @Transactional(readOnly = true)
    public  CustomResponse<List<Ventanilla>> getAllActive(){
        return new CustomResponse<>(
                this.ventanillaRepository.findAllByStatus(true),
                false,
                200,
                "ok"
        );
    }

    ///Servicio para los inactivos
    @Transactional(readOnly = true)
    public  CustomResponse<List<Ventanilla>> getAllInactive(){
        return new CustomResponse<>(
                this.ventanillaRepository.findAllByStatus(false),
                false,
                200,
                "ok"
        );
    }

    ///Ventanilla por id
    @Transactional(readOnly = true)
    public CustomResponse<Ventanilla> getOne(Long id) {
        Optional<Ventanilla> ventOptional = this.ventanillaRepository.findById(id);
        if (ventOptional.isPresent()) {
            return new CustomResponse<>(
                    ventOptional.get(),
                    false,
                    200,
                    "ok"
            );
        } else {
            return new CustomResponse<>(
                    null,
                    true,
                    404,
                    "La ventanilla no existe"
            );
        }
    }

    //Insertar un Ventanilla
    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Ventanilla> insert(Ventanilla ventanilla){
        if (this.ventanillaRepository.existsByCorreoVent(ventanilla.getCorreoVent())) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "El correo ya existe"
            );
        }
        return new CustomResponse<>(
                this.ventanillaRepository.saveAndFlush(ventanilla),
                false,
                200,
                "Ventanilla registrada correctamente"
        );
    }

    //Actualizar un ventanilla
    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Ventanilla> update(Ventanilla ventanilla){
        if(!this.ventanillaRepository.existsById(ventanilla.getId()))
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "Ventanilla no encontrada"
            );
        return new CustomResponse<>(
                this.ventanillaRepository.saveAndFlush(ventanilla),
                false,
                200,
                "Ventanilla actualizada correctamente"
        );
    }

    //Cambiar Status
    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Boolean> changeStatus(Ventanilla ventanilla){
        if(!this.ventanillaRepository.existsById(ventanilla.getId())){
            return new CustomResponse<>(
                    false,
                    true,
                    400,
                    "Ventanilla no encontrada"
            );
        }
        return new CustomResponse<>(
                this.ventanillaRepository.updateStatusById(
                        ventanilla.getStatus(), ventanilla.getId()
                ) == 1,
                false,
                200,
                "Ventanilla actualizada correctamente"
        );
    }
}
