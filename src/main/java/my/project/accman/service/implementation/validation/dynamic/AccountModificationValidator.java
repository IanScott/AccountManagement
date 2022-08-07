package my.project.accman.service.implementation.validation.dynamic;

import my.project.accman.model.Account;
import java.util.Set;

/**
 * A validator for validating potential Account modifications.
 */
public interface AccountModificationValidator {
    /**
     * Method for validating potential Account modifications.
     * @param existingAccount the existing account
     * @param updatedAccount an entity representing the potential changes.
     * @return a set of Validation Error Codes.
     */
    Set<String> validate(Account existingAccount, Account updatedAccount);
}
