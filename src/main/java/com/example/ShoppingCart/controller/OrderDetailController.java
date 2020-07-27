package com.example.ShoppingCart.controller;

import com.example.ShoppingCart.dto.CartDto;
import com.example.ShoppingCart.dto.CartLineDto;
import com.example.ShoppingCart.dto.OrderDetailDto;
import com.example.ShoppingCart.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/order-details")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/{orderId}")
    public List<OrderDetailDto> getAllOrderDetailsByOrderId(@PathVariable Long orderId) {
        return orderDetailService.getAllOrderDetailsByOrderId(orderId);
}

    @PostMapping
    public ResponseEntity<?> saveOrderDetails(@Valid @RequestBody CartDto cartDto) {
        long orderId = orderDetailService.saveOrderDetails(cartDto);
        return ResponseEntity.ok()
                .body("OrderId : "+orderId);
    }

    @PutMapping("/{orderId}")
    public void updateCartItem(@PathVariable Long orderId, @RequestBody CartLineDto cartLineDto) {
        orderDetailService.updateCartItem(orderId, cartLineDto);
    }

    @DeleteMapping("/{orderId}/{productCode}")
    public void deleteCartItem(@PathVariable Long orderId, @PathVariable String productCode) {
        orderDetailService.deleteCartItem(orderId, productCode);
    }

}
