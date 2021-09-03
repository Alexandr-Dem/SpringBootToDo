package com.alexandrdem.springBootTodo.controller;

import com.alexandrdem.springBootTodo.domain.ToDo;
import com.alexandrdem.springBootTodo.domain.ToDoBuilder;
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
        return toDoRepository.findById(id);
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
    public ResponseEntity<ToDo> completed(@PathVariable String id) {
        ToDo task = toDoRepository.findById(id);
        task.setCompleted(true);
        return ResponseEntity.ok(toDoRepository.save(task));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        toDoRepository.delete(ToDoBuilder.create().withId(id).build());
    }
}
