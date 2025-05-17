package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario
    // Por ejemplo, buscar categorías por nombre o descripción
    List<Category> findAll();
} 