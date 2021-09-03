package com.alexandrdem.springBootTodo.exceptions;

/**
 * @author AlexanderDementev on 03.09.2021
 */
public class ExceptionResponseDto {
    
    private boolean success;
    
    private int httpCode;
    
    private String message;

    public ExceptionResponseDto(int httpCode, String message) {
        this.success = false;
        this.httpCode = httpCode;
        this.message = message;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
    
}
