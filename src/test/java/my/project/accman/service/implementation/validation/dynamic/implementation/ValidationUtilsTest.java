package my.project.accman.service.implementation.validation.dynamic.implementation;

import java.util.*;

import my.project.accman.service.implementation.validation.dynamic.implementation.utils.ValidationUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilsTest {

    @Test
    void earlierThanPlusMonths() {
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();

        assertTrue(ValidationUtils.isBeforePlusMonths(today, today,  1));
        assertFalse(ValidationUtils.isBeforePlusMonths(today, today,  0));

        cal.add(Calendar.MONTH, -2);
        Date twoMonthsAgo = cal.getTime();

        assertTrue(ValidationUtils.isBeforePlusMonths(twoMonthsAgo, today,  1));
        assertTrue(ValidationUtils.isBeforePlusMonths(twoMonthsAgo, today,  0));

        assertFalse(ValidationUtils.isBeforePlusMonths(twoMonthsAgo, today,  -2));

        assertFalse(ValidationUtils.isBeforePlusMonths(today, twoMonthsAgo,  2));

        assertTrue(ValidationUtils.isBeforePlusMonths(today, twoMonthsAgo,  3));

        assertFalse(ValidationUtils.isBeforePlusMonths(null, twoMonthsAgo,  3));
    }
}