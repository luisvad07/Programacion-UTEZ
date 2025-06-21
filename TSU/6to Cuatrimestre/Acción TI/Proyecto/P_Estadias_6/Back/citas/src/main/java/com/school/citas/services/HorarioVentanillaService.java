package com.school.citas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.school.citas.models.Horario.HorarioVentanilla;
import com.school.citas.models.Horario.HorarioVentanillaRepository;
import com.school.citas.utils.CustomResponse;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class HorarioVentanillaService {
    @Autowired
    private HorarioVentanillaRepository horarioVentanillaRepository;

    ///Todos los horarios
    @Transactional(readOnly = true)
    public CustomResponse<List<HorarioVentanilla>> getAll(){
        return new CustomResponse<>(
                this.horarioVentanillaRepository.findAll(),
                false,
                200,
                "Ok"
        );
    }

    ///Servicio para los activos
    @Transactional(readOnly = true)
    public  CustomResponse<List<HorarioVentanilla>> getAllActive(){
        return new CustomResponse<>(
                this.horarioVentanillaRepository.findAllByStatus(true),
                false,
                200,
                "Ok"
        );
    }

    ///Servicio para los inactivos
    @Transactional(readOnly = true)
    public  CustomResponse<List<HorarioVentanilla>> getAllInactive(){
        return new CustomResponse<>(
                this.horarioVentanillaRepository.findAllByStatus(false),
                false,
                200,
                "Ok"
        );
    }

    ///Horario por id
    @Transactional(readOnly = true)
    public CustomResponse<HorarioVentanilla> getOne(Long id){
        Optional<HorarioVentanilla> horarioOptional = this.horarioVentanillaRepository.findById(id);
        if (horarioOptional.isPresent()){
            return new CustomResponse<>(
                    horarioOptional.get(),
                    false,
                    200,
                    "Ok"
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
    public CustomResponse<HorarioVentanilla> insert(HorarioVentanilla horario){
        return new CustomResponse<>(
                this.horarioVentanillaRepository.saveAndFlush(horario),
                false,
                200,
                "Horario registrado correctamente"
        );
    }

    //Actualizar un horario
    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<HorarioVentanilla> update(HorarioVentanilla horario){
        if(!this.horarioVentanillaRepository.existsById(horario.getId()))
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "Horario no encontrado"
            );
        return new CustomResponse<>(
                this.horarioVentanillaRepository.saveAndFlush(horario),
                false,
                200,
                "Horario actualizado correctamente"
        );
    }

    //Cambiar Status
    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Boolean> changeStatus(HorarioVentanilla horario){
        if(!this.horarioVentanillaRepository.existsById(horario.getId())){
            return new CustomResponse<>(
                    false,
                    true,
                    400,
                    "Horario no encontrado"
            );
        }
        return new CustomResponse<>(
                this.horarioVentanillaRepository.updateStatusById(
                        horario.getStatus(), horario.getId()
                ) == 1,
                false,
                200,
                "¡Se ha cambiado el status del Hoarario correctamente!"
        );
    }
}
