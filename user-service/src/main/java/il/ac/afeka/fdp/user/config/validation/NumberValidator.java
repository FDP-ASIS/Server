package il.ac.afeka.fdp.user.config.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NumberValidator implements ConstraintValidator<ValidNumber, String> {
    @Override
    public void initialize(final ValidNumber constraintAnnotation) {
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        try {
            Long.valueOf(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}