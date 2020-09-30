package com.nicklaus.dao;

import com.nicklaus.domain.DeviceCapacity;

import java.util.List;

public interface DeviceCapacityDao {
    DeviceCapacity findByDeviceIdAndProductId(String deviceId, String productId);

    int findMaxId();

    boolean save(DeviceCapacity capacity);

    List<DeviceCapacity> findByDeviceId(String deviceId);

    void removeById(int id);

    List<DeviceCapacity> findByProductId(String productId);
}
