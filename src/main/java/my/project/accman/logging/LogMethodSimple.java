package my.project.accman.logging;

import java.lang.annotation.*;

/**
 * Annotation used for logging simple method information.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogMethodSimple { }
