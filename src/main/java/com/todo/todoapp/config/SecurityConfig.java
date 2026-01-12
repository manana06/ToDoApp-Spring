package com.todo.todoapp.config;

import com.todo.todoapp.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1. Αυτό διορθώνει το κόκκινο σφάλμα στο UserService!
    // Λέει στο σύστημα πώς να κρυπτογραφεί τους κωδικούς.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. Εδώ ορίζουμε ποιος έχει πρόσβαση πού.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/register", "/login", "/css/**", "/js/**").permitAll() // Όλοι βλέπουν αυτά
                        .anyRequest().authenticated() // Για όλα τα άλλα θέλει login
                )
                .formLogin(form -> form
                        .loginPage("/login") // Θα φτιάξουμε δική μας σελίδα login
                        .defaultSuccessUrl("/tasks", true) // Μόλις μπει, πάει στα tasks
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }

    // 3. Η "Γέφυρα": Συνδέει το Spring Security με τη Βάση Δεδομένων μας
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            // Ψάχνουμε τον χρήστη στη βάση μας
            com.todo.todoapp.entity.User appUser = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            // Τον μετατρέπουμε σε χρήστη που καταλαβαίνει το Spring Security
            return org.springframework.security.core.userdetails.User
                    .withUsername(appUser.getUsername())
                    .password(appUser.getPassword())
                    .authorities(appUser.getRole()) // Βάζουμε τον ρόλο (ADMIN/MEMBER)
                    .build();
        };
    }
}