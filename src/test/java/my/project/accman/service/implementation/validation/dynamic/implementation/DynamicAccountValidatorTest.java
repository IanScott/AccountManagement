package my.project.accman.service.implementation.validation.dynamic.implementation;

import java.util.*;

import my.project.accman.service.implementation.validation.dynamic.DynamicAccountValidator;
import my.project.accman.service.implementation.validation.dynamic.DynamicAccountValidatorFactory;
import my.project.accman.testutils.TestUtils;
import org.junit.jupiter.api.*;

import static my.project.accman.constants.ValidationMessages.*;
import static org.junit.jupiter.api.Assertions.*;

public class DynamicAccountValidatorTest {

    private static DynamicAccountValidator createValidator;
    private static DynamicAccountValidator updateValidator;

    @BeforeAll
    static void init(){
        createValidator = DynamicAccountValidatorFactory.getValidation(DynamicAccountValidatorFactory.CREATE);
        updateValidator = DynamicAccountValidatorFactory.getValidation(DynamicAccountValidatorFactory.UPDATE);
    }

    @Test
    void correctClosuredate(){
        var account = TestUtils.createValidAccount();
        var update = TestUtils.createValidAccount();
        update.setUuid("12345");

        assertTrue(createValidator.validate(account).isEmpty());
        assertTrue(updateValidator.validate(update).isEmpty());

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH ,4);
        Date fourMonthsInFuture = cal.getTime();
        account.setClosureDate(fourMonthsInFuture);
        update.setClosureDate(fourMonthsInFuture);

        assertTrue(createValidator.validate(account).isEmpty());
        assertTrue(updateValidator.validate(update).isEmpty());

        //temporary false
        account.setTemporary(false);
        account.setClosureDate(null);
        update.setTemporary(false);
        update.setClosureDate(null);

        assertTrue(createValidator.validate(account).isEmpty());
        assertTrue(updateValidator.validate(update).isEmpty());
    }

    @Test
    void inCorrectClosuredate(){
        var account = TestUtils.createValidAccount();
        account.setClosureDate(null);

        //temporary true
        account.setTemporary(true);
        var constraints =createValidator.validate(account);
        assertTrue(constraints.contains(D1));
        constraints = updateValidator.validate(account);
        assertTrue(constraints.contains(D1));

        account.setClosureDate(account.getOpeningDate());
        constraints = createValidator.validate(account);
        assertTrue(constraints.contains(D2));

        constraints = updateValidator.validate(account);
        assertTrue(constraints.contains(D2));

        //temporary false
        account.setTemporary(false);
        assertTrue(createValidator.validate(account).isEmpty());

        account.setUuid("123456");
        assertTrue(updateValidator.validate(account).isEmpty());

        account.setClosureDate(null);
        account.setUuid(null);
        assertTrue(createValidator.validate(account).isEmpty());
        account.setUuid("123456");
        assertTrue(updateValidator.validate(account).isEmpty());
    }

    @Test
    void validateHolderField(){
        var account = TestUtils.createValidAccount();
        var update = TestUtils.createValidAccount();
        update.setUuid("12345");

        //create holder mandatory
        assertTrue(createValidator.validate(account).isEmpty());
        assertTrue(updateValidator.validate(update).isEmpty());

        //update holder optional
        account.setHolder(null);
        update.setHolder(null);
        assertTrue(updateValidator.validate(update).isEmpty());

        //Exception should be thrown
        var constraints = createValidator.validate(account);
        assertTrue(constraints.contains(D3));
    }

    @Test
    void uuidIsMandatoryWhenUpdating(){
        var account = TestUtils.createValidAccount();
        account.setUuid(null);

        var constraints = updateValidator.validate(account);
        assertTrue(constraints.contains(D5));

        account.setUuid("dummy-value");
        assertTrue(updateValidator.validate(account).isEmpty());
    }
}