package my.project.accman.service.implementation.validation.dynamic.implementation;

import my.project.accman.model.Account;
import my.project.accman.service.implementation.validation.dynamic.DynamicAccountValidator;
import my.project.accman.service.implementation.validation.dynamic.implementation.utils.ValidationUtils;

import java.util.*;

import static my.project.accman.constants.ValidationMessages.*;

/**
 * Validation criteria shared by multiple DynamicAccountValidator.
 */
public abstract class AbstractDynamicAccountValidator implements DynamicAccountValidator {
    /**
     * This Method checks for general validation criteria.
     * @param account the Account to check.
     * @return a set of violation error codes.
     */
    protected Set<String> generalValidation(Account account){
        Set<String> errorMessages = new HashSet<>();

        Date closure = account.getClosureDate();
        Date opening = account.getOpeningDate();

        //If temporary, closure date is mandatory,
        if(account.isTemporary() && closure == null){
            errorMessages.add(D1);
        }

        // it cannot be earlier than opening date + 2months
        var isEarlier = account.isTemporary() &&
                closure != null &&
                ValidationUtils.isBeforePlusMonths(closure, opening, 2);
        if(isEarlier){
            errorMessages.add(D2);
        }

        return errorMessages;
    }
}
