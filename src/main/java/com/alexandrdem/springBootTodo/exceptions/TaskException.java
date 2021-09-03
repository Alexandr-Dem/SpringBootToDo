package com.alexandrdem.springBootTodo.exceptions;

/**
 * @author AlexanderDementev on 03.09.2021
 */
public class TaskException extends DetailedException {

    public static final String TASK_NOT_FOUND = "Задача не найдена!";

    public TaskException(String message, Throwable e) {
        super(message, e);
    }

    public TaskException(String message) {
        super(message);
    }
}
