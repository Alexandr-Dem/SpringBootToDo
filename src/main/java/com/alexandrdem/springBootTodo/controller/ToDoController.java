package com.alexandrdem.springBootTodo.controller;

import com.alexandrdem.springBootTodo.domain.ToDo;
import com.alexandrdem.springBootTodo.domain.ToDoBuilder;
import com.alexandrdem.springBootTodo.exceptions.ExceptionResponseDto;
import com.alexandrdem.springBootTodo.exceptions.TaskException;
import com.alexandrdem.springBootTodo.repository.CommonRepository;
import com.alexandrdem.springBootTodo.validation.ToDoValidationErrorBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
/**
 * @author AlexanderDementev on 02.09.2021
 */

@RestController
@RequestMapping("/api/todo")
public class ToDoController {

    private CommonRepository<ToDo> toDoRepository;

    public ToDoController(CommonRepository<ToDo> toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    @GetMapping()
    public Iterable<ToDo> getAll() {
        return toDoRepository.findAll();
    }

    @GetMapping("{id}")
    public ToDo getById(@PathVariable String id) {
        return toDoRepository.findById(id).orElse(null);
    }

    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity<?> saveToDo(@Valid @RequestBody ToDo toDo, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ToDoValidationErrorBuilder.fromBindingError(errors));
        }
        ToDo savedToDo = toDoRepository.save(toDo);
        return ResponseEntity.ok(savedToDo);
    }

    @PatchMapping("{id}")
    public ResponseEntity<ToDo> completed(@PathVariable String id) throws TaskException {
        ToDo task = toDoRepository.findById(id)
                .orElseThrow(() -> new TaskException(TaskException.TASK_NOT_FOUND));
        task.setCompleted(true);
        return ResponseEntity.ok(toDoRepository.save(task));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        toDoRepository.delete(ToDoBuilder.create().withId(id).build());
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDto> handleException(Exception exception) {
        int status;
        if (exception instanceof TaskException) {
            status = 404;
        } else {
            exception = new TaskException("При обработки задачи было зафиксировано неожиданное исключение!", exception);
            status = 500;
        }
        return ResponseEntity.status(status).body(new ExceptionResponseDto(status, exception.getMessage()));
    }

}
