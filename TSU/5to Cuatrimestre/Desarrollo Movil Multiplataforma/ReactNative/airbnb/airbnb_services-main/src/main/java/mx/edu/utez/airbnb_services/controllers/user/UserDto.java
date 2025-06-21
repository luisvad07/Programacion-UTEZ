package mx.edu.utez.airbnb_services.controllers.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.airbnb_services.models.person.Person;
import mx.edu.utez.airbnb_services.models.rent.Rent;
import mx.edu.utez.airbnb_services.models.user.User;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private Long id;
    @NotEmpty(message = "Campo obligatorio")
    @Size(min = 1, max = 255, message = "Máximo 255 carácteres.")
    private String email;
    private String image;
    @NotEmpty(message = "Campo obligatorio")
    private String uid;
    private Person person;

    private List<Rent> rent;

    public User getUser(){
        return new User(
                getId(),
                getEmail(),
                getUid(),
                getImage(),
                getPerson(),
                getRent()
        );
    }
}
