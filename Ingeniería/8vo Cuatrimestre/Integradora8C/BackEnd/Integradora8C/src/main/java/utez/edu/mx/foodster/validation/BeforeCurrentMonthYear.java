package utez.edu.mx.foodster.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BeforeCurrentMonthYearValidator.class)
public @interface BeforeCurrentMonthYear {
    String message() default "La fecha debe ser antes del mes y año actual";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}