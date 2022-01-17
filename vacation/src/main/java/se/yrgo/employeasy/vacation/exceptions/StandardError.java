package se.yrgo.employeasy.vacation.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;

import java.io.Serializable;

@ControllerAdvice
public class StandardError implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer status;
    private String error;
    private String message;

    public StandardError() {}

    public StandardError(Integer status, String error, String message) {
        super();
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public String getError() {
        return error;
    }

}

