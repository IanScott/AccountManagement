package my.project.accman.service.implementation.validation.annotations.age;

import my.project.accman.logging.LogMethod;
import javax.validation.*;
import java.util.Date;

/**
 * A validator used for validating if a person is an adult, based on their date of birth.
 */
public class AdultValidator implements ConstraintValidator<AdultValidation, Date> {
    private int ageOfAdult;

    @Override
    public void initialize(AdultValidation constraintAnnotation) {
        this.ageOfAdult = constraintAnnotation.ageOfAdult();
    }
    @LogMethod
    public boolean isValid(Date dateOfBirth, ConstraintValidatorContext cxt) {
        return new IsAdultAction()
                .performAction(dateOfBirth, new Date(), ageOfAdult);
    }
}