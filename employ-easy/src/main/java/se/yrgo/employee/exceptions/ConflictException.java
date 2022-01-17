package se.yrgo.employee.exceptions;

public class ConflictException extends RuntimeException {
	
    private static final long serialVersionUID = 2L;

    public ConflictException(String msg) {
        super(msg);
    }
}
