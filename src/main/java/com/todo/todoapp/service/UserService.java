package com.todo.todoapp.service;

import com.todo.todoapp.entity.User;
import com.todo.todoapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Θα μας βοηθήσει να κρυπτογραφήσουμε τον κωδικό

    public void saveUser(User user) {
        // Πριν αποθηκεύσουμε, κρυπτογραφούμε τον κωδικό για ασφάλεια
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Αν δεν έχει ρόλο, του βάζουμε "ROLE_MEMBER"
        if (user.getRole() == null) {
            user.setRole("ROLE_MEMBER");
        }
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}
