package utez.edu.mx.foodster.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.sql.Timestamp;

public class FutureOrPresentTimestampValidator implements ConstraintValidator<FutureOrPresentTimestamp, Timestamp> {
    @Override
    public boolean isValid(Timestamp value, ConstraintValidatorContext context) {
        return value == null || value.getTime() >= System.currentTimeMillis();
    }
}