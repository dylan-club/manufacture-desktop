package com.nicklaus.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nicklaus.view.element.MyCheckBox;

public class Product {
    private int id; //id
    private String productId; //产品编号
    private String name; //产品名称
    private String productTypeName; //产品类型
    private String productSize; //产品规格
    private String productDetail; //产品描述
    @JsonIgnore
    public MyCheckBox myCheckBox =  new MyCheckBox();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productId='" + productId + '\'' +
                ", name='" + name + '\'' +
                ", productTypeName='" + productTypeName + '\'' +
                ", productSize='" + productSize + '\'' +
                ", productDetail='" + productDetail + '\'' +
                '}';
    }
}
