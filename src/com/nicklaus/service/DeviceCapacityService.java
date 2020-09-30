package com.nicklaus.service;

import com.nicklaus.domain.CapacityView;
import com.nicklaus.domain.DeviceCapacity;

import java.util.List;

public interface DeviceCapacityService {
    String add(CapacityView capacityView);

    List<CapacityView> findByDeviceId(String deviceId);

    void remove(CapacityView capacityView);
}
