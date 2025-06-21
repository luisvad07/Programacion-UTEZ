package utez.edu.mx.foodster.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FutureOrPresentTimestampValidator.class)

public @interface FutureOrPresentTimestamp {
    String message() default "The timestamp must be in the future or present";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}