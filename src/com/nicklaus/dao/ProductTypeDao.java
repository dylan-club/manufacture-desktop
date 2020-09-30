package com.nicklaus.dao;

import com.nicklaus.domain.ProductType;

import java.util.List;

public interface ProductTypeDao {
    ProductType findByName(String productTypeName);

    int findMaxId();

    boolean save(ProductType type);

    List<ProductType> findAll();

    void remove(ProductType type);

    void modify(ProductType type);

    ProductType findById(int id);
}
