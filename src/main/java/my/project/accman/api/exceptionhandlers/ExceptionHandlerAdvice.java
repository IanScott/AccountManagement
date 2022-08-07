package my.project.accman.api.exceptionhandlers;

import my.project.accman.constants.ValidationMessages;
import my.project.accman.exceptions.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * An ControllerAdvice object responsible for handling Exception thrown by the RestControllers.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MissingAccountException.class)
    public ResponseEntity<Object> handleMissingAccountException(MissingAccountException ex) {
        return createErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(InvalidAccountException.class)
    public ResponseEntity<Object> handleInvalidAccountException(InvalidAccountException ex) {
        var list = ex.getContraintViolations().stream()
                .map(ValidationMessages::codeToMessage)
                .collect(Collectors.toList());

        return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InternalException.class)
    public ResponseEntity<Object> handleUnknownException(InternalException ex) {
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed JSON request";
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    private ResponseEntity<Object> createErrorResponse(HttpStatus status, String message) {
        var errorResponse = new HashMap();
        errorResponse.put("status", status.value());
        errorResponse.put("message", message);
        return new ResponseEntity<>(errorResponse, status);
    }
}