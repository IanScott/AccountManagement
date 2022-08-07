package my.project.accman.service.implementation.validation.annotations.opening;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

/**
 * A custom annotation used for validating that a date is no more than x days in the past..
 */
@Target( { FIELD, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = OpeningValidator.class)
public @interface OpeningValidation {
    //error message
    public String message() default "Account cannot be opened more than 30 days in the past";
    public int maxDaysInPast() default 30;
    //represents group of constraints
    public Class<?>[] groups() default {};
    //represents additional information about annotation
    public Class<? extends Payload>[] payload() default {};
}
