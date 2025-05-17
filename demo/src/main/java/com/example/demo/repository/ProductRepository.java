package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Category;
import com.example.demo.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario
    // Por ejemplo, buscar productos por nombre o categoría
    List<Product> findProductByNameContaining(String name);
    List<Product> findProductByCategory(Category category);
    Optional<Product> findProductById(Long id);

    List<Product> findProductByUserId(Long userId);
    List<Product> findProductByUserIdAndCategory(Long userId, Category category);
    List<Product> findProductByUserIdAndNameContaining(Long userId, String name);
    List<Product> findProductByUserIdAndCategoryAndNameContaining(Long userId, Category category, String name);
}
