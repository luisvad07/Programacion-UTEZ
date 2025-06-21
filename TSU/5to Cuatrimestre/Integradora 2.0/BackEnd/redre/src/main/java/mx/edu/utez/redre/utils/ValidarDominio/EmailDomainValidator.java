package mx.edu.utez.redre.utils.ValidarDominio;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailDomainValidator implements ConstraintValidator<ValidEmailDomain, String> {

    private Pattern pattern;

    @Override
    public void initialize(ValidEmailDomain constraintAnnotation) {
        pattern = Pattern.compile(constraintAnnotation.regexp());
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null) {
            return false;
        }

        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            // Validación del dominio @utez.edu.mx
            return email.endsWith("utez.edu.mx");
        }
        return false;
    }
}
