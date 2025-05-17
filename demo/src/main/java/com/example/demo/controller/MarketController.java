package com.example.demo.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/market")
public class MarketController {

    private final ProductService productService;
    private final UserService userService;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public String listMarketProducts(
        Model model,
        @AuthenticationPrincipal UserDetails userDetails,
        HttpServletRequest request
    ) {
        Long userId = userService.findByUsername(userDetails.getUsername()).getId();
        List<Product> products = productService.getProductsExcludingUser(userId); // Aquí excluye los propios
        String username = userDetails.getUsername();
        List<Category> categories = categoryRepository.findAll();

        model.addAttribute("products", products);
        model.addAttribute("username", username);
        model.addAttribute("categories", categories);
        model.addAttribute("requestURI", request.getRequestURI());
        return "marketplace";
    }

    @GetMapping("/search")
    public String searchMarketProducts(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) Long categoryId,
        Model model,
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        Long userId = userService.findByUsername(userDetails.getUsername()).getId();
        Category category = categoryId != null ? categoryRepository.findById(categoryId).orElse(null) : null;
        List<Product> products;

        if (name != null && !name.isEmpty() && category != null) {
            products = productService.getProductsByCategoryAndNameExcludingUser(category, name, userId);
        } else if (category != null) {
            products = productService.getProductsByCategoryExcludingUser(category, userId);
        } else if (name != null && !name.isEmpty()) {
            products = productService.getProductsByNameExcludingUser(name, userId);
        } else {
            products = productService.getProductsExcludingUser(userId);
        }

        List<Category> categories = categoryRepository.findAll();
        String username = userDetails.getUsername();

        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("username", username);
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("searchTerm", name);

        return "marketplace";
    }
}
