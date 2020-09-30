package com.nicklaus.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nicklaus.view.element.MyCheckBox;

public class DeviceCapacity {

    private int id;

    private String deviceId;

    private String productId;

    private int amount;

    @JsonIgnore
    public MyCheckBox myCheckBox = new MyCheckBox();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "DeviceCapacity{" +
                "id=" + id +
                ", deviceId='" + deviceId + '\'' +
                ", productId='" + productId + '\'' +
                ", amount=" + amount +
                '}';
    }
}
