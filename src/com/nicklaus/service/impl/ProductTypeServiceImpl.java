package com.nicklaus.service.impl;

import com.nicklaus.dao.ProductDao;
import com.nicklaus.dao.ProductTypeDao;
import com.nicklaus.dao.factory.ProductFactory;
import com.nicklaus.dao.factory.ProductTypeFactory;
import com.nicklaus.domain.ProductType;
import com.nicklaus.service.ProductTypeService;

import java.util.ArrayList;
import java.util.List;

public class ProductTypeServiceImpl implements ProductTypeService {
    private static final ProductTypeService productTypeService = new ProductTypeServiceImpl();

    private ProductTypeServiceImpl(){}

    public static ProductTypeService getInstance(){
        return productTypeService;
    }

    private ProductTypeDao productTypeDao = ProductTypeFactory.getInstance();

    private ProductDao productDao = ProductFactory.getInstance();

    @Override
    public String add(String productTypeName) {
        String message;

        ProductType productType = productTypeDao.findByName(productTypeName);

        //判断当前产品类别是否可用
        if (productType == null){
            //名称可用，进行保存
            ProductType type = new ProductType();
            type.setId(productTypeDao.findMaxId()+1);
            type.setName(productTypeName);
            boolean flag = productTypeDao.save(type);

            if (flag){
                message = "添加成功！";
            }else{
                message = "添加失败，请联系管理员";
            }
        }else{
            message = "该产品类别已存在，请跟换一个！";
        }

        return message;
    }

    @Override
    public List<ProductType> findAll() {
        return productTypeDao.findAll();
    }

    @Override
    public List<ProductType> findByName(String name) {
        List<ProductType> types = new ArrayList<>();
        ProductType type = productTypeDao.findByName(name);

        if (type != null){
            types.add(type);
        }

        return types;
    }

    @Override
    public void remove(ProductType type) {
        productTypeDao.remove(type);
        //同时清空产品信息的类别
        productDao.removeProductType(type.getName());
    }

    @Override
    public String modify(ProductType type) {
        String message;
        //查找该名称是否可用
        if (productTypeDao.findByName(type.getName()) == null){

            ProductType oldType = productTypeDao.findById(type.getId());
            productTypeDao.modify(type);
            //修改类别的同时，将属于该类别的产品一同修改
            productDao.modifyByProductType(oldType.getName(), type.getName());
            message = "修改成功！";
        }else{
            message = "该产品类别已存在，请更换一个！";
        }
        return message;
    }
}
