package se.yrgo.employeasy.exceptions;

/**
 * Exception for not found expected objects.
 */
public class ObjectNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ObjectNotFoundException(String msg) {
        super(msg);
    }
}
