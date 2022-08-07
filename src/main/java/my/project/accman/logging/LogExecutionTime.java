package my.project.accman.logging;

import java.lang.annotation.*;

/**
 * Annotation used for logging the execution time of a method.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogExecutionTime {
}
