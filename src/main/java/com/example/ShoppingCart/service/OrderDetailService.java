package com.example.ShoppingCart.service;

import com.example.ShoppingCart.dao.OrderDao;
import com.example.ShoppingCart.dao.OrderDetailDao;
import com.example.ShoppingCart.dao.ProductDao;
import com.example.ShoppingCart.dto.*;
import com.example.ShoppingCart.model.Order;
import com.example.ShoppingCart.model.OrderDetail;
import com.example.ShoppingCart.model.OrderLineId;
import com.example.ShoppingCart.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional
public class OrderDetailService {

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    public List<OrderDetailInfo> getAllOrderDetailsByOrderId(final Long orderId) {
        List<OrderDetail> orderDetails = orderDetailDao.findOrderDetailByOrderLineId_OrderId(orderId);

        List<OrderDetailInfo> orderDetailInfoList = new ArrayList<>();

        orderDetails.forEach(orderDetail -> {
            OrderDetailInfo orderDetailInfo = new OrderDetailInfo();
            orderDetailInfo.setProductNo(orderDetail.getOrderLineId().getLineNum());
            orderDetailInfo.setCode(orderDetail.getProduct().getCode());
            orderDetailInfo.setName(orderDetail.getProduct().getName());
            orderDetailInfo.setPrice(orderDetail.getProduct().getPrice());
            orderDetailInfo.setQuantity(orderDetail.getQuanity());
            orderDetailInfo.setAmount(orderDetail.getAmount());

            orderDetailInfoList.add(orderDetailInfo);
        });

        return orderDetailInfoList;
    }

    public void saveOrderDetails(final CartInfo cartInfo) {
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setAmount(cartInfo.getAmountTotal());

        CustomerInfo customerInfo = cartInfo.getCustomerInfo();
        order.setCustomerName(customerInfo.getName());
        order.setCustomerEmail(customerInfo.getEmail());
        order.setCustomerPhone(customerInfo.getPhone());
        order.setCustomerAddress(customerInfo.getAddress());

        orderDao.save(order);

        AtomicInteger items = new AtomicInteger();

        cartInfo.getCartLines().forEach(cartLineInfo -> {
            OrderDetail orderDetail = new OrderDetail();
            OrderLineId orderLineId = new OrderLineId();
            orderLineId.setOrderId(order.getId());
            orderLineId.setLineNum(items.incrementAndGet());
            orderDetail.setOrderLineId(orderLineId);
            orderDetail.setAmount(cartLineInfo.getAmount());
            orderDetail.setPrice(cartLineInfo.getProductInfo().getPrice());
            orderDetail.setQuanity(cartLineInfo.getQuantity());

            String code = cartLineInfo.getProductInfo().getCode();
            Product product = productDao.findById(code)
                    .orElseThrow(() -> new EntityNotFoundException("Product code " + code + " not found!"));
            orderDetail.setProduct(product);

            orderDetailDao.save(orderDetail);
        });
    }

    public void updateCartItem(final long orderId, final CartLineInfo cartLineInfo) {
        Order order = orderDao.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("orderId " + orderId + " not found!"));

        List<OrderDetail> orderDetails = orderDetailDao.findOrderDetailByOrderLineId_OrderId(orderId);
        OrderDetail orderDetail = orderDetails.stream()
                .filter(o -> cartLineInfo.getProductInfo().getCode().equals(o.getProduct().getCode()))
                .findAny()
                .orElseThrow(() -> new EntityNotFoundException("product Code " + cartLineInfo.getProductInfo().getCode() + " not found!"));
        orderDetail.setQuanity(cartLineInfo.getQuantity());
        orderDetail.setAmount(cartLineInfo.getAmount());
        orderDetailDao.save(orderDetail);

        double totalAmount = orderDetailDao.findOrderDetailTotalAmount(orderId);
        order.setOrderDate(new Date());
        order.setAmount(totalAmount);
        orderDao.save(order);
    }

    public void deleteCartItem(final long orderId, final String productCode) {
        Order order = orderDao.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("orderId " + orderId + " not found!"));

        orderDetailDao.deleteByOrderLineId_OrderIdAndProduct_Code(orderId, productCode);

        double totalAmount = orderDetailDao.findOrderDetailTotalAmount(orderId);
        order.setOrderDate(new Date());
        order.setAmount(totalAmount);
        orderDao.save(order);
    }

}
