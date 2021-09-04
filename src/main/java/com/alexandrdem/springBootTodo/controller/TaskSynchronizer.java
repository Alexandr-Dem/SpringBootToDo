package com.alexandrdem.springBootTodo.controller;

import com.alexandrdem.springBootTodo.configs.ConfigProperties;
import com.alexandrdem.springBootTodo.domain.Task;
import com.alexandrdem.springBootTodo.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
/**
 * @author AlexanderDementev on 02.09.2021
 */
@Service
public class TaskSynchronizer {

    private ConfigProperties configProperties;

    private TaskRepository taskRepository;

    private RestTemplate restTemplate;

    public TaskSynchronizer(ConfigProperties configProperties, TaskRepository taskRepository) {
        this.configProperties = configProperties;
        this.taskRepository = taskRepository;
        restTemplate = new RestTemplate();
    }

    public void sync() {
        String url = configProperties.getSyncUrl() + configProperties.getSyncBasePath();
        List<Task> actualTask = performGetRequest(url);
        Map<String, Task> taskById = (taskRepository.findAll()).stream().collect(Collectors.toMap(Task::getId, t -> t));

        findNewTask(actualTask, taskById)
                .forEach(taskRepository::save);
        findChangedTask(actualTask, taskById)
                .forEach(taskRepository::save);
        findDeletedTask(actualTask, taskById)
                .forEach(taskRepository::delete);
    }

    private List<Task> findNewTask(List<Task> actualTask, Map<String, Task> tasks) {
        return actualTask.stream()
                .filter(t -> !tasks.containsKey(t.getId()))
                .collect(Collectors.toList());
    }

    private List<Task> findChangedTask(List<Task> actualTask, Map<String, Task> tasks) {
        Predicate<Task> isChangedTask = t -> !tasks.get(t.getId()).equals(t);
        return actualTask.stream()
                .filter(t -> tasks.containsKey(t.getId()))
                .filter(isChangedTask)
                .collect(Collectors.toList());
    }

    private List<Task> findDeletedTask(List<Task> actualTask, Map<String, Task> tasks) {
        Set<String> actualTaskId = actualTask.stream()
                .map(Task::getId)
                .collect(Collectors.toSet());
        return tasks.values().stream()
                .filter(t -> !actualTaskId.contains(t.getId()))
                .collect(Collectors.toList());
    }

    private List<Task> performGetRequest(String url) {
        ResponseEntity<Task[]> responseEntity;
        try {
            responseEntity = restTemplate.getForEntity(url, Task[].class);
        } catch (Exception e) {
            throw new RuntimeException("Не удалось получить задачи с сервера. Причина: " + e.getMessage());
        }
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Не удалось получить задачи с сервера. Статус овтета: " + responseEntity.getStatusCode());
        }
        return responseEntity.getBody() == null ? new ArrayList<>() : Arrays.asList(responseEntity.getBody());
    }

}
