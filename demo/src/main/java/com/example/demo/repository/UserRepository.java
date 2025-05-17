package com.example.demo.repository;

import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    
    List<Product> findProductByUserId(Long userId);
    List<Product> findProductByUserIdAndCategory(Long userId, Category category);
    List<Product> findProductByUserIdAndNameContaining(Long userId, String name);
    List<Product> findProductByUserIdAndCategoryAndNameContaining(Long userId, Category category, String name);
}
