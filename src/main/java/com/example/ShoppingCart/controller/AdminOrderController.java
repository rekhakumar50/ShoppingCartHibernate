package com.example.ShoppingCart.controller;

import com.example.ShoppingCart.model.Order;
import com.example.ShoppingCart.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/order")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<Order> getOrders() {
        return orderService.getOrders();
    }

}
