package se.yrgo.employeasy.vacation.exceptions;

public class TimeException extends RuntimeException {
    private static final long serialVersionUID = 2L;

    public TimeException(String message) { super(message); }
}
