package com.nicklaus.service;

import com.nicklaus.domain.DeviceType;
import com.nicklaus.domain.ProductType;

import java.util.List;

public interface DeviceTypeService {
    String add(String deviceTypeName);

    List<DeviceType> findAll();

    List<DeviceType> findByName(String name);

    String remove(DeviceType removeType);

    String modify(DeviceType type);
}
