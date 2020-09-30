package com.nicklaus.domain;

import com.nicklaus.view.element.MyCheckBox;

public class CapacityView {
    private int id;
    private String deviceName;
    private String productName;
    private int amount;
    public MyCheckBox myCheckBox = new MyCheckBox();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
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
}
