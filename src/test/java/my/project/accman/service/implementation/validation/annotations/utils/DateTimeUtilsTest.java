package my.project.accman.service.implementation.validation.annotations.utils;

import my.project.accman.service.implementation.validation.annotations.utils.DateTimeUtils;
import org.junit.jupiter.api.Test;


import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeUtilsTest {

    @Test
    void convertToLocalDateTimeViaInstant() {

        var date = new Date();
        var localdate = DateTimeUtils.convertToLocalDateTimeViaInstant(date);
        var newDate = Date.from(localdate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        String pattern = "dd-MM-yy";
        SimpleDateFormat sf = new SimpleDateFormat(pattern);

        boolean equal = sf.format(date).equals(localdate.format(DateTimeFormatter.ofPattern(pattern)));

        assertTrue(equal);
    }
}