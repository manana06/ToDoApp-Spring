package com.todo.todoapp.repository;

import com.todo.todoapp.entity.Task;
import com.todo.todoapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUser(User user);

    List<Task> findByUserAndTitleContaining(User user, String keyword);
}