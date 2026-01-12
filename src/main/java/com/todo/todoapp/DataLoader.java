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

        if (userService.findByUsername("maria") == null) {
            User maria = new User();
            maria.setUsername("maria");
            maria.setPassword("1234");
            maria.setRole("MARIA");

            userService.saveUser(admin);
            System.out.println("ΕΠΙΤΥΧΙΑ: Ο χρήστης 'maria' δημιουργήθηκε!");
        } else {
            System.out.println("Ο χρήστης 'maria' υπάρχει ήδη.");
        }

        System.out.println("-------------------------------");
    }
}
