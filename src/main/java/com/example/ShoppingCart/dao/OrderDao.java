package com.example.ShoppingCart.dao;

import com.example.ShoppingCart.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    List<Order> findAll();

    Optional<Order> findById(Long orderId);

    void save(Order order);

    void update(Order order);

    void delete(Order order);
}
