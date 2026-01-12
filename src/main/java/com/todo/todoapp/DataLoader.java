package com.todo.todoapp;

import com.todo.todoapp.entity.User;
import com.todo.todoapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("maria").isEmpty()) {

            User user = new User();
            user.setUsername("maria");
            user.setPassword(passwordEncoder.encode("password"));
            user.setRole("USER");

            userRepository.save(user);
            System.out.println("--- Ο χρήστης 'maria' δημιουργήθηκε επιτυχώς! ---");
        } else {
            System.out.println("--- Ο χρήστης 'maria' υπάρχει ήδη. ---");
        }
    }
}