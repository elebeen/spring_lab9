package com.example.demo.model;

import lombok.Data;

import java.util.List;

import org.springframework.security.access.method.P;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role; // Ejemplo: "USER" o "ADMIN"

    @OneToMany(mappedBy = "user")
    private List<Product> products;
}
