package my.project.accman.service.implementation.validation.dynamic.implementation.utils;

import java.util.Date;

import static my.project.accman.service.implementation.validation.annotations.utils.DateTimeUtils.convertToLocalDateTimeViaInstant;

/**
 * A utilities class used containing logic for validating Account metadata.
 */
public class ValidationUtils {

    private ValidationUtils(){
        //not visable
    }

    /**
     * The method checks if a Date is no more than an x amount of mouths away from a second date.
     * @param value the date to validate.
     * @param limit the date to compare the value date to.
     * @param numberOfMonths the maximum amount of mounths plus the limet the dates can differ
     * @return true or false.
     */
    public static boolean isBeforePlusMonths(Date value, Date limit, int numberOfMonths){
        if(value == null) return false;

        var dateAsLocaldate = convertToLocalDateTimeViaInstant(value);
        var limitAsLocaldate = convertToLocalDateTimeViaInstant(limit);
        return  dateAsLocaldate.isBefore(limitAsLocaldate.plusMonths(numberOfMonths));
    }
}
