package com.todo.todoapp.service;

import com.todo.todoapp.entity.Task;
import com.todo.todoapp.entity.User;
import com.todo.todoapp.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getTasksByUser(User user) {
        return taskRepository.findByUser(user);
    }

    public void saveTask(Task task) {
        taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public Task getTask(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public List<Task> searchTasks(User user, String keyword) {
        if (keyword != null) {
            return taskRepository.findByUserAndTitleContaining(user, keyword);
        }
        return taskRepository.findByUser(user);
    }
}