package com.alexandrdem.springBootTodo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
/**
 * @author AlexanderDementev on 02.09.2021
 */
@Data
public class Task {

    @Size(min = 36, max = 36)
    private String id;
    @NotNull
    @NotBlank
    private String descriptions;
    private boolean completed;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modified;

    public Task() {
        id = UUID.randomUUID().toString();
        completed = false;
        created = LocalDateTime.now();
    }

    public Task(String descriptions) {
        this();
        this.descriptions = descriptions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return completed == task.completed && Objects.equals(id, task.id) && Objects.equals(descriptions, task.descriptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descriptions, completed);
    }
}
