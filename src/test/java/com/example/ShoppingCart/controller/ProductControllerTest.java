package com.example.ShoppingCart.controller;

import com.example.ShoppingCart.dto.ProductDto;
import com.example.ShoppingCart.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    @WithMockUser(username = "manager", password = "password", roles = "MANAGER")
    void findAllProducts() throws Exception {

        ProductDto productDto = new ProductDto("S001", "Core Java", 100.0, null, new Date());
        ProductDto productDto1 = new ProductDto("S002", "Spring for Beginners", 50.0, null, new Date());

        List<ProductDto> productDtos = new ArrayList<>();
        productDtos.add(productDto);
        productDtos.add(productDto1);

        when(productService.getAllProducts()).thenReturn(productDtos);

        mockMvc.perform(get("/admin/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].code", is(productDto.getCode())));
    }

    @Test
    @WithMockUser(username = "manager", password = "password", roles = "MANAGER")
    void getProductById() throws Exception {

        ProductDto productDto = new ProductDto("S001", "Core Java", 100.0, null, new Date());

        when(productService.getProduct(productDto.getCode())).thenReturn(productDto);

        mockMvc.perform(get("/admin/products/" + productDto.getCode()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("price", is(productDto.getPrice())));

    }

    @Test
    @WithMockUser(username = "manager", password = "password", roles = "MANAGER")
    void getProductByName() throws Exception {

        String nameToFind = "spring";

        ProductDto productDto = new ProductDto("S001", "Spring DI", 100.0, null, new Date());
        ProductDto productDto1 = new ProductDto("S002", "Spring MVC", 50.0, null, new Date());

        List<ProductDto> productDtos = new ArrayList<>();
        productDtos.add(productDto);
        productDtos.add(productDto1);

        when(productService.getProductByName(nameToFind)).thenReturn(productDtos);

        mockMvc.perform(get("/admin/products/name?name=" + nameToFind))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(productDto.getName())))
                .andExpect(jsonPath("$[1].name", is(productDto1.getName())));


    }

    @Test
    @WithMockUser(username = "manager", password = "password", roles = "MANAGER")
    void addProduct() throws Exception {

        String inputJson = "{\n" +
                "    \"code\": \"S007\",\n" +
                "    \"name\": \"Core Java\",\n" +
                "    \"price\": 101.0,\n" +
                "    \"pictureUrl\": null\n" +
                "}";

        mockMvc.perform(post("/admin/products").content(inputJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @WithMockUser(username = "manager", password = "password", roles = "MANAGER")
    void updateProduct() throws Exception {

        String inputJson = "{\n" +
                "    \"code\": \"S007\",\n" +
                "    \"name\": \"Core Java\",\n" +
                "    \"price\": 100.0,\n" +
                "    \"pictureUrl\": null\n" +
                "}";

        mockMvc.perform(put("/admin/products").content(inputJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @WithMockUser(username = "manager", password = "password", roles = "MANAGER")
    void deleteProduct() throws Exception {

        mockMvc.perform(delete("/admin/products/S007").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    }
}
