package com.alexandrdem.springBootTodo.controller;

import com.alexandrdem.springBootTodo.domain.Task;
import com.alexandrdem.springBootTodo.exceptions.ExceptionResponseDto;
import com.alexandrdem.springBootTodo.exceptions.TaskException;
import com.alexandrdem.springBootTodo.repository.CommonRepository;
import com.alexandrdem.springBootTodo.validation.ValidationErrorBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
/**
 * @author AlexanderDementev on 02.09.2021
 */

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private CommonRepository<Task> taskRepository;

    public TaskController(CommonRepository<Task> taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public Iterable<Task> getAll() {
        return taskRepository.findAll();
    }

    @GetMapping("{id}")
    public Task getById(@PathVariable String id) {
        return taskRepository.findById(id).orElse(null);
    }

    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity<?> saveTask(@Valid @RequestBody Task task, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingError(errors));
        }
        Task savedTask = taskRepository.save(task);
        return ResponseEntity.ok(savedTask);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Task> completed(@PathVariable String id) throws TaskException {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskException(TaskException.TASK_NOT_FOUND));
        task.setCompleted(true);
        return ResponseEntity.ok(taskRepository.save(task));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) throws TaskException {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskException(TaskException.TASK_NOT_FOUND));
        taskRepository.delete(task);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDto> handleException(Exception exception) {
        int status;
        if (exception instanceof TaskException) {
            status = 404;
        } else {
            exception = new TaskException("При обработке задачи было зафиксировано неожиданное исключение!", exception);
            status = 500;
        }
        return ResponseEntity.status(status).body(new ExceptionResponseDto(status, exception.getMessage()));
    }

}
