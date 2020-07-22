package com.example.ShoppingCart.controller;

import com.example.ShoppingCart.dto.CustomerInfo;
import com.example.ShoppingCart.model.Order;
import com.example.ShoppingCart.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/{orderId}")
    public Order getOrder(@PathVariable Long orderId) {
        return orderService.getOrder(orderId);
    }

    @PutMapping("/{orderId}")
    public void updateOrder(@PathVariable Long orderId, @Valid @RequestBody CustomerInfo customerInfo) {
        orderService.updateOrder(orderId, customerInfo);
    }

    @DeleteMapping("/{orderId}")
    public void removeOrder(@PathVariable Long orderId) {
        orderService.removeOrder(orderId);
    }

}
