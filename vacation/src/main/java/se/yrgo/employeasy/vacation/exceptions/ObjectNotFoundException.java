package se.yrgo.employeasy.vacation.exceptions;

public class ObjectNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 5L;

    public ObjectNotFoundException(String msg) {
        super(msg);
    }
}

