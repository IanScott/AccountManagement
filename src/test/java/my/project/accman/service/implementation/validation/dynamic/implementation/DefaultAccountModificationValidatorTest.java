package my.project.accman.service.implementation.validation.dynamic.implementation;

import my.project.accman.model.*;
import my.project.accman.testutils.TestUtils;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.Date;

import static my.project.accman.constants.ValidationMessages.*;
import static org.junit.jupiter.api.Assertions.*;

class DefaultAccountModificationValidatorTest {

    private DefaultAccountModificationValidator validator = null;

    @Test
    void validate() {
        var original = TestUtils.createValidAccount();
        var updated = TestUtils.createValidAccount();
        updated.setUuid(null);
        updated.setOpeningDate(null);
        updated.setType(null);

        assertTrue(validate(original, updated).isEmpty());

        updated.setHolder(null);
        assertTrue(validate(original, updated).isEmpty());

        updated.setInitialDeposit(35);
        assertTrue(validate(original, updated).isEmpty());
    }

    @Test
    void inValidate() {
        var original = TestUtils.createValidAccount();
        original.setType(AccountType.CurrentAccount);

        var updated = TestUtils.createValidAccount();
        updated.setUuid(null);
        updated.setOpeningDate(null);
        original.setType(AccountType.Savings);

        var constraints = validate(original, updated);
        assertTrue(constraints.size() == 1);
        assertTrue(constraints.contains(M2));

        updated.setUuid("dummy");
        constraints = validate(original, updated);
        assertTrue(constraints.size() == 2);
        assertTrue(constraints.contains(M1));

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -10);
        updated.setOpeningDate(cal.getTime());

        constraints = validate(original, updated);
        assertTrue(constraints.size() == 3);
        assertTrue(constraints.contains(M3));

        original.setTemporary(false);
        updated.setTemporary(true);
        updated.setClosureDate(new Date());

        constraints = validate(original, updated);
        assertTrue(constraints.size() == 4);
        assertTrue(constraints.contains(M4));
    }

    private Set<String> validate(Account original, Account update){
        if(this.validator == null){
            this.validator = new DefaultAccountModificationValidator();
        }
        return this.validator.validate(original, update);
    }
}