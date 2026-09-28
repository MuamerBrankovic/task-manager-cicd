package com.example.taskAPI.service;

import com.example.taskAPI.exception.TaskAlreadyDoneException;
import com.example.taskAPI.exception.TaskNotFoundException;
import com.example.taskAPI.model.Task;
import com.example.taskAPI.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository repository;

    @InjectMocks
    private TaskService service;

    @Test
    void shouldReturnAllTasks() {
        //Arange
        Task tasks = new Task(1, "gurka", false);
        when(repository.findAll()).thenReturn(List.of(tasks));

        //Act
        List<Task> result = service.getAllTasks();

        //Assert
        assertEquals(List.of(tasks), result);

        verify(repository).findAll();
    }

    @Test
    void shouldAddTask(){
        //Arrange
        Task task = new Task(1, "gurka", false);

        //Act
        Task result = service.addTask(task);

        //Assert
        assertEquals(task, result);
        verify(repository).save(task);
    }

    @Test
    void shouldDeleteTask(){
        //Arrange
        Task task = new Task(1, "gurka", false);
        when(repository.findById(1)).thenReturn(task);

        //Act
        Task result = service.deleteTask(1);

        //Assert
        assertEquals(task, result);
        verify(repository).delete(1);
        verify(repository).findById(1);
    }

    @Test
    void shouldThrowWhenDeletingNonexistentTask() {
        //Arrange

        // Act & Assert
        assertThrows(TaskNotFoundException.class, () -> service.deleteTask(1));
    }

    @Test
    void shouldPatchTask(){
        //Arrange
        Task task = new Task(1, "gurka", false);
        when(repository.findById(1)).thenReturn(task);

        //Act
        Task updated = new Task();
        updated.setName("mjölk");
        Task result = service.patchTask(1, updated);

        //Assert
        assertEquals("mjölk", result.getName());
        verify(repository).findById(1);
    }

    @Test
    void shouldMarkTaskAsDone(){
        //Arrange
        Task task = new Task(1, "tomat", false);
        when(repository.findById(1)).thenReturn(task);

        //Act
        Task result = service.patchTask(1, new Task(null, null, true));

        //Assert
        assertTrue(result.isDone());
        verify(repository).findById(1);
    }

    @Test
    void shouldThrowWhenPatchingNonexistentTask() {
        //Arrange ingen task riggad.

        // Act & Assert
        assertThrows(TaskNotFoundException.class, ()-> service.patchTask(1, new Task(null, null, true)));
    }

    @Test
    void shouldThrowWhenMarkingAlreadyDoneTask(){
        //Arrange
        Task task = new Task(1,"morot", true);
        when(repository.findById(1)).thenReturn(task);

        //Act & Assert
        assertThrows(TaskAlreadyDoneException.class, () -> service.patchTask(1, new Task(null, null, true)));
    }

}








