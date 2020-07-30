package com.example.ShoppingCart.dto;

public class OrderDetailDto {

    private int productNo;
    private String code;
    private String name;
    private double price;
    private int quantity;
    private double amount;

    public OrderDetailDto() {
    }

    public OrderDetailDto(int productNo, String code, String name, double price, int quantity, double amount) {
        this.productNo = productNo;
        this.code = code;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.amount = amount;
    }

    public int getProductNo() {
        return productNo;
    }

    public void setProductNo(int productNo) {
        this.productNo = productNo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
