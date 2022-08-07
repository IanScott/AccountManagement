package my.project.accman.service.implementation.validation.annotations.opening;

import my.project.accman.logging.LogMethod;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

/**
 * A validator used for validating if a date is no more than x days in the past.
 */
public class OpeningValidator implements ConstraintValidator<OpeningValidation, Date> {
    private int maxDaysInPast;

    @Override
    public void initialize(OpeningValidation constraintAnnotation) {
        this.maxDaysInPast = constraintAnnotation.maxDaysInPast();
    }

    @LogMethod
    public boolean isValid(Date date, ConstraintValidatorContext cxt) {
        return new IsMaxDaysOldAction()
                .performAction(date, new Date(), maxDaysInPast);
    }
}
