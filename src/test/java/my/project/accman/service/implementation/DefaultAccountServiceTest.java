package my.project.accman.service.implementation;

import my.project.accman.dao.AccountRepository;
import my.project.accman.exceptions.InvalidAccountException;
import my.project.accman.exceptions.MissingAccountException;
import my.project.accman.model.Account;
import my.project.accman.service.AccountService;
import my.project.accman.testutils.TestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import java.util.Date;
import java.util.Optional;

import static my.project.accman.constants.ValidationMessages.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DefaultAccountServiceTest {

    private AccountService service;
    private AccountRepository repository;

    @BeforeAll
    public void init(){
        this.repository = Mockito.mock(AccountRepository.class);
        this.service = new DefaultAccountService(repository, new DefaultAccountValidationService());
    }

    @Test
    void findAccountByUUID() {
        var account = TestUtils.createValidAccount();

        //Scenario 1: Account is found.
        when(repository.findByUuidEquals(anyString())).thenReturn(Optional.of(account));
        assertEquals(account, service.findAccountByUUID("12345"));

        //Scenario 2: Account is not found.
        when(repository.findByUuidEquals(anyString())).thenReturn(Optional.empty());
        assertThrows(MissingAccountException.class, ()->service.findAccountByUUID("12345"));
    }

    @Test
    void addValidAccount() {
        var account = TestUtils.createValidAccount();
        when(repository.save(any(Account.class))).thenReturn(account);
        var result = service.addAccount(account);
        assertEquals(account, result);
    }

    @Test
    void addInValidAccount() {
        var invalidAccount = TestUtils.createValidAccount();
        invalidAccount.setInitialDeposit(-1);

        when(repository.save(any(Account.class))).thenReturn(invalidAccount);

        var exception = assertThrows(InvalidAccountException.class, () -> service.addAccount(invalidAccount));
        assertTrue(exception.getContraintViolations().contains(A2));
    }

    @Test
    void updateAccountByUUID() {
        String uuid = "12345";
        Date openingdate = new Date();
        var persisted = TestUtils.createValidAccount();
        persisted.setUuid(uuid);
        persisted.setOpeningDate(openingdate); // dates need to be identical to the micro second

        var update = TestUtils.createValidAccount();
        update.setUuid(uuid);
        update.setOpeningDate(openingdate); // dates need to be identical to the micro second

        when(repository.save(any(Account.class))).thenReturn(update);
        when(repository.findByUuidEquals(anyString())).thenReturn(Optional.of(persisted));


        //Scenario 1: valid update
        update.setInitialDeposit(1000); //valid update
        assertDoesNotThrow(()->service.updateAccountByUUID(update, uuid));

        //Scenario 2: invalid update object
        update.setUuid(null);
        var exception = assertThrows(InvalidAccountException.class, ()-> service.updateAccountByUUID(update, uuid));
        assertTrue(exception.getContraintViolations().contains(D5));

        //Scenario 3: trying to update non-existing account.
        update.setUuid("12345");
        when(repository.findByUuidEquals(anyString())).thenReturn(Optional.empty());
        assertThrows(MissingAccountException.class, ()->service.updateAccountByUUID(update, uuid));
    }

    @Test
    void deleteAccountById() {
        //Scenario 1: successfully delete account.
        when(repository.removeByUuid(anyString())).thenReturn(1);
        assertDoesNotThrow(()->service.deleteAccountById("12345"));

        //Scenario 2: trying to delete a non-existing account.
        when(repository.removeByUuid(anyString())).thenReturn(0);
        assertThrows(MissingAccountException.class, ()-> service.deleteAccountById("12345"));
    }
}