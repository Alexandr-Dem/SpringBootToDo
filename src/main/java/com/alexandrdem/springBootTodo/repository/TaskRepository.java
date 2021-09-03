package com.alexandrdem.springBootTodo.repository;

import com.alexandrdem.springBootTodo.domain.Task;
import com.alexandrdem.springBootTodo.domain.TaskBuilder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
/**
 * @author AlexanderDementev on 02.09.2021
 */
@Repository
public class TaskRepository implements CommonRepository<Task> {

    private Map<String, Task> tasks = new HashMap<>();

    @Override
    public Task save(Task domain) {
        Task task;
        Optional<Task> taskFromCache = findById(domain.getId());
        if (taskFromCache.isPresent()) {
            task = taskFromCache.get();
            task.setCompleted(domain.isCompleted());
            task.setDescriptions(domain.getDescriptions());
            task.setModified(LocalDateTime.now());
        } else {
            task = new TaskBuilder()
                    .withDescription(domain.getDescriptions())
                    .withId(domain.getId())
                    .build();
            tasks.put(task.getId(), task);
        }
        return task;
    }

    @Override
    public Iterable<Task> save(Collection<Task> domains) {
        return domains.stream().map(this::save).collect(Collectors.toSet());
    }

    @Override
    public void delete(Task domain) {
        tasks.remove(domain.getId());
    }

    @Override
    public Optional<Task> findById(String id) {
        return Optional.ofNullable(tasks.get(id));
    }

    @Override
    public Iterable<Task> findAll() {
        return tasks.values().stream()
                .sorted(Comparator.comparing(Task::getCreated))
                .collect(Collectors.toList());
    }
}
