package my.project.accman.service.implementation.validation.dynamic.implementation;

import lombok.NoArgsConstructor;
import my.project.accman.model.Account;
import my.project.accman.service.implementation.validation.dynamic.AccountModificationValidator;
import my.project.accman.service.implementation.validation.dynamic.implementation.utils.ValidationUtils;
import org.springframework.stereotype.Service;
import java.util.*;

import static my.project.accman.constants.ValidationMessages.*;

/**
 * A default implementation of the AccountModificationValidator.
 */
@NoArgsConstructor
@Service
public class DefaultAccountModificationValidator implements AccountModificationValidator {
    @Override
    public Set<String> validate(Account existingAccount, Account updatedAccount){
        Set<String> errorCodes = new HashSet<>();

        // UUID cannot be changed
        if(updatedAccount.getUuid() !=null && !updatedAccount.getUuid().equals(existingAccount.getUuid())){
            errorCodes.add(M1);
        }
        // Type cannot be changed
        if(updatedAccount.getType() !=null && !updatedAccount.getType().equals(existingAccount.getType())){
            errorCodes.add(M2);
        }
        // Opening date cannot be changed
        if(updatedAccount.getOpeningDate() !=null && !updatedAccount.getOpeningDate().equals(existingAccount.getOpeningDate())){
            errorCodes.add(M3);
        }

        // when closing date changed to temporary, it cannot be earlier than modification date + 1 month
        if(!existingAccount.isTemporary() && updatedAccount.isTemporary()){
            Date closure = updatedAccount.getClosureDate();
            if(ValidationUtils.isBeforePlusMonths(closure, new Date(), 1)){
                errorCodes.add(M4);
            }
        }

        return errorCodes;
    }
}
