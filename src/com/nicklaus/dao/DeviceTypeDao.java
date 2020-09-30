package com.nicklaus.dao;

import com.nicklaus.domain.DeviceType;
import com.nicklaus.domain.ProductType;

import java.util.List;

public interface DeviceTypeDao {
    DeviceType findByName(String deviceTypeName);

    int findMaxId();

    boolean save(DeviceType type);

    List<DeviceType> findAll();

    void remove(DeviceType removeType);

    DeviceType findById(int id);

    void modify(DeviceType type);
}
