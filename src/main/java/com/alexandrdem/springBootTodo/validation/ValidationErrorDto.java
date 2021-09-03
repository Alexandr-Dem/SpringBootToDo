package com.alexandrdem.springBootTodo.validation;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AlexanderDementev on 03.09.2021
 */
@Getter()
public class ValidationErrorDto {

    private List<String> errors = new ArrayList<>();

    private String errorMessage;

    public ValidationErrorDto(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void addErrors(String error) {
        errors.add(error);
    }

}
