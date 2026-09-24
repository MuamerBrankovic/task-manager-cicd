package com.example.taskAPI.controller;

import com.example.taskAPI.model.Task;

import com.example.taskAPI.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class TaskController {

    private TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(service.getAllTasks());
    }

    @PostMapping("/tasks")
    public ResponseEntity<Task> addTask(@Valid @RequestBody Task task) {
        service.addTask(task);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable int id) {
        service.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/tasks/{id}")
    public ResponseEntity<Task> patchTask( @PathVariable int id , @RequestBody Task updates) {
        Task updated = service.patchTask(id, updates);
        return ResponseEntity.ok(updated);
    }

}
