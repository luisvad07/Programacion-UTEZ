package mx.edu.utez.airbnb_services.controllers.person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.airbnb_services.models.person.Person;
import mx.edu.utez.airbnb_services.models.user.User;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PersonDto {
    private Long id;
    @Size(min = 1, max = 255, message = "Máximo 255 carácteres.")
    private String fullname;
    private String birthday;
    private User user;
    public Person getPerson(){
        return new Person(
                getId(),
                getFullname(),
                getBirthday(),
                getUser()
        );
    }
}
