package my.project.accman.service.implementation.validation.annotations.opening;

import java.util.*;

import my.project.accman.service.implementation.validation.annotations.opening.IsMaxDaysOldAction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IsMaxDaysOldActionTest {

    @Test
    void isMax10DaysOldFalse(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -11);
        var action = new IsMaxDaysOldAction();
        assertFalse(action.performAction(cal.getTime(), new Date(), 10));
    }

    @Test
    void isMax10DaysOldTrue(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -9);
        var action = new IsMaxDaysOldAction();
        assertTrue(action.performAction(cal.getTime(), new Date(), 10));
    }

    @Test
    void is10DaysOld(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -10);
        var action = new IsMaxDaysOldAction();
        assertTrue(action.performAction(cal.getTime(), new Date(), 10));
    }

    @Test
    void is0DaysOld(){
        Calendar cal = Calendar.getInstance();
        var action = new IsMaxDaysOldAction();
        assertTrue(action.performAction(cal.getTime(), new Date(), 0));
    }
}