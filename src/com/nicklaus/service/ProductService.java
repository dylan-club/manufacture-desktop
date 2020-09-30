package com.nicklaus.service;

import com.nicklaus.domain.Product;
import com.nicklaus.domain.ProductType;

import java.util.List;

public interface ProductService {
    List<String> findAllTypes();

    String add(Product product);

    List<Product> findAll();

    void remove(Product product);

    List<Product> findByName(String name);

    String modify(Product product);

    List<String> findAllNames();
}
