package com.example.ShoppingCart.service;

import com.example.ShoppingCart.dao.OrderDaoImpl;
import com.example.ShoppingCart.dao.OrderDetailDaoImpl;
import com.example.ShoppingCart.dao.ProductDaoImpl;
import com.example.ShoppingCart.dto.*;
import com.example.ShoppingCart.model.Order;
import com.example.ShoppingCart.model.OrderDetail;
import com.example.ShoppingCart.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailDaoImpl orderDetailDao;

    @Autowired
    private OrderDaoImpl orderDao;

    @Autowired
    private ProductDaoImpl productDao;

    public List<OrderDetailDto> getAllOrderDetailsByOrderId(final Long orderId) {
        List<OrderDetail> orderDetails = orderDetailDao.findOrderDetailByOrderId(orderId);
        List<OrderDetailDto> orderDetailInfoList = new ArrayList<>();

        AtomicInteger productNo = new AtomicInteger(0);
        orderDetails.forEach(orderDetail -> {
            OrderDetailDto orderDetailInfo = new OrderDetailDto();
            orderDetailInfo.setProductNo(productNo.incrementAndGet());
            orderDetailInfo.setCode(orderDetail.getProduct().getCode());
            orderDetailInfo.setName(orderDetail.getProduct().getName());
            orderDetailInfo.setPrice(orderDetail.getProduct().getPrice());
            orderDetailInfo.setQuantity(orderDetail.getQuanity());
            orderDetailInfo.setAmount(orderDetail.getAmount());

            orderDetailInfoList.add(orderDetailInfo);
        });

        return orderDetailInfoList;
    }

    public long saveOrderDetails(final CartDto cartInfo) {
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setAmount(cartInfo.getAmountTotal());

        CustomerDto customerInfo = cartInfo.getCustomerInfo();
        order.setCustomerName(customerInfo.getName());
        order.setCustomerEmail(customerInfo.getEmail());
        order.setCustomerPhone(customerInfo.getPhone());
        order.setCustomerAddress(customerInfo.getAddress());

        long orderId = orderDao.save(order);

        cartInfo.getCartLines().forEach(cartLineInfo -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setAmount(cartLineInfo.getAmount());
            orderDetail.setPrice(cartLineInfo.getProductInfo().getPrice());
            orderDetail.setQuanity(cartLineInfo.getQuantity());

            String code = cartLineInfo.getProductInfo().getCode();
            Product product = productDao.findById(code)
                    .orElseThrow(() -> new EntityNotFoundException("Product code " + code + " not found!"));
            orderDetail.setProduct(product);

            orderDetailDao.save(orderDetail);
        });
        return orderId;
    }

    public void updateCartItem(final long orderId, final CartLineDto cartLineInfo) {
        Order order = orderDao.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("orderId " + orderId + " not found!"));

        OrderDetail orderDetail =
                orderDetailDao.findByOrderIdAndProductCode(orderId, cartLineInfo.getProductInfo().getCode())
                .orElseThrow(() -> new EntityNotFoundException("product Code " + cartLineInfo.getProductInfo().getCode() + " not found!"));
        orderDetail.setQuanity(cartLineInfo.getQuantity());
        orderDetail.setAmount(cartLineInfo.getAmount());
        orderDetailDao.update(orderDetail);

        order.setOrderDate(new Date());
        order.setAmount(getTotalAmount(orderId));
        orderDao.update(order);
    }

    public void deleteCartItem(final long orderId, final String productCode) {
        Order order = orderDao.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("orderId " + orderId + " not found!"));

        orderDetailDao.deleteByOrderIdAndProduct_Code(orderId, productCode);

        order.setOrderDate(new Date());
        order.setAmount(getTotalAmount(orderId));
        orderDao.update(order);
    }

    private double getTotalAmount(long orderId) {
        List<OrderDetail> orderDetails = orderDetailDao.findOrderDetailByOrderId(orderId);
        return orderDetails.stream().mapToDouble(OrderDetail::getAmount).sum();
    }

}
