package com.codegym.service;

import com.codegym.model.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService implements IProductService{
    private static List<Product> productList = new ArrayList<>();
    private static int autoIncreasedId = 0;

    static {
        productList.add(new Product(++autoIncreasedId, "OMO", new BigDecimal("87000"), 12));
        productList.add(new Product(++autoIncreasedId, "Clear", new BigDecimal("35000"), 80));
        productList.add(new Product(++autoIncreasedId, "H&S", new BigDecimal("29000"), 80));
        productList.add(new Product(++autoIncreasedId, "Vinamilk", new BigDecimal("25000"), 300));
    }


    @Override
    public List<Product> findAll() {
        return productList;
    }

    @Override
    public Product findOne(int id) {
        for (Product product : productList) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    @Override
    public Product save(Product Product) {
        return null;
    }

    @Override
    public List<Product> save(List<Product> Products) {
        return null;
    }

    @Override
    public boolean exists(int id) {
        return false;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void delete(Product Product) {

    }

    @Override
    public void delete(List<Product> Products) {

    }
}
