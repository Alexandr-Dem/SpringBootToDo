package com.alexandrdem.springBootTodo.repository;

import com.alexandrdem.springBootTodo.domain.ToDo;
import com.alexandrdem.springBootTodo.domain.ToDoBuilder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
/**
 * @author AlexanderDementev on 02.09.2021
 */
@Repository
public class ToDoRepository implements CommonRepository<ToDo> {

    private Map<String, ToDo> toDos = new HashMap<>();

    @Override
    public ToDo save(ToDo domain) {
        ToDo toDo;
        if (toDos.containsKey(domain.getId())) {
            toDo = findById(domain.getId());
            toDo.setCompleted(domain.isCompleted());
            toDo.setDescriptions(domain.getDescriptions());
            toDo.setModified(LocalDateTime.now());
        } else {
            toDo = new ToDoBuilder()
                    .withDescription(domain.getDescriptions())
                    .withId(domain.getId())
                    .build();
            toDos.put(toDo.getId(), toDo);
        }
        return toDo;
    }

    @Override
    public Iterable<ToDo> save(Collection<ToDo> domains) {
        return domains.stream().map(this::save).collect(Collectors.toSet());
    }

    @Override
    public void delete(ToDo domain) {
        toDos.remove(domain.getId());
    }

    @Override
    public ToDo findById(String id) {
        return toDos.get(id);
    }

    @Override
    public Iterable<ToDo> findAll() {
        return toDos.values().stream()
                .sorted(Comparator.comparing(ToDo::getCreated))
                .collect(Collectors.toList());
    }
}
