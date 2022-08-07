package my.project.accman.service.implementation.validation.annotations;

import my.project.accman.model.Account;
import org.junit.jupiter.api.*;

import javax.validation.*;
import java.util.*;
import java.util.stream.Collectors;

import static my.project.accman.constants.ValidationMessages.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnnotationAccountValidationTest {

    private static Validator validator = null;

    @BeforeAll
    static void init(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validateErrorAccount(){
        var account = new Account();
        account.setInitialDeposit(-1);

        Set<ConstraintViolation<Object>> constraints = validator.validate(account);
        var codes = getErrorCodes(constraints);

        //check for error fields
        assertTrue(codes.contains(A1)); //invalid opening date
        assertTrue(codes.contains(A2)); // invalid initial deposit
        assertTrue(codes.size() == 2);
    }

    @Test
    void validateAccount(){
        var account = new Account();
        account.setOpeningDate(new Date());

        Set<ConstraintViolation<Object>> constraints = validator.validate(account);
        var codes = getErrorCodes(constraints);

        assertTrue(codes.isEmpty());
    }

    @Test
    void validateOpeningDateAccount(){
        var account = new Account();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -29);
        account.setOpeningDate(cal.getTime());

        Set<ConstraintViolation<Object>> constraints = validator.validate(account);
        var codes = getErrorCodes(constraints);

        assertTrue(codes.isEmpty());

        cal.add(Calendar.DAY_OF_YEAR, -1);
        account.setOpeningDate(cal.getTime());

        constraints = validator.validate(account);
        codes = getErrorCodes(constraints);
        assertTrue(codes.isEmpty());


        cal.add(Calendar.DAY_OF_YEAR, -1);
        account.setOpeningDate(cal.getTime());

        constraints = validator.validate(account);
        codes = getErrorCodes(constraints);
        assertTrue(codes.contains(A1)); //invalid opening date
    }

    private Set<String> getErrorCodes(Set<ConstraintViolation<Object>> constraints){
        return constraints.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toSet());
    }
}