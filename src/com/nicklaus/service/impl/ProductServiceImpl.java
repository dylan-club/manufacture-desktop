package com.nicklaus.service.impl;

import com.nicklaus.dao.ProductDao;
import com.nicklaus.dao.ProductTypeDao;
import com.nicklaus.dao.factory.ProductFactory;
import com.nicklaus.dao.factory.ProductTypeFactory;
import com.nicklaus.domain.Product;
import com.nicklaus.domain.ProductType;
import com.nicklaus.service.ProductService;
import com.nicklaus.service.ProductTypeService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductServiceImpl implements ProductService {


    private static final ProductService productService = new ProductServiceImpl();

    private ProductServiceImpl(){}

    public static ProductService getInstance(){
        return productService;
    }
    private ProductTypeDao productTypeDao = ProductTypeFactory.getInstance();

    private ProductDao productDao = ProductFactory.getInstance();

    @Override
    public List<String> findAllTypes() {

        List<String> types = new ArrayList<>();

        List<ProductType> typeList = productTypeDao.findAll();

        if (typeList != null){
            typeList.forEach(productType -> {
                types.add(productType.getName());
            });
        }

        return types;
    }

    @Override
    public String add(Product product) {
        String message;

        Product findProduct = productDao.findByName(product.getName());

        //判断当前产品名称是否可用
        if (findProduct == null){
            //名称可用，进行保存
            product.setId(productDao.findMaxId()+1);
            product.setProductId(UUID.randomUUID().toString());
            boolean flag = productDao.save(product);

            if (flag){
                message = "添加成功！";
            }else{
                message = "添加失败，请联系管理员";
            }
        }else{
            message = "该产品名称已存在，请跟换一个！";
        }

        return message;
    }

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public void remove(Product product) {
        productDao.remove(product);
    }

    @Override
    public List<Product> findByName(String name) {
        List<Product> products = new ArrayList<>();
        Product product = productDao.findByName(name);

        if (product != null){
            products.add(product);
        }

        return products;
    }

    @Override
    public String modify(Product product) {
        String message;
        //查找该名称是否可用
        if (productDao.findByName(product.getName()) == null){

            productDao.modify(product);
            message = "修改成功！";
        }else{
            message = "该产品已存在，请更换一个！";
        }
        return message;
    }

    @Override
    public List<String> findAllNames() {
        return productDao.findAllNames();
    }
}
