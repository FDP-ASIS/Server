package il.ac.afeka.fdp.user.config.validation;

import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@javax.validation.Constraint(validatedBy = { NumberValidator.class })
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
public @interface ValidNumber {
    String message() default "Not a number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
