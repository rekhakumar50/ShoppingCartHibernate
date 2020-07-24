package com.example.ShoppingCart.dto;

public class CartLineDto {

    private ProductDto productInfo;
    private int quantity;

    public ProductDto getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductDto productInfo) {
        this.productInfo = productInfo;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getAmount() {
        return this.productInfo.getPrice() * this.quantity;
    }
}
