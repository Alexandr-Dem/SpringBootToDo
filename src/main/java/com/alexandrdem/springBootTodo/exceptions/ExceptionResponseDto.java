package com.alexandrdem.springBootTodo.exceptions;

import lombok.Data;
/**
 * @author AlexanderDementev on 03.09.2021
 */
@Data
public class ExceptionResponseDto {
    
    private boolean success;
    
    private int httpCode;
    
    private String message;

    public ExceptionResponseDto(int httpCode, String message) {
        this.success = false;
        this.httpCode = httpCode;
        this.message = message;
    }
    
}
