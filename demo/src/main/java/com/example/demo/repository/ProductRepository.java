package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Category;
import com.example.demo.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario
    // Por ejemplo, buscar productos por nombre o categoría
    List<Product> findByCategoryAndUserIdNot(Category category, Long userId);
    List<Product> findByNameContainingAndUserIdNot(String name, Long userId);
    List<Product> findByCategoryAndNameContainingAndUserIdNot(Category category, String name, Long userId);

    List<Product> findAll();
    List<Product> findByUserIdNot(Long userId);
    Optional<Product> findById(Long id);

    // Métodos personalizados para buscar productos por usuario
    List<Product> findByUserId(Long userId);
    List<Product> findByUserIdAndCategory(Long userId, Category category);
    List<Product> findByUserIdAndNameContaining(Long userId, String name);
    List<Product> findByUserIdAndCategoryAndNameContaining(Long userId, Category category, String name);
}
