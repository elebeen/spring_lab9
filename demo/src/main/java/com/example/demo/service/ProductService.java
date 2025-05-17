package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Product;
import com.example.demo.model.Category;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;

public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public Product addProduct(Long userId, Product product) {
        // Verifica si el usuario existe
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("El usuario no existe.");
        }

        // Asigna el usuario al producto
        product.setUser(userRepository.findById(userId).orElse(null));
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        // Verifica si el producto existe
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("El producto no existe.");
        }

        productRepository.deleteById(id);
    }

    public Product updateProduct(Long id, Product product) {
        // Verifica si el producto existe
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("El producto no existe.");
        }

        // Asigna el ID del producto al objeto que se va a actualizar
        product.setId(id);
        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        // Verifica si el producto existe
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("El producto no existe."));
    }
    
    public List<Product> findByNameContaining(String name) {
        return productRepository.findByNameContaining(name);
    }

    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }
}
