package my.project.accman.service.implementation.validation.annotations.utils;

import java.time.*;
import java.util.Date;

/**
 * A utilities class used for converting Date objects.
 */
public class DateTimeUtils {

    private DateTimeUtils(){
        //Class does not need to be initialized.
    }

    /**
     * Method used for converting a Date object into a LocalDate object.
     * @param dateToConvert the value to convert.
     * @return the LocalDate conversion.
     */
    public static LocalDate convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}