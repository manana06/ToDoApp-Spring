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

    // 1. Εμφάνιση της λίστας εργασιών (Η κεντρική σελίδα μετά το login)
    @GetMapping("/tasks")
    public String listTasks(Model model, Principal principal, @RequestParam(value = "keyword", required = false) String keyword) {
        // Βρίσκουμε ποιος χρήστης είναι συνδεδεμένος
        String username = principal.getName();
        User currentUser = userService.findByUsername(username);

        // Βρίσκουμε τα tasks του (με ή χωρίς αναζήτηση)
        List<Task> tasks = taskService.searchTasks(currentUser, keyword);

        model.addAttribute("tasks", tasks);
        model.addAttribute("newTask", new Task()); // Για τη φόρμα νέας εργασίας
        model.addAttribute("username", username);

        return "tasks"; // Θα ανοίξει το tasks.html
    }

    // 2. Αποθήκευση νέας εργασίας
    @PostMapping("/tasks")
    public String saveTask(@ModelAttribute("newTask") Task task, Principal principal) {
        String username = principal.getName();
        User currentUser = userService.findByUsername(username);

        task.setUser(currentUser); // Συνδέουμε την εργασία με τον χρήστη
        task.setStatus("Pending"); // Αρχικά είναι "Σε εκκρεμότητα"

        taskService.saveTask(task);
        return "redirect:/tasks"; // Ξαναφορτώνουμε τη σελίδα
    }

    // 3. Διαγραφή εργασίας
    @GetMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }

    // 4. Αλλαγή κατάστασης (Ολοκληρώθηκε / Εκκρεμεί)
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