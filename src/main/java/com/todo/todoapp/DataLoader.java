package com.todo.todoapp;

import com.todo.todoapp.entity.User;
import com.todo.todoapp.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserService userService;

    public DataLoader(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- ΕΛΕΓΧΟΣ ΒΑΣΗΣ ΔΕΔΟΜΕΝΩΝ ---");

        // Ελέγχουμε αν υπάρχει ήδη ο χρήστης για να μην βγάλει λάθος
        if (userService.findByUsername("admin") == null) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("1234"); // Ο κωδικός θα κρυπτογραφηθεί αυτόματα στο Service
            admin.setRole("ADMIN");

            userService.saveUser(admin);
            System.out.println("✅ ΕΠΙΤΥΧΙΑ: Ο χρήστης 'admin' δημιουργήθηκε!");
        } else {
            System.out.println("ℹ️ Ο χρήστης 'admin' υπάρχει ήδη.");
        }

        System.out.println("-------------------------------");
    }
}