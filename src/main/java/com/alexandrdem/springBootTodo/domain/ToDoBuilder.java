package com.alexandrdem.springBootTodo.domain;

/**
 * @author AlexanderDementev on 02.09.2021
 */
public class ToDoBuilder {

    private String id;

    private String description;

    public static ToDoBuilder create() {
        return new ToDoBuilder();
    }

    public ToDoBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public ToDoBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public ToDo build() {
        ToDo toDo = new ToDo(description);
        if (id != null) {
            toDo.setId(id);
        }
        return toDo;
    }

}
