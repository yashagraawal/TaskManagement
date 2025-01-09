package com.tms.controller;

import java.security.Principal;
import java.util.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tms.model.Task;
import com.tms.repository.TaskRepository;
import com.tms.service.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    
	@Autowired
    private TaskService service;
    
    @Autowired
    private TaskRepository taskRepository;
    
    

    @GetMapping
    public List<Task> getAllTasks(Principal principal) {
        return service.getAllTasks(principal.getName());
    }

    @PostMapping
    public Task createTask(@RequestBody Task task, Principal principal) {
        return service.createTask(principal.getName(), task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTask(@PathVariable Long id, @RequestBody Task task) {
        
    	if (taskRepository.existsById(id)) {
            service.updateTask(id, task);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

 // Delete a task by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/api/current-user")
    public Map<String, String> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return Map.of("username", username);
    }
}
