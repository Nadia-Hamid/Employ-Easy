package se.yrgo.employeasy.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ResourceExceptionHandlerTest {

    @Test
    void objectNotFoundTest() {
        ResourceExceptionHandler reh = new ResourceExceptionHandler();
        try{
            throw new ObjectNotFoundException("test");
        } catch (ObjectNotFoundException ex) {
            var response = reh.objectNotFound(ex);
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals("Not Found", response.getBody().getError());
        }
    }

    @Test
    void conflict() {
    }

    @Test
    void nullPointer() {
    }

    @Test
    void illegalArgument() {
    }
}