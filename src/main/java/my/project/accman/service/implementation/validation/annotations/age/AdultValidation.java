package my.project.accman.service.implementation.validation.annotations.age;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

/**
 * A custom annotation used for validating birthday fields.
 */
@Target( { FIELD, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = AdultValidator.class)
public @interface AdultValidation {
    //error message
    public String message() default "Person is not an Adult.";
    public int ageOfAdult() default 18;
    //represents group of constraints
    public Class<?>[] groups() default {};
    //represents additional information about annotation
    public Class<? extends Payload>[] payload() default {};
}
