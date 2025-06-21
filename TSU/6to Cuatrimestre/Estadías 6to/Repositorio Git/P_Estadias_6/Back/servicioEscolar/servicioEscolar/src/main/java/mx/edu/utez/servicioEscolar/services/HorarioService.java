package mx.edu.utez.servicioEscolar.services;

import mx.edu.utez.servicioEscolar.models.horario.Horario;
import mx.edu.utez.servicioEscolar.models.horario.HorarioRepository;
import mx.edu.utez.servicioEscolar.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HorarioService {

    @Autowired
    private HorarioRepository horarioRepository;

    ///Todos los horarios
    @Transactional(readOnly = true)
    public CustomResponse<List<Horario>> getAll(){
        return new CustomResponse<>(
                this.horarioRepository.findAll(),
                false,
                200,
                "Ok"
        );
    }

    ///Servicio para los activos
    @Transactional(readOnly = true)
    public  CustomResponse<List<Horario>> getAllActive(){
        return new CustomResponse<>(
                this.horarioRepository.findAllByStatus(true),
                false,
                200,
                "ok"
        );
    }

    ///Servicio para los inactivos
    @Transactional(readOnly = true)
    public  CustomResponse<List<Horario>> getAllInactive(){
        return new CustomResponse<>(
                this.horarioRepository.findAllByStatus(false),
                false,
                200,
                "ok"
        );
    }

    ///Horario por id
    @Transactional(readOnly = true)
    public CustomResponse<Horario> getOne(Long id){
        Optional<Horario> horarioOptional = this.horarioRepository.findById(id);
        if (horarioOptional.isPresent()){
            return new CustomResponse<>(
                    horarioOptional.get(),
                    false,
                    200,
                    "ok"
            );
        }else {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "Horario no encontrado"
            );
        }
    }

    //Insertar un horario
    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Horario> insert(Horario horario){
        return new CustomResponse<>(
                this.horarioRepository.saveAndFlush(horario),
                false,
                200,
                "Categoría registrada correctamente"
        );
    }

    //Actualizar un horario
    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Horario> update(Horario horario){
        if(!this.horarioRepository.existsById(horario.getId()))
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "Horario no encontrado"
            );
        return new CustomResponse<>(
                this.horarioRepository.saveAndFlush(horario),
                false,
                200,
                "Horario actualizado correctamente"
        );
    }

    //Cambiar Status
    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Boolean> changeStatus(Horario horario){
        if(!this.horarioRepository.existsById(horario.getId())){
            return new CustomResponse<>(
                    false,
                    true,
                    400,
                    "Horario no encontrado"
            );
        }
        return new CustomResponse<>(
                this.horarioRepository.updateStatusById(
                        horario.getStatus(), horario.getId()
                ) == 1,
                false,
                200,
                "Horario actualizado correctamente"
        );
    }
}
