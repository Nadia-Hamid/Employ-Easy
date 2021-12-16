package se.yrgo.employee.exceptions;

import java.io.Serializable;
import org.springframework.web.bind.annotation.ControllerAdvice;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
