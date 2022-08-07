package my.project.accman.service.implementation.validation.annotations.age;

import my.project.accman.logging.LogMethod;
import my.project.accman.service.implementation.validation.annotations.utils.DateTimeUtils;

import java.time.*;
import java.util.Date;

/**
 * An action which checks if someone is an adult, based on their birthday.
 */
public class IsAdultAction {
    /**
     * Method used for checking if someone is an adult.
     * @param dateOfBirth their date of birth.
     * @param today today's date.
     * @param ageOfAdult the legal age of an adult.
     * @return true (validated) of false (not validated).
     */
    @LogMethod
    public boolean performAction(Date dateOfBirth, Date today, int ageOfAdult){
        if(dateOfBirth == null) return false;
        var period = Period.between(
                DateTimeUtils.convertToLocalDateTimeViaInstant(dateOfBirth),
                DateTimeUtils.convertToLocalDateTimeViaInstant(today));
        return period.getYears() >= ageOfAdult;
    }
}