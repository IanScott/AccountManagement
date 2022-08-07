package my.project.accman.service.implementation;

import my.project.accman.exceptions.InvalidAccountException;
import my.project.accman.model.Account;
import my.project.accman.model.AccountType;
import my.project.accman.service.AccountValidationService;
import my.project.accman.testutils.TestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static my.project.accman.constants.ValidationMessages.*;
import static org.junit.jupiter.api.Assertions.*;

class DefaultAccountValidationServiceTest {

    private static AccountValidationService validator;

    @BeforeAll
    static void init(){
        validator =  new DefaultAccountValidationService();
    }

    @Test
    void validateAccountOnCreation() {
        Account account = TestUtils.createValidAccount();
        assertDoesNotThrow(()->this.validator.validateAccountOnCreation(account));

        account.setType(null);
        var ex =assertThrows(InvalidAccountException.class,()->this.validator.validateAccountOnCreation(account));
        assertTrue(ex.getContraintViolations().contains(D4));

        account.setInitialDeposit(-1);
        ex =assertThrows(InvalidAccountException.class,()->this.validator.validateAccountOnCreation(account));
        assertTrue(ex.getContraintViolations().contains(A2));

        account.getHolder().setFirstName("JC");
        ex =assertThrows(InvalidAccountException.class,()->this.validator.validateAccountOnCreation(account));
        assertTrue(ex.getContraintViolations().contains(P2));

        account.setHolder(null);
        ex =assertThrows(InvalidAccountException.class,()->this.validator.validateAccountOnCreation(account));
        assertTrue(ex.getContraintViolations().contains(D3));
    }

    @Test
    void validateAccountOnUpdate() {
        Account account = TestUtils.createValidAccount();
        var ex = assertThrows(InvalidAccountException.class, ()->this.validator.validateAccountOnUpdate(account));
        assertTrue(ex.getContraintViolations().contains(D5));

        account.setUuid("dummy-value");
        assertDoesNotThrow(()->this.validator.validateAccountOnUpdate(account));


        account.setType(null);
        assertDoesNotThrow(()->this.validator.validateAccountOnUpdate(account));

        account.setInitialDeposit(-1);
        ex =assertThrows(InvalidAccountException.class,()->this.validator.validateAccountOnUpdate(account));
        assertTrue(ex.getContraintViolations().contains(A2));

        account.getHolder().setFirstName("JC");
        ex =assertThrows(InvalidAccountException.class,()->this.validator.validateAccountOnUpdate(account));
        assertTrue(ex.getContraintViolations().contains(P2));

        account.setInitialDeposit(100);
        account.getHolder().setFirstName("JCB");
        account.setHolder(null);
        assertDoesNotThrow(()->this.validator.validateAccountOnUpdate(account));
    }

    @Test
    void validateAccountModifications() {
        Account account = TestUtils.createValidAccount();
        Account updated = TestUtils.createValidAccount();
        //make sure dates are identical.
        updated.setClosureDate(account.getClosureDate());
        updated.setOpeningDate(account.getOpeningDate());

        assertDoesNotThrow(()->validator.validateAccountModifications(account, updated));

        account.setType(AccountType.CurrentAccount);
        updated.setType(AccountType.Savings);

        var ex =assertThrows(InvalidAccountException.class,()->validator.validateAccountModifications(account, updated));
        System.out.println(ex.getContraintViolations());
        assertTrue(ex.getContraintViolations().contains(M2));

        account.setUuid("123456789");
        updated.setUuid("987654321");

        ex =assertThrows(InvalidAccountException.class,()->validator.validateAccountModifications(account, updated));
        System.out.println(ex.getContraintViolations());
        assertTrue(ex.getContraintViolations().contains(M2));
        assertTrue(ex.getContraintViolations().contains(M1));
    }
}