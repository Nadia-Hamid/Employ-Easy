package se.yrgo.employeasy.vacation.exceptions;

public class DoubleBookedException extends RuntimeException {
    private static final long serialVersionUID = 3L;

    public DoubleBookedException(String msg) {
        super(msg);
    }

}
