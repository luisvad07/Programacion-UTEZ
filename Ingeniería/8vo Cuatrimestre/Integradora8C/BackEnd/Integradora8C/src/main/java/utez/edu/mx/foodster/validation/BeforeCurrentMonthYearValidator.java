package utez.edu.mx.foodster.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.sql.Date;
import java.time.LocalDate;

public class BeforeCurrentMonthYearValidator implements ConstraintValidator<BeforeCurrentMonthYear, Date> {

    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        LocalDate date = value.toLocalDate();
        LocalDate now = LocalDate.now();

        return date.getYear() < now.getYear() || (date.getYear() == now.getYear() && date.getMonthValue() < now.getMonthValue());
    }
}