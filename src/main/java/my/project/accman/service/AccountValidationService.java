package my.project.accman.service;

import my.project.accman.exceptions.InvalidAccountException;
import my.project.accman.model.Account;

/**
 * An Service for validating Accounts.
 */
public interface AccountValidationService {
    /**
     * Method which validates Account entities representing new Accounts.
     * @param account the account to validate.
     * @throws InvalidAccountException may throw an InvalidAccountException is validation fails.
     */
    void validateAccountOnCreation(Account account) throws InvalidAccountException;

    /**
     * Method which validates Account entities representing updates for existing Accounts.
     * @param account the update Account to validate.
     * @throws InvalidAccountException may throw an InvalidAccountException is validation fails.
     */
    void validateAccountOnUpdate(Account account) throws InvalidAccountException;

    /**
     * Method compares an existing Account with an Account entity respresenting modifications. And validates the changes.
     * @param original the existing persisted Account.
     * @param update an Account entity representing
     * @throws InvalidAccountException may throw an InvalidAccountException is validation fails.
     */
    void validateAccountModifications(Account original, Account update) throws InvalidAccountException;
}