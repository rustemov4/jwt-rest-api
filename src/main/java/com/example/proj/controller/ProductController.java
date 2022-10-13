package com.example.proj.controller;

import com.example.proj.entity.Category;
import com.example.proj.entity.Product;
import com.example.proj.repository.CategoryRepository;
import com.example.proj.request.ProductRequest;
import com.example.proj.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;
    private final CategoryRepository categoryRepository;

    @PostMapping("/product")
    public ResponseEntity<?> saveProduct(@RequestBody ProductRequest product) {
        Product newProduct = new Product();
        newProduct.setName(product.getName());
        newProduct.setDescription(product.getDescription());
        newProduct.setImage(product.getImage());
        newProduct.setPrice(product.getPrice());
        Category category = categoryRepository.findCategoryByName(product.getCategory());
        newProduct.setCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(newProduct));
    }

    @GetMapping("/products")
    public ResponseEntity<?> getProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProducts());
    }
}
