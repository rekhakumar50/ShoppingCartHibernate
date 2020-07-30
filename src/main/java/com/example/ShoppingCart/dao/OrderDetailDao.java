package com.example.ShoppingCart.dao;

import com.example.ShoppingCart.model.OrderDetail;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDetailDao {

    List<OrderDetail> findOrderDetailByOrderId(Long orderId);

    Optional<OrderDetail> findByOrderIdAndProductCode(Long orderId, String productCode);

    void deleteByOrderIdAndProduct_Code(Long orderId, String productCode);

    void deleteByOrderId(Long orderId);

    void save(OrderDetail orderDetail);

    void update(OrderDetail orderDetail);
}
