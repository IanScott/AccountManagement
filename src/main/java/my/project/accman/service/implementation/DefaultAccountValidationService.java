package my.project.accman.service.implementation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.project.accman.exceptions.InvalidAccountException;
import my.project.accman.logging.LogMethod;
import my.project.accman.model.Account;
import my.project.accman.service.AccountValidationService;
import my.project.accman.service.implementation.validation.dynamic.*;
import org.springframework.stereotype.Service;

import javax.validation.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A default implementation of the AccountValidationService
 */
@AllArgsConstructor
@Slf4j
@Service
public class DefaultAccountValidationService implements AccountValidationService {

    @Override
    @LogMethod
    public void validateAccountOnCreation(Account account){
        var constraints = staticValidation(account);
        constraints.forEach(x->log.info(x));
        var validator = DynamicAccountValidatorFactory.getValidation(DynamicAccountValidatorFactory.CREATE);
        constraints.addAll(validator.validate(account));
        if(!constraints.isEmpty()){
            throwInvalidAccountException(constraints);
        }
    }

    @Override
    @LogMethod
    public void validateAccountOnUpdate(Account account){
        var constraints = staticValidation(account);
        var validator = DynamicAccountValidatorFactory.getValidation(DynamicAccountValidatorFactory.UPDATE);
        constraints.addAll(validator.validate(account));

        if(!constraints.isEmpty()){
            throwInvalidAccountException(constraints);
        }
    }

    @Override
    @LogMethod
    public void validateAccountModifications(Account original, Account update){

        var validator = AccountModificationValidatorFactory.getValidator();
        var constraints = validator.validate(original, update);

        if(!constraints.isEmpty()){
            throwInvalidAccountException(constraints);
        }
    }

    @LogMethod
    private void throwInvalidAccountException(Set<String> constraints){
        var ex = new InvalidAccountException();
        ex.setContraintViolations(constraints);
        throw ex;
    }

    private Set<String> staticValidation(Object object){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        var validator = factory.getValidator();
        Set<ConstraintViolation<Object>> constraints = validator.validate(object);

        if(object instanceof Account){
            Account acc = ((Account)object);
            if(acc.getHolder() != null){
                Set<ConstraintViolation<Object>> temp = validator.validate(acc.getHolder());
                constraints.addAll(temp);
            }
        }

        return constraints.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toSet());
    }
}