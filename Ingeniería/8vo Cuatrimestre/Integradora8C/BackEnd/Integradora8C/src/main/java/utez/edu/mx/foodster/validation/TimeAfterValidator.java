package utez.edu.mx.foodster.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

import java.sql.Timestamp;
import java.util.Calendar;

public class TimeAfterValidator implements ConstraintValidator<TimeAfter, Object> {
    private String startFieldName;
    private String endFieldName;

    @Override
    public void initialize(TimeAfter constraintAnnotation) {
        this.startFieldName = constraintAnnotation.start();
        this.endFieldName = constraintAnnotation.end();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Timestamp startTime = (Timestamp) new BeanWrapperImpl(value).getPropertyValue(startFieldName);
        Timestamp endTime = (Timestamp) new BeanWrapperImpl(value).getPropertyValue(endFieldName);

        // Check if the start and end times are not before the current time minus one day
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentTime);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Timestamp currentTimeMinusOneDay = new Timestamp(calendar.getTimeInMillis());

        if ((startTime != null && startTime.before(currentTimeMinusOneDay)) || (endTime != null && endTime.before(currentTimeMinusOneDay))) {
            return false;
        }

        return startTime == null || endTime == null || endTime.after(startTime);
    }
}