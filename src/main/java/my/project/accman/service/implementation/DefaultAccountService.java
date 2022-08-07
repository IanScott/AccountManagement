package my.project.accman.service.implementation;

import lombok.AllArgsConstructor;
import my.project.accman.logging.LogMethod;
import my.project.accman.model.Account;
import my.project.accman.exceptions.*;
import my.project.accman.dao.AccountRepository;
import my.project.accman.service.*;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * An default implementation of the Account Service.
 */
@AllArgsConstructor
@Service
public class DefaultAccountService implements AccountService {

    private AccountRepository repository;
    private AccountValidationService validator;

    @LogMethod
    @Override
    public Account findAccountByUUID(String uuid) {
        return this.repository.findByUuidEquals(uuid)
                .orElseThrow(()-> new MissingAccountException(uuid));
    }

    @LogMethod
    @Override
    public Account addAccount(Account account) {
        this.validator.validateAccountOnCreation(account);
        var result = repository.save(account);
        if(result == null){
            throw new InternalException("Failed to add Account to repository");
        }
        return result;
    }

    @LogMethod
    @Override
    public Account updateAccountByUUID(Account updatedAccount, String uuid) {
        this.validator.validateAccountOnUpdate(updatedAccount);
        return updateAccountByUUIDImpl(updatedAccount, uuid)
                .orElseThrow(()-> new MissingAccountException(uuid));
    }

    private Optional<Account> updateAccountByUUIDImpl(Account updatedAccount, String uuid) {
        var persistend = this.repository.findByUuidEquals(uuid);


        return this.repository.findByUuidEquals(uuid)
                .map(account -> updateAccount(account, updatedAccount))
                .map(account -> repository.save(account));
    }

    private Account updateAccount(Account account, Account updatedAccount){
        this.validator.validateAccountModifications(account, updatedAccount);
        account.setClosureDate(updatedAccount.getClosureDate());
        account.setInitialDeposit(updatedAccount.getInitialDeposit());

        if(account.getHolder() == null && updatedAccount.getHolder() == null){
            throw new InternalException();
        }

        var holder = account.getHolder();
        var updatedHolder = updatedAccount.getHolder();

        holder.setFirstName(updatedHolder.getFirstName());
        holder.setLastName(updatedHolder.getLastName());
        holder.setEmail(updatedHolder.getEmail());
        holder.setDateOfBirth(updatedHolder.getDateOfBirth());

        return account;
    }

    @LogMethod
    @Override
    public boolean deleteAccountById(String uuid) {
        Integer count = repository.removeByUuid(uuid);
        if(count != 1){
            throw new MissingAccountException(uuid);
        }
        return true;
    }
}
