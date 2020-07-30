package com.example.ShoppingCart.controller;

import com.example.ShoppingCart.dto.CustomerDto;
import com.example.ShoppingCart.dto.OrderDto;
import com.example.ShoppingCart.dto.ProductDto;
import com.example.ShoppingCart.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    void getOrderById() throws Exception {

        OrderDto orderDto = new OrderDto(1L, "xxxx", "chennai",
                "xxxx@gmail.com", "0123456789", 650.00, new Date());

        when(orderService.getOrder(orderDto.getOrderId())).thenReturn(orderDto);

        mockMvc.perform(get("/order/" + orderDto.getOrderId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("amount", is(orderDto.getAmount())));

    }

    @Test
    void updateOrder() throws Exception {

        OrderDto orderDto = new OrderDto(1L, "xxxx", "chennai",
                "xxxx@gmail.com", "0123456789", 650.00, new Date());

        CustomerDto customerDto = new CustomerDto("zyz", "chennai", "zyyz@gmail.com",
                "0123456789");

        String inputJson = "{\n" +
                "    \"name\": \"zyz\",\n" +
                "    \"address\": \"chennai\",\n" +
                "    \"email\": \"zyyz@gmail.com\",\n" +
                "    \"phone\": \"0123456789\"\n" +
                "}";

        mockMvc.perform(put("/order/" + orderDto.getOrderId())
                .content(inputJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void deleteOrder() throws Exception {

        mockMvc.perform(delete("/order/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    }

}
