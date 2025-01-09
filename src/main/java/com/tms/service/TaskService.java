package com.tms.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.tms.model.Task;
import com.tms.model.User;
import com.tms.repository.TaskRepository;
import com.tms.repository.UserRepository;

@Service
public class TaskService {
    
	@Autowired
    private TaskRepository taskRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public List<Task> getAllTasks(String Username) {
    	User user = userRepository.findByUserName(Username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return taskRepository.findByUser(user);
    }

    public Task createTask(String Username, Task task) {
    	User user = userRepository.findByUserName(Username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    	task.setUser(user);
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task task) {
    	task.setId(id);
    	
    	Task updatedTask = taskRepository.findById(id).get();
    	updatedTask.setCompleted(task.getCompleted());
    	
        return taskRepository.save(updatedTask);
    }

    public void deleteTask(Long id) {
    	taskRepository.deleteById(id);
    }
}
