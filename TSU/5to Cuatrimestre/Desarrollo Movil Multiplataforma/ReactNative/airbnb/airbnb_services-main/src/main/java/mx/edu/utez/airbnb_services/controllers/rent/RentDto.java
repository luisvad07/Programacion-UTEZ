package mx.edu.utez.airbnb_services.controllers.rent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.airbnb_services.models.department.Department;
import mx.edu.utez.airbnb_services.models.rent.Rent;
import mx.edu.utez.airbnb_services.models.user.User;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RentDto {
    private Long id;
    @NotEmpty(message = "Campo obligatorio")
    private String description;
    private String initial_date;
    @NotEmpty(message = "Campo obligatorio")
    private String final_date;
    private Department department;
    private User user;

    public Rent getRent(){
        return new Rent(
                getId(),
                getDescription(),
                getInitial_date(),
                getFinal_date(),
                getDepartment(),
                getUser()
        );
    }
}
