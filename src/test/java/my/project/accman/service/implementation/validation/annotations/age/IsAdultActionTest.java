package my.project.accman.service.implementation.validation.annotations.age;

import my.project.accman.service.implementation.validation.annotations.age.IsAdultAction;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class IsAdultActionTest {

    @Test
    void isAdultZeroYears() {
        var action = new IsAdultAction();
        assertFalse(action.performAction(new Date(), new Date(), 18));
    }

    @Test
    void isAdultNullYears() {
        var action = new IsAdultAction();
        assertFalse(action.performAction(null, new Date(), 18));
    }

    @Test
    void isAdultSevenTeenYears() {
        Calendar cal = Calendar.getInstance();
        var today = cal.getTime();
        cal.add(Calendar.YEAR, -17);
        var seventeenYearsAgo = cal.getTime();
        var action = new IsAdultAction();
        assertFalse(action.performAction(seventeenYearsAgo, today, 18));
    }
    @Test

    void isAdultSevenTeenPlusYears() {
        Calendar cal = Calendar.getInstance();
        var today = cal.getTime();
        cal.add(Calendar.YEAR, -18);
        cal.add(Calendar.DAY_OF_YEAR, 1);
        var seventeenYearsAgo = cal.getTime();
        var action = new IsAdultAction();
        assertFalse(action.performAction(seventeenYearsAgo, today, 18));
    }

    @Test
    void isAdultEightTeenYears() {
        Calendar cal = Calendar.getInstance();
        var today = cal.getTime();
        cal.add(Calendar.YEAR, -18);
        var yearsAgo = cal.getTime();
        var action = new IsAdultAction();
        assertTrue(action.performAction(yearsAgo, today, 18));
    }

    @Test
    void isAdultNineTeenYears() {
        Calendar cal = Calendar.getInstance();
        var today = cal.getTime();
        cal.add(Calendar.YEAR, -19);
        var nineteenYearsAgo = cal.getTime();
        var action = new IsAdultAction();
        assertTrue(action.performAction(nineteenYearsAgo, today, 18));
    }
}