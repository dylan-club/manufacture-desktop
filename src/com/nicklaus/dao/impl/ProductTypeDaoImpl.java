package com.nicklaus.dao.impl;

import com.nicklaus.dao.ProductTypeDao;
import com.nicklaus.domain.ProductType;
import com.nicklaus.util.FileUtils;
import com.nicklaus.util.NavigationUtils;

import java.util.ArrayList;
import java.util.List;

public class ProductTypeDaoImpl implements ProductTypeDao {

    //单例模式
    private static final ProductTypeDao productTypeDao = new ProductTypeDaoImpl();

    private ProductTypeDaoImpl(){}

    public static ProductTypeDao getInstance(){
        return productTypeDao;
    }

    private FileUtils<ProductType> productTypeFileUtils = new FileUtils<ProductType>();

    @Override
    public ProductType findByName(String productTypeName) {

        ProductType type = null;
        List<ProductType> typeList = getProductTypeList();
        if (typeList != null){
            for (ProductType productType: typeList) {
                if (productType.getName().equals(productTypeName)){
                    type = productType;
                }
            }
        }

        return type;
    }

    @Override
    public int findMaxId() {
        //找到id最大值
        List<ProductType> typeList = getProductTypeList();

        if (typeList == null){
            return 0;
        }else{
            int maxId = 0;
            for (ProductType productType:
                 typeList) {
                if (productType.getId() > maxId){
                    maxId = productType.getId();
                }
            }

            return maxId;
        }
    }

    @Override
    public boolean save(ProductType type) {
        List<ProductType> typeList = getProductTypeList();

        if (typeList == null){
            typeList = new ArrayList<>();
        }

        typeList.add(type);
        return productTypeFileUtils.writeJsonList(NavigationUtils.productTypeFile,typeList);
    }

    @Override
    public List<ProductType> findAll() {

        List<ProductType> typeList = getProductTypeList();

        if (typeList == null){
            typeList = new ArrayList<>();
        }

        return typeList;
    }

    @Override
    public void remove(ProductType type) {
        List<ProductType> productTypeList = getProductTypeList();

        for (ProductType productType:
             productTypeList) {
            if (productType.getId() == type.getId()){
                productTypeList.remove(productType);
                break;
            }
        }

        //更新文件信息
        productTypeFileUtils.writeJsonList(NavigationUtils.productTypeFile,productTypeList);
    }

    @Override
    public void modify(ProductType type) {
        List<ProductType> typeList = getProductTypeList();

        for (ProductType productType:
             typeList) {
            if (productType.getId() == type.getId()){
                productType.setName(type.getName());
                break;
            }
        }

        //将修改写入文件
        productTypeFileUtils.writeJsonList(NavigationUtils.productTypeFile,typeList);
    }

    @Override
    public ProductType findById(int id) {
        List<ProductType> typeList = getProductTypeList();
        ProductType oldType = null;
        for (ProductType productType:
                typeList) {
            if (productType.getId() == id){
                oldType = productType;
                break;
            }
        }

        return oldType;
    }

    private List<ProductType> getProductTypeList(){
        return productTypeFileUtils.readJsonFileAsList(NavigationUtils.productTypeFile, ProductType.class);
    }
}
