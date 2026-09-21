package com.example.taskAPI.service;

import com.example.taskAPI.exception.TaskAlreadyDoneException;
import com.example.taskAPI.exception.TaskNotFoundException;
import com.example.taskAPI.model.Task;
import com.example.taskAPI.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TaskService {
    private TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Task addTask(Task task) {
        repository.save(task);
        return task;
    }

    public Task deleteTask(int id) {
        Task task = repository.findById(id);
        if (task == null) {
            throw new TaskNotFoundException("Task not found");
        }
        repository.delete(id);
        return task;
    }

    public Task patchTask(int id, Task updates) {
        Task task = repository.findById(id);
        if (task == null) {
            throw new TaskNotFoundException("Task not found");
        }

        if (updates.getName() != null) {
            task.setName(updates.getName());
        }

        if (task.isDone() && updates.isDone() != null && updates.isDone()) {
            throw new TaskAlreadyDoneException("Task is already done");
        }

        if (updates.isDone() != null) {
            task.setDone(updates.isDone());
        }

        return task;
    }



}
