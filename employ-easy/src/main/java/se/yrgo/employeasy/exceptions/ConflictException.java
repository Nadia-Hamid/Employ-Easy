package se.yrgo.employeasy.exceptions;

/**
 * Exception for handling email duplications
 */
public class ConflictException extends RuntimeException {
    private static final long serialVersionUID = 2L;

    public ConflictException(String msg) {
        super(msg);
    }
}
