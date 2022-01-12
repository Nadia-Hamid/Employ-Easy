package se.yrgo.employeasy.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResourceExceptionHandlerTest {

    private final ResourceExceptionHandler handlerTest = new ResourceExceptionHandler();

    @Test
    void throwObjectNotFound() {
        try{
            throw new ObjectNotFoundException("try to catch me");
        } catch (ObjectNotFoundException ex) {
            ResponseEntity<StandardError> response = handlerTest.objectNotFound(ex);
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals("Not Found", response.getBody().getError());
        }
    }

    @Test
    void throwConflict() {
        try{
            throw new ConflictException("try to catch me");
        } catch (ConflictException ex) {
            ResponseEntity<StandardError> response = handlerTest.conflict(ex);
            assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
            assertEquals("Conflict", response.getBody().getError());
        }
    }

    @Test
    void throwNullPointer() {
        try{
            throw new NullPointerException("try to catch me");
        } catch (NullPointerException ex) {
            ResponseEntity<StandardError> response = handlerTest.nullPointer(ex);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertEquals("Null Pointer", response.getBody().getError());
        }
    }

    @Test
    void throwIllegalArgument() {
        try {
            throw new IllegalArgumentException("try to catch me");
        } catch (IllegalArgumentException ex) {
            ResponseEntity<StandardError> response = handlerTest.illegalArgument(ex);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertEquals("Illegal Argument", response.getBody().getError());
        }
    }
}