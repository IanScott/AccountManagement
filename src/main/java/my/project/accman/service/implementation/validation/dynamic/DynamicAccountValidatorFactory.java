package my.project.accman.service.implementation.validation.dynamic;

import my.project.accman.service.implementation.validation.dynamic.implementation.*;

/**
 * A Factory responsible for the creation of DynamicAccountValidators.
 * At moment there are two types:
 * - A creation validator
 * - A update validator
 */
public class DynamicAccountValidatorFactory {
    public static final String CREATE = "Create";
    public static final String UPDATE = "Update";

    private DynamicAccountValidatorFactory(){
        //not visable
    }
    private static DynamicAccountValidator createValidator = null;
    private static DynamicAccountValidator updateValidator = null;

    /**
     * Method creates a DynamicAccountValidator.
     * @param type a constant representing the type of validator ('Create' or 'Update').
     * @return a DynamicAccountValidator.
     */
    public static DynamicAccountValidator getValidation(String type){
        if(type == null){
            throw new IllegalArgumentException();
        }

        switch(type){
            case CREATE: return getCreateValidator();
            case UPDATE: return getUpdateValidator();
            default: throw new IllegalArgumentException();
        }
    }

    private static DynamicAccountValidator getCreateValidator(){
        if(createValidator == null){
            createValidator = new CreateDynamicAccountValidator();
        }
        return createValidator;
    }

    private static DynamicAccountValidator getUpdateValidator(){
        if(updateValidator == null){
            updateValidator = new UpdateDynamicAccountValidator();
        }
        return updateValidator;
    }
}
