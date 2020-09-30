package com.nicklaus.dao;

import com.nicklaus.domain.Device;
import com.nicklaus.domain.DeviceCapacity;

import java.util.Collection;
import java.util.List;

public interface DeviceDao {
    Device findByName(String name);

    int findMaxId();

    boolean save(Device device);

    List<Device> findAll();

    List<Device> findByDeviceType(String name);

    void modifyByDeviceType(String oldTypeName, String newTypeName);

    void remove(Device removeDevice);

    void modify(Device device);

    void modifyDeviceState(Device switchDevice);

    List<Device> findByFactoryName(String factoryName);

    List<Device> findByNameAndFactoryName(String name, String factoryName);

    void modifyToAdmin(Device removeDevice);

    List<Device> findByRentalStateAndDeviceState(String rentalState, String deviceState);

    void modifyRentalStateAndFactoryName(Device device, String factoryName);

    String findByDeviceId(String deviceId);


    Device findForDeviceId(String deviceId);

    void modifyDeviceStateByDeviceIdAndState(String deviceId, String deviceState);
}
