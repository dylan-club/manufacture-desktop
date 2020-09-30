package com.nicklaus.service;

import com.nicklaus.domain.ProductType;

import java.util.List;

public interface ProductTypeService {
    String add(String productTypeName);

    List<ProductType> findAll();

    List<ProductType> findByName(String name);

    void remove(ProductType type);

    String modify(ProductType type);
}
