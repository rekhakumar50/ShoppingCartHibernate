package com.example.ShoppingCart.service;

import com.example.ShoppingCart.dao.OrderDao;
import com.example.ShoppingCart.dao.OrderDetailDao;
import com.example.ShoppingCart.dto.CustomerDto;
import com.example.ShoppingCart.dto.OrderDto;
import com.example.ShoppingCart.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderDetailDao orderDetailDao;

    public List<OrderDto> getOrders() {
        List<Order> orders = orderDao.findAll();
        List<OrderDto> orderDtoList = new ArrayList<>();
        orders.forEach(order -> {
            orderDtoList.add(getOrderInfo(order));
        });
        return orderDtoList;
    }

    public OrderDto getOrder(final Long orderId) {
        Order order = orderDao.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("OrderId " + orderId + " not found"));
        return getOrderInfo(order);
    }

    public void updateOrder(final Long orderId, final CustomerDto customerInfo) {
        Order order = orderDao.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("OrderId " + orderId + " not found"));
        order.setCustomerAddress(customerInfo.getAddress());
        order.setCustomerEmail(customerInfo.getEmail());
        order.setCustomerName(customerInfo.getName());
        order.setCustomerPhone(customerInfo.getPhone());
        orderDao.update(order);
    }

    public void removeOrder(final Long orderId) {
        Order order = orderDao.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("OrderId " + orderId + " not found"));
        orderDao.delete(order);
        orderDetailDao.deleteByOrderId(orderId);
    }

    private OrderDto getOrderInfo(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(order.getId());
        orderDto.setCustomerName(order.getCustomerName());
        orderDto.setCustomerEmail(order.getCustomerEmail());
        orderDto.setCustomerPhone(order.getCustomerPhone());
        orderDto.setCustomerAddress(order.getCustomerAddress());
        orderDto.setAmount(order.getAmount());
        orderDto.setOrderDate(order.getOrderDate());
        return orderDto;
    }

}
