package se.yrgo.employeasy.vacation.exceptions;

public class JobTitleNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public JobTitleNotFoundException(String msg) {
        super(msg);
    }
}

