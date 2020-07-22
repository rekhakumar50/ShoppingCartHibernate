package com.example.ShoppingCart.service;

import com.example.ShoppingCart.dao.ProductDao;
import com.example.ShoppingCart.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    public List<Product> getAllProducts() {
        return productDao.findAll();
    }

    public Product getProduct(final String code) {
        return productDao
                .findById(code)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    public Product save(Product product) {
        return productDao.save(product);
    }

    public List<Product> getProductByName(final String name) {
        return productDao.findByNameContaining(name);
    }

    public void updateProduct(final Product product) {
        productDao.save(product);
    }

    public void deleteProducts(final String code) {
        productDao.deleteById(code);
    }

}
