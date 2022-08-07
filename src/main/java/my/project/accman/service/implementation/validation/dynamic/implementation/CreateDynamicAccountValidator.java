package my.project.accman.service.implementation.validation.dynamic.implementation;

import my.project.accman.logging.LogMethod;
import my.project.accman.model.Account;

import java.util.Set;

import static my.project.accman.constants.ValidationMessages.*;

/**
 * A Dynamic Validator for Account Entities representing Creation Entities.
 */
public class CreateDynamicAccountValidator extends AbstractDynamicAccountValidator {

    @LogMethod
    @Override
    public Set<String> validate(Account account) {

        Set<String> errorCodes = this.generalValidation(account);

        //During an create holder is required
        if(account.getHolder() == null) {
            errorCodes.add(D3);
        }

        //Type may not be null
        if(account.getType() == null){
            errorCodes.add(D4);
        }

        return errorCodes;
    }
}
