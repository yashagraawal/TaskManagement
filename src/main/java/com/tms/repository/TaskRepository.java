package com.tms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tms.model.Task;
import com.tms.model.User;

public interface TaskRepository extends JpaRepository<Task, Long> {
	List<Task> findByUser(User user);
}
