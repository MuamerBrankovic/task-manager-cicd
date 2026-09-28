package com.example.taskAPI.controller;

import com.example.taskAPI.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnAllTasks()throws Exception{

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldCreateTask()throws Exception{
        Task task = new Task(null, "broccoli", false);
        String json = objectMapper.writeValueAsString(task);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturn400WhenNameTooShort()throws Exception{
        Task task = new Task(null, "a", false);  // "a" är bara 1 tecken, för kort
        String json = objectMapper.writeValueAsString(task);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn404WhenPatchingNonexistentTask()throws Exception{
        Task updated = new Task(null, "Hej", true);
        String json = objectMapper.writeValueAsString(updated);

        mockMvc.perform(patch("/tasks/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn409WhenTaskAlreadyDone() throws Exception {
        Task updated = new Task(null, "mjölk", true);
        String json = objectMapper.writeValueAsString(updated);

        mockMvc.perform(patch("/tasks/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isConflict());
    }

}