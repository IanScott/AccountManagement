package my.project.accman.service.implementation.validation.dynamic;

import my.project.accman.model.Account;

import java.util.Set;

/**
 * A validator for validating Accounts.
 */
public interface DynamicAccountValidator {
    /**
     * Method for validating an Account entity.
     * @param account the existing account
     * @return a set of Validation Error Codes.
     */
    Set<String> validate(Account account);
}
