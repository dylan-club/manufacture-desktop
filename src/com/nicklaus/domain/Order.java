package com.nicklaus.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nicklaus.view.element.MyCheckBox;

import java.time.LocalDate;
import java.util.Date;

public class Order {

    private int id;
    private String orderId;
    private String productName;
    private int amount;
    private String endDate;
    private String startDate;
    private String salemanNickName;
    private String salemanPhone;

    @JsonFormat
    private String orderState;
    private String factoryName;

    @JsonIgnore
    public MyCheckBox myCheckBox = new MyCheckBox();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getSalemanNickName() {
        return salemanNickName;
    }

    public void setSalemanNickName(String salemanNickName) {
        this.salemanNickName = salemanNickName;
    }

    public String getSalemanPhone() {
        return salemanPhone;
    }

    public void setSalemanPhone(String salemanPhone) {
        this.salemanPhone = salemanPhone;
    }


    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", productName='" + productName + '\'' +
                ", amount=" + amount +
                ", endDate=" + endDate +
                ", startDate=" + startDate +
                ", salemanNickName='" + salemanNickName + '\'' +
                ", salemanPhone='" + salemanPhone + '\'' +
                ", orderState='" + orderState + '\'' +
                ", factoryName='" + factoryName + '\'' +
                '}';
    }
}
