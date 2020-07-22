package com.example.ShoppingCart.dao;

import com.example.ShoppingCart.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ProductDao extends JpaRepository<Product, String> {

    List<Product> findByNameContaining(String name);

}
