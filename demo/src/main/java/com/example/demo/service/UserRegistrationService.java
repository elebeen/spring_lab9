package com.example.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.repository.UserRepository;
import com.example.demo.model.User;

public class UserRegistrationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserRegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(String username, String rawPassword, String role) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("El nombre de usuario ya existe.");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword)); // ✨ Codifica la contraseña
        user.setRole(role); // Por ejemplo, "USER" o "ADMIN"
        return userRepository.save(user);
    }
}
