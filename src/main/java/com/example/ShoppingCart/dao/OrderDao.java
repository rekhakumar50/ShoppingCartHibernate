package com.example.ShoppingCart.dao;

import com.example.ShoppingCart.model.Order;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDao {
    List<Order> findAll();

    Optional<Order> findById(Long orderId);

    long save(Order order);

    void update(Order order);

    void delete(Order order);
}
