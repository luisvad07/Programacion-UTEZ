package com.school.citas.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.school.citas.models.Administrador.Administrador;
import com.school.citas.models.Servicio.Servicio;
import com.school.citas.models.Solicitante.Solicitante;
import com.school.citas.models.Ventanilla.Ventanilla;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AdminDto {

    private Long id;
    private String nombreAdmin;
    private String apePaternoAdmin;
    private String apeMaternoAdmin;//No todos tienen dos apellidos
    @NotEmpty(message = "Campo Obligatorio")
    private String correoAdmin;
    @NotEmpty(message = "Campo Obligatorio")
    @Length(min = 1, max = 20)
    private String pass;
    private Boolean status;
    private Boolean changePassword;

    private List<Ventanilla> ventanillas;
    private List<Solicitante> solicitantes;
    private List<Servicio> servicios;

    public Administrador getAdmin(){
        return new Administrador(
                getId(),
                getNombreAdmin(),
                getApePaternoAdmin(),
                getApeMaternoAdmin(),
                getCorreoAdmin(),
                getPass(),
                getStatus(),
                getChangePassword(),
                getVentanillas(),
                getSolicitantes(),
                getServicios()

        );
    }
}
