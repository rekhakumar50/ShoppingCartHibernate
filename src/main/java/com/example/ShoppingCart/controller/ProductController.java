package com.example.ShoppingCart.controller;

import com.example.ShoppingCart.dto.ProductDto;
import com.example.ShoppingCart.model.Product;
import com.example.ShoppingCart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/name")
    public List<ProductDto> getProductsByName(@RequestParam(value = "name") String name) {
        return productService.getProductByName(name);
    }

    @GetMapping({"/code"})
    public ProductDto getProductByCode(@RequestParam(value = "code") String code) {
        return productService.getProduct(code);
    }

    @GetMapping
    public List<ProductDto> getProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    public void addProducts(@RequestBody Product product) {
        productService.save(product);
    }

    @PutMapping
    public void updateProduct(@RequestBody Product product) {
        productService.updateProduct(product);
    }

    @DeleteMapping
    public void deleteProducts(@RequestParam String code) {
        productService.deleteProducts(code);
    }

}
