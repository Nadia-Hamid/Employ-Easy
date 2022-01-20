package se.yrgo.employeasy.exceptions;

import java.io.Serializable;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * class StandardError
 * abstract DTO transfer object for exceptions
 * updated 2022-01-20
 */
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

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
