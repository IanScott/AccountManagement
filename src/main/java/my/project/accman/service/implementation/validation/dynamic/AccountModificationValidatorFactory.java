package my.project.accman.service.implementation.validation.dynamic;

import my.project.accman.service.implementation.validation.dynamic.implementation.DefaultAccountModificationValidator;

/**
 * A Factory responsible for creating AccountModificationValidators.
 */
public class AccountModificationValidatorFactory {

    private AccountModificationValidatorFactory(){
        //not visable
    }

    /**
     * Method used to get a AccountModificationValidator.
     * @return an AccountModificationValidator.
     */
    public static AccountModificationValidator getValidator(){
        return new DefaultAccountModificationValidator();
    }
}
