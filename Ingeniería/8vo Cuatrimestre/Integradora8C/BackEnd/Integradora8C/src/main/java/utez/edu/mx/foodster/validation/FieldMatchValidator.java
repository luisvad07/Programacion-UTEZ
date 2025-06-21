package utez.edu.mx.foodster.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object firstFieldValue = new BeanWrapperImpl(value).getPropertyValue(firstFieldName);
        Object secondFieldValue = new BeanWrapperImpl(value).getPropertyValue(secondFieldName);

        return firstFieldValue == null && secondFieldValue == null || firstFieldValue != null && firstFieldValue.equals(secondFieldValue);
    }
}