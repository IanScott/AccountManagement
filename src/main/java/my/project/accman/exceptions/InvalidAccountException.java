package my.project.accman.exceptions;

import lombok.*;
import java.util.*;

/**
 * Exception thrown if Account objects are invalid.
 * This Exception contains a list of all the constraint found constraint violations.
 */
public class InvalidAccountException extends RuntimeException{
    @Getter @Setter
    private Set<String> contraintViolations = new HashSet<>();
}
