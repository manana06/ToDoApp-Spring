package com.todo.todoapp.controller;

import com.todo.todoapp.entity.Task;
import com.todo.todoapp.entity.User;
import com.todo.todoapp.service.TaskService;
import com.todo.todoapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("/tasks")
    public String listTasks(Model model, Principal principal, @RequestParam(value = "keyword", required = false) String keyword) {
        String username = principal.getName();
        User currentUser = userService.findByUsername(username);

        List<Task> tasks = taskService.searchTasks(currentUser, keyword);

        model.addAttribute("tasks", tasks);
        model.addAttribute("newTask", new Task()); 
        model.addAttribute("username", username);

        return "tasks"; 
    }

    @PostMapping("/tasks")
    public String saveTask(@ModelAttribute("newTask") Task task, Principal principal) {
        String username = principal.getName();
        User currentUser = userService.findByUsername(username);
 
        task.setUser(currentUser); 
        task.setStatus("Pending"); 

        taskService.saveTask(task);
        return "redirect:/tasks"; 
    }

    @GetMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/toggle/{id}")
    public String toggleTaskStatus(@PathVariable Long id) {
        Task task = taskService.getTask(id);
        if (task != null) {
            if ("Completed".equals(task.getStatus())) {
                task.setStatus("Pending");
            } else {
                task.setStatus("Completed");
            }
            taskService.saveTask(task);
        }
        return "redirect:/tasks";
    }
}
