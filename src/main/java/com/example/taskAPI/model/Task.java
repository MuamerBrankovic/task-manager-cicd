package com.example.taskAPI.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Task {
    private Integer id;

    @NotBlank(message = "Name have to have a value")
    @Size(min=2, message="Name must be at least 2 characters")
    private String name;

    @NotNull(message = "done need to be true or false")
    private Boolean done;

//    @Email(message = "email invalid")
//    private String email;

    public Task() {
    }

    public Task(Integer id, String name, Boolean done) {
        this.id = id;
        this.name = name;
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }
}
