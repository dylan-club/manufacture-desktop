package com.nicklaus.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nicklaus.view.element.MyCheckBox;

import java.util.List;

public class Device {
    private int id; //id
    private String deviceId; //设备编号
    private String name; //设备名称
    private String deviceTypeName; //设备类型名称
    private String deviceSize; //设备规格
    private String deviceDetail; //设备描述
    private String deviceState;  //设备状态
    private String factoryName;  //所属工厂
    private String deviceRentalState;  //租用状态
    private String deviceRoot; //设备来源
    @JsonIgnore
    public MyCheckBox myCheckBox =  new MyCheckBox();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
    }

    public String getDeviceSize() {
        return deviceSize;
    }

    public void setDeviceSize(String deviceSize) {
        this.deviceSize = deviceSize;
    }

    public String getDeviceDetail() {
        return deviceDetail;
    }

    public void setDeviceDetail(String deviceDetail) {
        this.deviceDetail = deviceDetail;
    }

    public String getDeviceState() {
        return deviceState;
    }

    public void setDeviceState(String deviceState) {
        this.deviceState = deviceState;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getDeviceRentalState() {
        return deviceRentalState;
    }

    public void setDeviceRentalState(String deviceRentalState) {
        this.deviceRentalState = deviceRentalState;
    }

    public String getDeviceRoot() {
        return deviceRoot;
    }

    public void setDeviceRoot(String deviceRoot) {
        this.deviceRoot = deviceRoot;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", deviceId='" + deviceId + '\'' +
                ", name='" + name + '\'' +
                ", deviceTypeName='" + deviceTypeName + '\'' +
                ", deviceSize='" + deviceSize + '\'' +
                ", deviceDetail='" + deviceDetail + '\'' +
                ", deviceState='" + deviceState + '\'' +
                ", factoryName='" + factoryName + '\'' +
                ", deviceRentalState='" + deviceRentalState + '\'' +
                ", deviceRoot='" + deviceRoot + '\'' +
                '}';
    }
}
