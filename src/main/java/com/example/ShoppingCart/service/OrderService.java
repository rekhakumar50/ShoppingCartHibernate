package com.example.ShoppingCart.service;

import com.example.ShoppingCart.dao.OrderDao;
import com.example.ShoppingCart.dao.OrderDetailDao;
import com.example.ShoppingCart.dto.CustomerInfo;
import com.example.ShoppingCart.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderDetailDao orderDetailDao;

    public List<Order> getOrders() {
        return orderDao.findAll();
    }

    public Order getOrder(final Long orderId) {
        return orderDao.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("OrderId " + orderId + " not found"));
    }

    public void updateOrder(final Long orderId, final CustomerInfo customerInfo) {
        Order order = orderDao.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("OrderId " + orderId + " not found"));
        order.setCustomerAddress(customerInfo.getAddress());
        order.setCustomerEmail(customerInfo.getEmail());
        order.setCustomerName(customerInfo.getName());
        order.setCustomerPhone(customerInfo.getPhone());
        orderDao.save(order);
    }

    public void removeOrder(final Long orderId) {
        Order order = orderDao.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("OrderId " + orderId + " not found"));
        orderDao.delete(order);
        orderDetailDao.deleteByOrderLineId_OrderId(orderId);
    }
}
