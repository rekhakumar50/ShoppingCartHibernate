package com.example.ShoppingCart.dto;

import java.util.List;

public class CartInfo {

    private CustomerInfo customerInfo;

    private List<CartLineInfo> cartLines;

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }

    public List<CartLineInfo> getCartLines() {
        return cartLines;
    }

    public void setCartLines(List<CartLineInfo> cartLines) {
        this.cartLines = cartLines;
    }

    public double getAmountTotal() {
        double total = 0;
        for (CartLineInfo line : this.getCartLines()) {
            total += line.getAmount();
        }
        return total;
    }
}
