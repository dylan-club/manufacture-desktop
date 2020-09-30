package com.nicklaus.service;

import com.nicklaus.domain.Device;
import com.nicklaus.domain.User;
import javafx.collections.ObservableList;

import java.util.List;

public interface DeviceService {
    String add(Device device, User loginUser);

    List<String> findAllTypes();

    List<Device> findAll();

    List<Device> findByName(String name);

    void remove(Device removeDevice);

    String modify(Device device);

    void modifyDeviceState(Device switchDevice);

    List<Device> findByFactoryName(String factoryName);

    List<Device> findForFactory(String name, String factoryName);

    void withDrawDevice(Device removeDevice);

    List<Device> findAvailableDevices();

    void rentalDevice(Device device, String factoryName);

    List<String> findAvailableDevicesByProductName(String productName, String factoryName);

    void modifyDeviceStateByDeviceIdAndState(String deviceId, String deviceState);
}
