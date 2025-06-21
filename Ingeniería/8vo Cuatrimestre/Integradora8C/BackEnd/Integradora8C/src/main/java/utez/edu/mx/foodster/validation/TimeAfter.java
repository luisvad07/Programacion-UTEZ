package utez.edu.mx.foodster.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TimeAfterValidator.class)
public @interface TimeAfter {
    String message() default "La fecha de inicio debe ser antes que la fecha de fin";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String start();
    String end();
}