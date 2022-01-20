package se.yrgo.employeasy.vacation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> notFound(ObjectNotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(status.value(), "Not Found", e.getMessage());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(TimeException.class)
    public ResponseEntity<StandardError> time(TimeException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(status.value(), "Old vacation date", e.getMessage());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DoubleBookedException.class)
    public ResponseEntity<StandardError> doubleBooked(DoubleBookedException e) {
        HttpStatus status = HttpStatus.CONFLICT;
        StandardError err = new StandardError(status.value(), "User double booked a date", e.getMessage());
        return ResponseEntity.status(status).body(err);
    }
}
