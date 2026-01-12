package com.todo.todoapp.controller;

import com.todo.todoapp.entity.User;
import com.todo.todoapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Δείχνει τη σελίδα Login
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Δείχνει τη σελίδα Εγγραφής
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Επεξεργάζεται την Εγγραφή όταν πατάς το κουμπί
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        // Ελέγχουμε αν υπάρχει ήδη ο χρήστης
        if (userService.findByUsername(user.getUsername()) != null) {
            return "redirect:/register?error"; // Αν υπάρχει, ξαναπάμε στο register με error
        }
        userService.saveUser(user);
        return "redirect:/login?success"; // Αν όλα καλά, πάμε στο login
    }
}