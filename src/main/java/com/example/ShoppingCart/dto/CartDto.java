package com.example.ShoppingCart.dto;

import java.util.List;

public class CartDto {

    private CustomerDto customerInfo;

    private List<CartLineDto> cartLines;

    public CustomerDto getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerDto customerInfo) {
        this.customerInfo = customerInfo;
    }

    public List<CartLineDto> getCartLines() {
        return cartLines;
    }

    public void setCartLines(List<CartLineDto> cartLines) {
        this.cartLines = cartLines;
    }

    public double getAmountTotal() {
        double total = 0;
        for (CartLineDto line : this.getCartLines()) {
            total += line.getAmount();
        }
        return total;
    }
}
