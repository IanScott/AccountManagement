package my.project.accman.service.implementation.validation.dynamic.implementation;

import my.project.accman.service.implementation.validation.dynamic.DynamicAccountValidatorFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DynamicAccountValidatorFactoryTest {

    @Test
    void getCreateValidation() {
        var validation = DynamicAccountValidatorFactory.getValidation(DynamicAccountValidatorFactory.CREATE);
        assertTrue(validation instanceof CreateDynamicAccountValidator);
    }

    @Test
    void getUpdateValidation() {
        var validation = DynamicAccountValidatorFactory.getValidation(DynamicAccountValidatorFactory.UPDATE);
        assertTrue(validation instanceof UpdateDynamicAccountValidator);
    }

    @Test
    void error() {
        assertThrows(IllegalArgumentException.class, ()-> DynamicAccountValidatorFactory.getValidation(null));
        assertThrows(IllegalArgumentException.class, ()-> DynamicAccountValidatorFactory.getValidation(""));
    }
}