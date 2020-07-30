package com.example.ShoppingCart.service;

import com.example.ShoppingCart.dao.ProductDao;
import com.example.ShoppingCart.dto.ProductDto;
import com.example.ShoppingCart.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    public List<ProductDto> getAllProducts() {
        List<Product> products = productDao.findAll();
        List<ProductDto> productInfoList = new ArrayList<>();
        products.forEach(product -> {
            productInfoList.add(getProductInfo(product));
        });
        return productInfoList;
    }

    public ProductDto getProduct(final String code) {
        Product product = productDao
                .findById(code)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        return getProductInfo(product);
    }

    public List<ProductDto> getProductByName(final String name) {
        List<Product> products = productDao.findByNameContaining(name);
        List<ProductDto> productInfoList = new ArrayList<>();
        products.forEach(product -> {
            productInfoList.add(getProductInfo(product));
        });
        return productInfoList;
    }

    public void save(Product product) {
        product.setCreateDate(new Date());
        productDao.save(product);
    }

    public void updateProduct(final Product product) {
        product.setCreateDate(new Date());
        productDao.update(product);
    }

    public void deleteProducts(final String code) {
        productDao.deleteById(code);
    }

    private ProductDto getProductInfo(Product product) {
        ProductDto productInfo = new ProductDto();
        productInfo.setCode(product.getCode());
        productInfo.setName(product.getName());
        productInfo.setPrice(product.getPrice());
        productInfo.setPictureUrl(product.getPictureUrl());
        productInfo.setCreateDate(product.getCreateDate());
        return productInfo;
    }

}
