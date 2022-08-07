package my.project.accman.service.implementation.validation.annotations;

import my.project.accman.model.Person;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.*;
import java.util.*;
import java.util.stream.Collectors;

import static my.project.accman.constants.ValidationMessages.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class AnnotationPersonValidationTest {

    //Test static validation annnotations here

    private static Validator validator = null;

    @BeforeAll
    static void init(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validateEmptyPerson(){
        var person = new Person();
        Set<ConstraintViolation<Object>> constraints = validator.validate(person);
        var codes = getErrorCodes(constraints);
        //check for mandatory fields
        assertTrue(codes.contains(P1));
        assertTrue(codes.contains(P3));
        assertTrue(codes.contains(P5));
        assertTrue(codes.contains(P6));
        assertTrue(codes.contains(P7));
    }

    @Test
    void validateErrorPerson(){
        var person = new Person();
        person.setFirstName("Ab"); // to short P2
        person.setLastName("Ab"); // too short P4
        person.setDateOfBirth(new Date()); // not 18
        person.setEmail(""); // empty email P7

        Set<ConstraintViolation<Object>> constraints = validator.validate(person);
        var codes = getErrorCodes(constraints);

        //check for error fields
        assertTrue(codes.contains(P2));
        assertTrue(codes.contains(P4));
        assertTrue(codes.contains(P6));
        assertTrue(codes.contains(P7));
    }

    @Test
    void validateInvalidEmail(){
        var person = createCorrectPerson();
        person.setEmail("test"); // P8

        Set<ConstraintViolation<Object>> constraints = validator.validate(person);
        var codes = getErrorCodes(constraints);

        //check for error email field
        assertTrue(codes.contains(P8));
        assertTrue(codes.size() == 1);
    }

    @Test
    void validPerson(){
        var person = createCorrectPerson();

        Set<ConstraintViolation<Object>> constraints = validator.validate(person);
        var codes = getErrorCodes(constraints);

        assertTrue(codes.isEmpty());
    }


    @Test
    void namesTooLong(){
        var person = createCorrectPerson();
        person.setFirstName("1234567890123456789012345678901234567"); //37 length
        person.setLastName("1234567890123456789012345678901234567"); //37 length

        Set<ConstraintViolation<Object>> constraints = validator.validate(person);
        var codes = getErrorCodes(constraints);

        //check for error fields
        assertTrue(codes.contains(P2));
        assertTrue(codes.contains(P4));
        assertTrue(codes.size() == 2);
    }

    @Test
    void namesTooLong2(){
        var person = createCorrectPerson();
        person.setFirstName("123456789012345678901234567890123456"); //36 length
        person.setLastName("123456789012345678901234567890123456"); //36 length

        Set<ConstraintViolation<Object>> constraints = validator.validate(person);
        var codes = getErrorCodes(constraints);

        //check for error fields
        assertTrue(codes.contains(P2));
        assertTrue(codes.contains(P4));
        assertTrue(codes.size() == 2);
    }

    @Test
    void namesMaxLength(){
        var person = createCorrectPerson();
        person.setFirstName("12345678901234567890123456789012345"); //35 length
        person.setLastName("12345678901234567890123456789012345"); //35 length

        Set<ConstraintViolation<Object>> constraints = validator.validate(person);
        var codes = getErrorCodes(constraints);

        //check for no error fields
        assertTrue(codes.size() == 0);
    }
    private Set<String> getErrorCodes(Set<ConstraintViolation<Object>> constraints){
        return constraints.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toSet());
    }

    private Person createCorrectPerson(){
        var person = new Person();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -18);
        person.setFirstName("Abc");
        person.setLastName("Abc");
        person.setDateOfBirth(cal.getTime());
        person.setEmail("test@test.com");
        return person;
    }
}
