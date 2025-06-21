package com.school.citas.dtos;

import com.school.citas.models.Administrador.Administrador;
import com.school.citas.models.Cita.Cita;
import com.school.citas.models.Horario.HorarioVentanilla;
import com.school.citas.models.Ventanilla.Ventanilla;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VentanillaDto {

    private Long id;
    private String nombreVent;
    private String apePaternoVent;
    private String apeMaternoVent;
    @NotEmpty(message = "Campo Obligatorio")
    private String correoElectronico;
    @NotEmpty(message = "Campo Obligatorio")
    @Length(min = 1, max = 20)
    private String pass;
    private Boolean status;
    private Boolean changePassword;

    private Administrador admin;
    private List<HorarioVentanilla> horarioventanilla;
    private List<Cita> citas;

    public Ventanilla getVentanilla(){
        return new Ventanilla(
                getId(),
                getNombreVent(),
                getApePaternoVent(),
                getApeMaternoVent(),
                getCorreoElectronico(),
                getPass(),
                getStatus(),
                getChangePassword(),
                getAdmin(),
                getHorarioventanilla(),
                getCitas()
        );
    }
}