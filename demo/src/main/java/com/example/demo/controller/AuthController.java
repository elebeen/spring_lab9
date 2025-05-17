package com.example.demo.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.User;
import com.example.demo.service.UserRegistrationService;

@Controller
public class AuthController {
    private final UserRegistrationService registrationService;
    private final AuthenticationManager authenticationManager;
    
    public AuthController(UserRegistrationService registrationService, AuthenticationManager authenticationManager) {
        this.registrationService = registrationService;
        this.authenticationManager = authenticationManager;
    }
    
    @GetMapping("/login")
    public String login() {
        return "login"; // Nombre de la plantilla Thymeleaf o JSP
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User()); // Usamos un DTO para seguridad
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User userDto, Model model) {
        try {
            registrationService.registerUser(userDto.getUsername(), userDto.getPassword(), "USER");

            Authentication auth = new UsernamePasswordAuthenticationToken(
                userDto.getUsername(),
                userDto.getPassword()
            );

            Authentication authenticated = authenticationManager.authenticate(auth);
            SecurityContextHolder.getContext().setAuthentication(authenticated);

            return "redirect:/home";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}
