package com.alexandrdem.springBootTodo.controller;

import com.alexandrdem.springBootTodo.domain.Task;
import com.alexandrdem.springBootTodo.domain.TaskBuilder;
import com.alexandrdem.springBootTodo.exceptions.TaskException;
import com.alexandrdem.springBootTodo.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author AlexanderDementev on 04.09.2021
 */
public class TaskControllerTest {

    private TaskController taskController;

    private Task notCompletedTask;

    private TaskRepository repositoryMock;

    @BeforeEach
    public void before() {
        notCompletedTask = TaskBuilder.create().withDescription("NotCompletedTask").build();

        repositoryMock = Mockito.mock(TaskRepository.class);
        when(repositoryMock.findById(notCompletedTask.getId())).thenReturn(Optional.of(notCompletedTask));
        when(repositoryMock.save(notCompletedTask)).thenReturn(notCompletedTask);

        taskController = new TaskController(repositoryMock, Mockito.mock(TaskSynchronizer.class));
    }

    @Test
    public void taskCompletedTest() throws TaskException {
        ResponseEntity<Task> result = taskController.completed(notCompletedTask.getId());

        assertThat(result).isNotNull();
        assertThat(result.getBody())
                .isNotNull()
                .matches(Task::isCompleted);

        verify(repositoryMock).findById(notCompletedTask.getId());
        verify(repositoryMock).save(notCompletedTask);
        verifyNoMoreInteractions(repositoryMock);
    }
}
