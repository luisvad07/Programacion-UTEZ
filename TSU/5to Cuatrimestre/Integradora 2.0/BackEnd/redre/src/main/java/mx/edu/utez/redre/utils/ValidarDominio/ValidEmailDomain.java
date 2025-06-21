package mx.edu.utez.redre.utils.ValidarDominio;

import java.lang.annotation.*;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailDomainValidator.class)
@Documented
public @interface ValidEmailDomain {

    String message() default "Dominio del Correo Electrónico Invalido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String regexp() default "^[A-Za-z0-9+_.-]+@(.+)$";
}
