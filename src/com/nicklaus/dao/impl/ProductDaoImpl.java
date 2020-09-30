package com.nicklaus.dao.impl;

import com.nicklaus.dao.ProductDao;
import com.nicklaus.dao.ProductTypeDao;
import com.nicklaus.domain.Device;
import com.nicklaus.domain.Product;
import com.nicklaus.domain.ProductType;
import com.nicklaus.util.FileUtils;
import com.nicklaus.util.NavigationUtils;

import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {

    //单例模式
    private static final ProductDao productDao = new ProductDaoImpl();

    private ProductDaoImpl(){}

    public static ProductDao getInstance(){
        return productDao;
    }

    private FileUtils<Product> productFileUtils = new FileUtils<Product>();

    @Override
    public Product findByName(String name) {
        Product product = null;
        List<Product> productList = getProductList();
        if (productList != null){
            for (Product myProduct: productList) {
                if (myProduct.getName().equals(name)){
                    product = myProduct;
                }
            }
        }

        return product;
    }

    @Override
    public int findMaxId() {
        //找到id最大值
        List<Product> productList = getProductList();

        if (productList == null){
            return 0;
        }else{
            int maxId = 0;
            for (Product product:
                    productList) {
                if (product.getId() > maxId){
                    maxId = product.getId();
                }
            }

            return maxId;
        }
    }

    @Override
    public boolean save(Product product) {
        List<Product> productList = getProductList();

        if (productList == null){
            productList = new ArrayList<>();
        }

        productList.add(product);
        return productFileUtils.writeJsonList(NavigationUtils.productFile,productList);
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = getProductList();

        if (products == null){
            products = new ArrayList<>();
        }

        return products;
    }

    @Override
    public void modifyByProductType(String oldName, String newName) {
        List<Product> productList = getProductList();
        if (productList != null){
            //修改产品信息列表
            productList.forEach(product -> {
                if (product.getProductTypeName().equals(oldName)){
                    product.setProductTypeName(newName);
                }
            });


            //将修改信息回写到文件
            productFileUtils.writeJsonList(NavigationUtils.productFile,productList);
        }
    }

    @Override
    public void removeProductType(String name) {
        List<Product> productList = getProductList();
        if (productList != null){
            //修改产品信息列表
            productList.forEach(product -> {
                if (product.getProductTypeName().equals(name)){
                    product.setProductTypeName(null);
                }
            });


            //将修改信息回写到文件
            productFileUtils.writeJsonList(NavigationUtils.productFile,productList);
        }
    }

    @Override
    public void remove(Product product) {
        List<Product> productList = getProductList();

        for (Product myProduct:
                productList) {
            if (myProduct.getId() == product.getId()){
                productList.remove(myProduct);
                break;
            }
        }

        //更新文件信息
        productFileUtils.writeJsonList(NavigationUtils.productFile,productList);
    }

    @Override
    public void modify(Product product) {
        List<Product> products = getProductList();

        for (Product myProduct:
                products) {
            if (myProduct.getId() == product.getId()){
                myProduct.setName(product.getName());
                myProduct.setProductTypeName(product.getProductTypeName());
                myProduct.setProductDetail(product.getProductDetail());
                myProduct.setProductSize(product.getProductSize());
                break;
            }
        }

        //将修改写入文件
        productFileUtils.writeJsonList(NavigationUtils.productFile,products);
    }

    @Override
    public List<String> findAllNames() {
        List<String> productNames = new ArrayList<>();

        if (getProductList() != null){
            getProductList().forEach(product -> {
                productNames.add(product.getName());
            });
        }

        return productNames;
    }

    @Override
    public String findByProductId(String productId) {
        String productName =null;

        if (getProductList() != null){
            for (Product product:
                    getProductList()) {
                if (product.getProductId().equals(productId)){
                    productName = product.getName();
                    break;
                }
            }
        }

        return productName;
    }

    private List<Product> getProductList(){
        return productFileUtils.readJsonFileAsList(NavigationUtils.productFile, Product.class);
    }
}
