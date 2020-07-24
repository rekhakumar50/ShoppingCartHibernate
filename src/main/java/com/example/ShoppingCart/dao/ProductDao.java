package com.example.ShoppingCart.dao;

import com.example.ShoppingCart.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao {

    List<Product> findByNameContaining(String name);

    List<Product> findAll();

    Optional<Product> findById(String code);

    void save(Product product);

    void deleteById(String code);

    void update(Product product);
}
