package com.example.ShoppingCart.dao;

import com.example.ShoppingCart.model.OrderDetail;
import com.example.ShoppingCart.model.OrderLineId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface OrderDetailDao extends JpaRepository<OrderDetail, OrderLineId> {

    List<OrderDetail> findOrderDetailByOrderLineId_OrderId(Long orderId);

    @Query("SELECT sum(amount) from OrderDetail where order_id=:orderId")
    double findOrderDetailTotalAmount(@Param("orderId") long orderId);

    void deleteByOrderLineId_OrderIdAndProduct_Code(Long orderId, String productCode);

    void deleteByOrderLineId_OrderId(Long orderId);

}
