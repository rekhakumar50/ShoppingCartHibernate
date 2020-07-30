package com.example.ShoppingCart.controller;

import com.example.ShoppingCart.dto.OrderDetailDto;
import com.example.ShoppingCart.service.OrderDetailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderDetailController.class)
class OrderDetailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderDetailService orderDetailService;

    @Test
    void getOrderDetailByOrderId() throws Exception {

        OrderDetailDto orderDetailDto = new OrderDetailDto(1, "S001", "Core Java",
                100.00, 2, 200.00);
        OrderDetailDto orderDetailDto1 = new OrderDetailDto(2, "S003", "Swift for Beginners",
                120.00, 1, 120.00);
        OrderDetailDto orderDetailDto2 = new OrderDetailDto(3, "S005",
                "CSharp Tutorial for Beginers", 110.00, 3, 330.00);

        List<OrderDetailDto> orderDetailDtos = new ArrayList<>();
        orderDetailDtos.add(orderDetailDto);
        orderDetailDtos.add(orderDetailDto1);
        orderDetailDtos.add(orderDetailDto2);

        when(orderDetailService.getAllOrderDetailsByOrderId(1L)).thenReturn(orderDetailDtos);

        mockMvc.perform(get("/order-details/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].code", is(orderDetailDto.getCode())))
                .andExpect(jsonPath("$[1].code", is(orderDetailDto1.getCode())));

    }

    @Test
    void addOrderAndOrderDetails() throws Exception {

        mockMvc.perform(post("/order-details").content(getJsonToPost())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void updateOrderDetail() throws Exception {

        String inputJson = "{\n" +
                "\t\"productInfo\": {\n" +
                "\t\t\"code\": \"S001\",\n" +
                "\t\t\"name\": \"Core Java\",\n" +
                "\t\t\"price\": \"100.0\"\n" +
                "\t},\n" +
                "\t\"quantity\": 1\n" +
                "}";

        mockMvc.perform(put("/order-details/1")
                .content(inputJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void deleteOrderDetail() throws Exception {

        mockMvc.perform(delete("/order-details/1/S001").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    }

    private String getJsonToPost() {
        return "{\n" +
                "\t\"customerInfo\": {\n" +
                "\t\t\"name\": \"zzz\",\n" +
                "\t\t\"address\": \"chennai\",\n" +
                "\t\t\"email\": \"zzzz@gmail.com\",\n" +
                "\t\t\"phone\": \"0123456789\"\n" +
                "\t},\n" +
                "\t\"cartLines\": [\n" +
                "\t\t{\n" +
                "\t\t\t\"productInfo\": {\n" +
                "\t\t\t\t\"code\": \"S001\",\n" +
                "\t\t\t\t\"name\": \"Core Java\",\n" +
                "\t\t\t\t\"price\": \"100.00\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"quantity\": 2\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"productInfo\": {\n" +
                "\t\t\t\t\"code\": \"S003\",\n" +
                "\t\t\t\t\"name\": \"Swift for Beginners\",\n" +
                "\t\t\t\t\"price\": \"120.00\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"quantity\": 1\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"productInfo\": {\n" +
                "\t\t\t\t\"code\": \"S005\",\n" +
                "\t\t\t\t\"name\": \"CSharp Tutorial for Beginers\",\n" +
                "\t\t\t\t\"price\": \"110.00\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"quantity\": 3\n" +
                "\t\t}\n" +
                "\t]\n" +
                "}";
    }

}
