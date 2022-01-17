package se.yrgo.employee.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(status.value(), "Not Found", e.getMessage());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<StandardError> conflict(ConflictException e) {
        HttpStatus status = HttpStatus.CONFLICT;
        StandardError err = new StandardError(status.value(), "Conflict", e.getMessage());
        return ResponseEntity.status(status).body(err);
    }
}
