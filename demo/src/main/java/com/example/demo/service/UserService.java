package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.repository.UserRepository;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Product> findProductByUserId(Long userId) {
        return userRepository.findProductByUserId(userId);
    }

    public List<Product> findProductByUserIdAndCategory(Long userId, Category category) {
        return userRepository.findProductByUserIdAndCategory(userId, category);
    }
}
