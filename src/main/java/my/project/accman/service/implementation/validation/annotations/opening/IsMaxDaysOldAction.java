package my.project.accman.service.implementation.validation.annotations.opening;

import java.util.Date;
import static my.project.accman.service.implementation.validation.annotations.utils.DateTimeUtils.*;

/**
 * An action which checks if a date is no more than x days in the past..
 */
public class IsMaxDaysOldAction {

    /**
     * Method used for checking if a date is no more than x days in the past.
     * @param value the date to validate.
     * @param today today's date.
     * @param maxDaysInPast the maximum amount of days in the past.
     * @return true (validated) of false (not validated).
     */
    public boolean performAction(Date value, Date today, int maxDaysInPast){
        if(value == null) return false;

        var dateAsLocaldate = convertToLocalDateTimeViaInstant(value);
        var todayAsLocaldate = convertToLocalDateTimeViaInstant(today);
        return  dateAsLocaldate.isAfter(todayAsLocaldate.minusDays(maxDaysInPast)) ||
                dateAsLocaldate.isEqual(todayAsLocaldate.minusDays(maxDaysInPast));
    }
}
