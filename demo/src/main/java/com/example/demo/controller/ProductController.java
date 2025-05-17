package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/home")
public class ProductController {

    private final ProductService productService;
    private final UserService userService;
    private final CategoryRepository categoryRepository;

    // Mostrar todos los productos del usuario autenticado
    @GetMapping
    public String listUserProducts(
        Model model, 
        @AuthenticationPrincipal UserDetails userDetails, 
        HttpServletRequest request
    ) {
        Long userId = userService.findByUsername(userDetails.getUsername()).getId();
        List<Product> products = productService.getProductsByUser(userId);
        String username = userDetails.getUsername();
        List<Category> categories = categoryRepository.findAll();

        model.addAttribute("products", products);
        model.addAttribute("username", username);
        model.addAttribute("categories", categories);
        model.addAttribute("requestURI", request.getRequestURI());
        return "home";
    }

    // Mostrar formulario de creación
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryRepository.findAll());
        return "home/create";
    }

    // Guardar nuevo producto
    @PostMapping("/create")
    public String createProduct(@ModelAttribute Product product,
                                @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = userService.findByUsername(userDetails.getUsername()).getId();
        productService.saveProduct(product, userId);
        return "redirect:/home";
    }

    // Mostrar formulario de edición
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model,
                               @AuthenticationPrincipal UserDetails userDetails) {
        Optional<Product> productOpt = productService.getProductById(id);

        if (productOpt.isEmpty()) return "redirect:/products";

        Product product = productOpt.get();
        Long userId = userService.findByUsername(userDetails.getUsername()).getId();

        // Solo deja editar si es del mismo usuario
        if (!product.getUser().getId().equals(userId)) return "redirect:/products";

        model.addAttribute("product", product);
        model.addAttribute("categories", categoryRepository.findAll());
        return "home/edit";
    }

    // Actualizar producto
    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable Long id,
                                @ModelAttribute Product product,
                                @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = userService.findByUsername(userDetails.getUsername()).getId();
        product.setId(id);
        productService.saveProduct(product, userId); // `saveProduct` actúa como crear o actualizar
        return "redirect:/home";
    }

    // Eliminar producto
    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id,
                                @AuthenticationPrincipal UserDetails userDetails) {
        Optional<Product> productOpt = productService.getProductById(id);
        Long userId = userService.findByUsername(userDetails.getUsername()).getId();

        if (productOpt.isPresent() && productOpt.get().getUser().getId().equals(userId)) {
            productService.deleteProductById(id);
        }

        return "redirect:/home";
    }

    // Buscar productos (por nombre, categoría o ambos)
    @GetMapping("/search")
    public String searchProducts(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) Long categoryId,
        Model model,
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        Long userId = userService.findByUsername(userDetails.getUsername()).getId();
        Category category = categoryId != null ? categoryRepository.findById(categoryId).orElse(null) : null;
        List<Product> products;

        if (name != null && !name.isEmpty() && category != null) {
            products = productService.getProductsByUserCategoryAndName(userId, category, name);
        } else if (category != null) {
            products = productService.getProductsByUserAndCategory(userId, category);
        } else if (name != null && !name.isEmpty()) {
            products = productService.getProductsByUserAndName(userId, name);
        } else {
            products = productService.getProductsByUser(userId);
        }

        model.addAttribute("products", products);
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("searchTerm", name);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("username", userDetails.getUsername());

        return "home";
    }
}
