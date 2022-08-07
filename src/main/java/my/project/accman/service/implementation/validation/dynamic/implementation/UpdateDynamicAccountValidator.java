package my.project.accman.service.implementation.validation.dynamic.implementation;

import my.project.accman.logging.LogMethod;
import my.project.accman.model.Account;

import java.util.Set;

import static my.project.accman.constants.ValidationMessages.D5;

/**
 * A Dynamic Validator for Account Entities representing Update Entities.
 */
public class UpdateDynamicAccountValidator extends AbstractDynamicAccountValidator {

    @LogMethod
    @Override
    public Set<String> validate(Account account) {

        Set<String> constraints = this.generalValidation(account);

        if(account.getUuid() == null){
            constraints.add(D5);
        }
        return constraints;
    }
}
