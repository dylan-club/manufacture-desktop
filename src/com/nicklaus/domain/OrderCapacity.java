package com.nicklaus.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nicklaus.view.element.MyCheckBox;

public class OrderCapacity {

    private int id;
    private String orderId;
    private String deviceId;
    private String deviceName;
    private String startDate;
    private String endDate;
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

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "OrderCapacity{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
