package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.model.Category;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    // Crear o actualizar un producto
    public Product saveProduct(Product product, Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        product.setUser(user);
        return productRepository.save(product);
    }

    // Buscar producto por ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Eliminar un producto por ID
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    // Buscar todos los productos de un usuario
    public List<Product> getProductsByUser(Long userId) {
        return productRepository.findProductByUserId(userId);
    }

    // Buscar por usuario y categoría
    public List<Product> getProductsByUserAndCategory(Long userId, Category category) {
        return productRepository.findProductByUserIdAndCategory(userId, category);
    }

    // Buscar por usuario y nombre parcial
    public List<Product> getProductsByUserAndName(Long userId, String name) {
        return productRepository.findProductByUserIdAndNameContaining(userId, name);
    }

    // Buscar por usuario, categoría y nombre parcial
    public List<Product> getProductsByUserCategoryAndName(Long userId, Category category, String name) {
        return productRepository.findProductByUserIdAndCategoryAndNameContaining(userId, category, name);
    }
}
