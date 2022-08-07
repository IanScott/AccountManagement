package my.project.accman;

import my.project.accman.dao.AccountRepository;
import my.project.accman.model.Account;
import my.project.accman.service.AccountService;
import my.project.accman.service.implementation.DefaultAccountService;
import my.project.accman.service.implementation.DefaultAccountValidationService;
import my.project.accman.testutils.TestUtils;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@Configuration
public class TestConfiguration {

    @Bean
    public AccountService accountService() {
        return new DefaultAccountService(accountRepository(), new DefaultAccountValidationService());
    }

    private AccountRepository accountRepository(){
        var mockAccount = TestUtils.createValidAccount();
        mockAccount.setUuid("1");

        var repository = Mockito.mock(AccountRepository.class);
        when(repository.save(any(Account.class)))
                .thenReturn(mockAccount);

        when(repository.findByUuidEquals("1"))
                .thenReturn(Optional.of(mockAccount));

        when(repository.findByUuidEquals("0"))
                .thenReturn(Optional.empty());

        when(repository.removeByUuid("1"))
                .thenReturn(1);

        when(repository.removeByUuid("0"))
                .thenReturn(0);

        return repository;
    }

}
