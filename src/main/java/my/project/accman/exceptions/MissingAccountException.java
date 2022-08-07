package my.project.accman.exceptions;

/**
 * Simple Exception thrown if the queried Account can not be found.
 */
public class MissingAccountException extends RuntimeException {
    public MissingAccountException(String uuid){
        super("Could not find Account with uuid:" + uuid );
    }
}
