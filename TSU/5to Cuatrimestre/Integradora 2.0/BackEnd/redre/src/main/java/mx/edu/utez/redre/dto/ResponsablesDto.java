package mx.edu.utez.redre.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.redre.models.asesor.Asesor;
import mx.edu.utez.redre.models.departamento.Departamento;
import mx.edu.utez.redre.models.responsables.Responsables;
//import mx.edu.utez.redre.models.usuario.Usuario;
import mx.edu.utez.redre.utils.ValidarDominio.ValidEmailDomain;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ResponsablesDto {
    private Long id;
    private String nombre;
    private String apellidos;
    @ValidEmailDomain
    private String correo;
    private String password;
    private Boolean status;
    private String divisionAcademica;
    private Departamento departamento;
    private List<Asesor> asesores;
    //private Usuario user;

    public Responsables getResponsable(){
        return new Responsables(
                getId(),
                getNombre(),
                getApellidos(),
                getCorreo(),
                getPassword(),
                getStatus(),
                getDivisionAcademica(),
                getDepartamento(),
                getAsesores()//,
                //getUser()
        );
    }
}
