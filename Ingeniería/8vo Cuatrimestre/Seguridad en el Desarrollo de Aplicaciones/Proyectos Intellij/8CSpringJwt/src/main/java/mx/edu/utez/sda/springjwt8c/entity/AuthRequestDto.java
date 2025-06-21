package mx.edu.utez.sda.springjwt8c.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequestDto {

    private String username;

    private String password;

}
