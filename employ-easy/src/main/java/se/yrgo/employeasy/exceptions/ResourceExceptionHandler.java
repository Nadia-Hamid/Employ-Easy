package se.yrgo.employeasy.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * class ResourceExceptionHandler
 * abstract Rest API http status converter for exceptions.
 * updated 2022-01-20
 */
@ControllerAdvice
public class ResourceExceptionHandler {

    /**
     * Object not found exception handler method.
     * @param e Exception thrown when object was expected but not found.
     * @return Status, error and message http client response.
     */
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(status.value(), "Not Found", e.getMessage());
        return ResponseEntity.status(status).body(err);
    }

    /**
     * Conflict exception handler method.
     * @param e Exception thrown when duplicates e.g. email was unexpectedly found.
     * @return Status, error and message http client response.
     */
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<StandardError> conflict(ConflictException e) {
        HttpStatus status = HttpStatus.CONFLICT;
        StandardError err = new StandardError(status.value(), "Conflict", e.getMessage());
        return ResponseEntity.status(status).body(err);
    }

    /**
     * Null pointer exception handler method.
     * @param e Exception thrown when object was unexpectedly null.
     * @return Status, error and message http client response.
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<StandardError> nullPointer(NullPointerException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(status.value(), "Null Pointer", e.getMessage());
        return ResponseEntity.status(status).body(err);
    }

    /**
     * Illegal argument exception handler method.
     * @param e Exception thrown when illegal argument was found during creation.
     * @return Status, error and message http client response.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandardError> illegalArgument(IllegalArgumentException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(status.value(), "Illegal Argument", e.getMessage());
        return ResponseEntity.status(status).body(err);
    }
}
