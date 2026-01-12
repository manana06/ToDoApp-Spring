package com.todo.todoapp.repository;

import com.todo.todoapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

    public interface UserRepository extends JpaRepository<User, Long> {
        // Βρίσκει τον χρήστη με βάση το username (για το Login)
        Optional<User> findByUsername(String username);
    }

