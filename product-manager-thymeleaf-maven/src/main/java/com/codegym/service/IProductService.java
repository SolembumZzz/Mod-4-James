package com.codegym.service;

import com.codegym.model.Product;

import java.util.List;

public interface IProductService {
    List<Product> findAll();

    Product findOne(int id);

    Product save(Product Product);

    List<Product> save(List<Product> Products);

    boolean exists(int id);

    void delete(int id);

    void delete(Product Product);

    void delete(List<Product> Products);
}
