package utez.edu.mx.movies.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CustomResponse<T>{

    private T data;
    private boolean error;
    private int status;
    private String message;
}