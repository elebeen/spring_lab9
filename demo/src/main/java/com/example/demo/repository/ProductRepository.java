package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Category;
import com.example.demo.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario
    // Por ejemplo, buscar productos por nombre o categoría
    List<Product> findByNameContaining(String name);
    List<Product> findByCategory(Category category);
    Optional<Product> findProductById(Long id);

    void addProduct(Product product, Long userId);
    void deleteProductById(Long id);
    void updateProduct(Product product, Long id);
}
