package com.example.ShoppingCart.controller;

import com.example.ShoppingCart.dto.CartInfo;
import com.example.ShoppingCart.dto.CartLineInfo;
import com.example.ShoppingCart.dto.OrderDetailInfo;
import com.example.ShoppingCart.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/order-details")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/{orderId}")
    public List<OrderDetailInfo> getAllOrderDetailsByOrderId(@PathVariable Long orderId) {
        return orderDetailService.getAllOrderDetailsByOrderId(orderId);
    }

    @PostMapping
    public void saveOrderDetails(@Valid @RequestBody CartInfo cartInfo) {
        orderDetailService.saveOrderDetails(cartInfo);
    }

    @PutMapping("/{orderId}")
    public void updateCartItem(@PathVariable Long orderId, @RequestBody CartLineInfo cartLineInfo) {
        orderDetailService.updateCartItem(orderId, cartLineInfo);
    }

    @DeleteMapping("/{orderId}/{productCode}")
    public void deleteCartItem(@PathVariable Long orderId, @PathVariable String productCode) {
        orderDetailService.deleteCartItem(orderId, productCode);
    }

}
