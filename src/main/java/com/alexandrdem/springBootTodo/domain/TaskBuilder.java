package com.alexandrdem.springBootTodo.domain;

/**
 * @author AlexanderDementev on 02.09.2021
 */
public class TaskBuilder {

    private String id;

    private String description;

    public static TaskBuilder create() {
        return new TaskBuilder();
    }

    public TaskBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public TaskBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public Task build() {
        Task task = new Task(description);
        if (id != null) {
            task.setId(id);
        }
        return task;
    }

}
