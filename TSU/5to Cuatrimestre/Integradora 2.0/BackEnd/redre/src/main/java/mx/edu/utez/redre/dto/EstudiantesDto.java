package mx.edu.utez.redre.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.redre.models.asesor.Asesor;
import mx.edu.utez.redre.models.estudiantes.Estudiantes;
//import mx.edu.utez.redre.models.usuario.Usuario;
import mx.edu.utez.redre.utils.ValidarDominio.ValidEmailDomain;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EstudiantesDto {
    private Long id;
    private String matricula;
    private String nombre;
    private String apellidos;
    @ValidEmailDomain
    private String correo;
    private String password;
    private String divisionAcademica;
    private String carrera;
    private int grado;
    private char grupo;
    private Boolean status;
    private String url;
    private String reporteStatus;
    private Asesor asesor;
    //private Usuario user;

    public Estudiantes getEstudiantes(){
        return new Estudiantes(
            getId(),
            getMatricula(),
            getNombre(),
            getApellidos(),
            getCorreo(),
            getPassword(),
            getDivisionAcademica(),
            getCarrera(),
            getGrado(),
            getGrupo(),
            getStatus(),
            getUrl(),
            getReporteStatus(),
            getAsesor()
        );
    }
}
