package com.alexandrdem.springBootTodo.validation;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.util.stream.Collectors;
import java.util.stream.Stream;
/**
 * @author AlexanderDementev on 03.09.2021
 */
public class ToDoValidationErrorBuilder {

    public static ToDoValidationError fromBindingError(Errors errors) {
        ToDoValidationError error = new ToDoValidationError(String.format("Ошибка валидации. Количество нарушений: %s.", errors.getErrorCount()));
        errors.getAllErrors().forEach(e -> error.addErrors(generateErrorString(e)));
        return error;
    }

    private static String generateErrorString(ObjectError error) {
        String objectName = error.getObjectName();
        String argument = error.getArguments() == null ? "unknown"
                : Stream.of(error.getArguments())
                .filter(a -> a instanceof DefaultMessageSourceResolvable)
                .map(a -> ((DefaultMessageSourceResolvable) a).getDefaultMessage())
                .collect(Collectors.joining());
        String message = error.getDefaultMessage();
        return String.format("Объект %s. Поле %s. %s", objectName, argument, message);
    }
}
