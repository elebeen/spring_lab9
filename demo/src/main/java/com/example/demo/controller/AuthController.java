package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    @GetMapping("/login")
    public String login() {
        return "login"; // Nombre de la plantilla Thymeleaf o JSP
    }

    @GetMapping("/home")
    public String home() {
        return "home"; // Página principal después del login
    }

}
