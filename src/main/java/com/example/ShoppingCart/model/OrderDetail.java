package com.example.ShoppingCart.model;

import javax.persistence.*;

@Entity
@Table(name = "order_detail")
public class OrderDetail {

    @EmbeddedId
    private OrderLineId orderLineId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private int quanity;

    @Column(name = "price")
    private double price;

    @Column(name = "amount")
    private double amount;

    public OrderLineId getOrderLineId() {
        return orderLineId;
    }

    public void setOrderLineId(OrderLineId orderLineId) {
        this.orderLineId = orderLineId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuanity() {
        return quanity;
    }

    public void setQuanity(int quanity) {
        this.quanity = quanity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
