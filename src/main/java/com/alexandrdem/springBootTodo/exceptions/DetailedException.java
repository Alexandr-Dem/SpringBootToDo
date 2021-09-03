package com.alexandrdem.springBootTodo.exceptions;

/**
 * @author AlexanderDementev on 03.09.2021
 */
public abstract class DetailedException extends Exception {

    public DetailedException(String message) {
        super(message);
    }

    public DetailedException(String message, Throwable e) {
        super(String.format("%s. Cause - '%s'", message, getThoroughCause(e)));
    }
    
    private static String getThoroughCause(Throwable e) {
        StringBuilder message = new StringBuilder(e.getMessage());
        while (e.getCause() != null) {
            e = e.getCause();
            message.append(". ").append(e.getMessage());
        }
        return message.toString();
    }
}
