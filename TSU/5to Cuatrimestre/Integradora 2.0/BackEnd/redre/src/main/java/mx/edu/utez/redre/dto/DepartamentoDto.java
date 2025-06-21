package mx.edu.utez.redre.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.redre.models.departamento.Departamento;
import mx.edu.utez.redre.models.responsables.Responsables;
//import mx.edu.utez.redre.models.usuario.Usuario;
import mx.edu.utez.redre.utils.ValidarDominio.ValidEmailDomain;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DepartamentoDto {
    private Long id;
    private String nombre;
    private String apellidos;
    @ValidEmailDomain
    private String correo;
    private String password;
    private String divisionAcademica;
    private Boolean status;
    private List<Responsables> responsables;
    //private Usuario user;

    public Departamento getDepartamento(){
        return new Departamento(
                getId(),
                getNombre(),
                getApellidos(),
                getCorreo(),
                getPassword(),
                getDivisionAcademica(),
                getStatus(),
                getResponsables()
                //getUser()
        );
    }
}
