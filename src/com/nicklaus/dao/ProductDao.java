package com.nicklaus.dao;

import com.nicklaus.domain.Product;
import com.nicklaus.domain.ProductType;

import java.util.List;

public interface ProductDao {
    Product findByName(String name);

    int findMaxId();

    boolean save(Product product);

    List<Product> findAll();

    void modifyByProductType(String oldName, String newName);

    void removeProductType(String name);

    void remove(Product product);

    void modify(Product product);

    List<String> findAllNames();

    String findByProductId(String productId);
}
