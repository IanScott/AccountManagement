package my.project.accman.exceptions;

/**
 * A generic Exception used unexpected Errors.
 */
public class InternalException extends RuntimeException {
    /**
     * Constructor with no Args.
     */
    public InternalException(){
        super("Something unknown has gone wrong.");
    }

    /**
     * Constructor
     * @param message custom error message.
     */
    public InternalException(String message){
        super(message);
    }
}