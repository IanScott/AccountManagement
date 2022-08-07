package my.project.accman.logging;

import java.lang.annotation.*;

/**
 * Annotation used for logging generic method information.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogMethod { }