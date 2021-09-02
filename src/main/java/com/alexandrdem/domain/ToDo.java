package com.alexandrdem.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;
/**
 * @author AlexanderDementev on 02.09.2021
 */
@Data
public class ToDo {

    @NotNull
    private String id;
    @NotNull
    @NotBlank
    private String descriptions;
    private boolean completed;
    private LocalDateTime created;
    private LocalDateTime modified;

    public ToDo() {
        id = UUID.randomUUID().toString();
        completed = false;
        created = LocalDateTime.now();
    }

    public ToDo(String descriptions) {
        this();
        this.descriptions = descriptions;
    }
}
