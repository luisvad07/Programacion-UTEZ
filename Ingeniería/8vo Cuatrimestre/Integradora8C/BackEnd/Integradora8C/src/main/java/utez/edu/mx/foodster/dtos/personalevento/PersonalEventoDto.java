package utez.edu.mx.foodster.dtos.personalevento;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utez.edu.mx.foodster.entities.eventos.Eventos;
import utez.edu.mx.foodster.entities.personal.Personal;
import utez.edu.mx.foodster.entities.personalevento.PersonalEvento;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PersonalEventoDto {
    private String idPersonalEvento;
    @NotNull(message = "El personal no puede ser nulo")
    private Personal personal;
    @NotNull(message = "El evento no puede ser nulo")
    private Eventos eventos;
    private Timestamp ultimaModificacion;
    private Boolean active;

    public PersonalEvento toEntity(){
        PersonalEvento personalEvento = new PersonalEvento();
        personalEvento.setIdPersonalEvento(this.idPersonalEvento);
        personalEvento.setPersonal(this.personal);
        personalEvento.setEventos(this.eventos);
        personalEvento.setUltimaModificacion(new Timestamp(System.currentTimeMillis()));
        personalEvento.setActive(active);
        return personalEvento;
    }
}
