package com.example.ShoppingCart.controller;

import com.example.ShoppingCart.dto.CustomerDto;
import com.example.ShoppingCart.dto.OrderDto;
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
    public OrderDto getOrder(@PathVariable Long orderId) {
        return orderService.getOrder(orderId);
    }

    @PutMapping("/{orderId}")
    public void updateOrder(@PathVariable Long orderId, @Valid @RequestBody CustomerDto customerInfo) {
        orderService.updateOrder(orderId, customerInfo);
    }

    @DeleteMapping("/{orderId}")
    public void removeOrder(@PathVariable Long orderId) {
        orderService.removeOrder(orderId);
    }

}
