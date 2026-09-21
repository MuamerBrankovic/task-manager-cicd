package com.example.taskAPI.exception;

public class TaskAlreadyDoneException extends RuntimeException{
    public TaskAlreadyDoneException(String message) {
        super(message);
    }
}
