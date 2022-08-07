package my.project.accman.api.controller;

import lombok.AllArgsConstructor;
import my.project.accman.logging.LogExecutionTime;
import my.project.accman.logging.LogMethodSimple;
import my.project.accman.model.Account;
import my.project.accman.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.Callable;

/**
 * Rest Controller responsible for all operations concerning Accounts.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/v1/account")
public class AccountController {

    private AccountService accountService;

    /**
     * An asynchronous method for retrieving an Account.
     * @param uuid the UUID of the account.
     * @return the account.
     */
    @LogMethodSimple
    @GetMapping("/{uuid}")
    public Callable<Account> getAccount(@PathVariable String uuid) {
        return () -> getAccountImpl(uuid);
    }

    @LogExecutionTime
    private Account getAccountImpl(String uuid) {
        return this.accountService
                .findAccountByUUID(uuid);
    }

    /**
     * An asynchronous method for persisting a new Account.
     * @param newAccount the account to persist.
     * @return echoes the account if successful.
     */
    @LogMethodSimple
    @PostMapping("/")
    public Callable<Account> addAccount(@RequestBody Account newAccount) {
        return () -> addAccountImpl(newAccount);
    }

    @LogExecutionTime
    private Account addAccountImpl(Account newAccount) {
        return this.accountService
                .addAccount(newAccount);
    }

    /**
     * An asynchronous method for updating an existing Account.
     * @param updatedAccount the account meta data to update.
     * @param uuid the UUID of the account to update.
     * @return echoes the account if successful.
     */
    @LogMethodSimple
    @PutMapping("/{uuid}")
    public Callable<Account> updateAccount(@RequestBody Account updatedAccount, @PathVariable String uuid) {
        return () -> updateAccountImpl(updatedAccount, uuid);
    }

    @LogExecutionTime
    private Account updateAccountImpl(Account updatedAccount, String uuid) {
        return this.accountService
                .updateAccountByUUID(updatedAccount, uuid);
    }

    /**
     * An asynchronous method for deleting an Account.
     * @param uuid the UUID of the account to be deleted.
     * @return an Object containing the status of the operation.
     */
    @LogMethodSimple
    @DeleteMapping("/{uuid}")
    public Callable<Map<String, Boolean>> deleteAccount(@PathVariable String uuid) {
        return () -> deleteAccountImpl(uuid);
    }

    @LogExecutionTime
    private Map<String, Boolean> deleteAccountImpl(String uuid) {
        var success = this.accountService
                .deleteAccountById(uuid);
        return Collections.singletonMap("success", success);
    }
}