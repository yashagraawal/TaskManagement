package com.tms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tms.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
