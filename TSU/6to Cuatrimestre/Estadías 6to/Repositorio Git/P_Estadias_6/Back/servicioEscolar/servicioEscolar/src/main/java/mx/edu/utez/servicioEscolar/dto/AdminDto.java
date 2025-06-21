package mx.edu.utez.servicioEscolar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.servicioEscolar.models.admin.Admin;
import mx.edu.utez.servicioEscolar.models.servicio.Servicio;
import mx.edu.utez.servicioEscolar.models.solicitante.Solicitante;
import mx.edu.utez.servicioEscolar.models.ventanilla.Ventanilla;

import javax.persistence.Column;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AdminDto {

    private Long id;
    private String nombreAdmin;
    private String apeMaternoAdmin;//No todos tienen dos apellidos
    private String apePaternoAdmin;
    //@ValidEmailDomain
    private String correoAdmin;
    private String passwordAdmin;
    private Boolean status;

    private List<Ventanilla> ventanillas;
    private List<Solicitante> solicitantes;
    private List<Servicio> servicios;

    public Admin getAdmin(){
        return new Admin(
                getId(),
                getNombreAdmin(),
                getApeMaternoAdmin(),
                getApePaternoAdmin(),
                getCorreoAdmin(),
                getPasswordAdmin(),
                getStatus(),
                getVentanillas(),
                getSolicitantes(),
                getServicios()
        );
    }
}
