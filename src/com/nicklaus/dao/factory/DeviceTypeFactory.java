package com.nicklaus.dao.factory;

import com.nicklaus.dao.DeviceTypeDao;
import com.nicklaus.dao.ProductTypeDao;
import com.nicklaus.dao.impl.DeviceTypeDaoImpl;
import com.nicklaus.dao.impl.ProductTypeDaoImpl;

public class DeviceTypeFactory {

    public static DeviceTypeDao getInstance(){
        return DeviceTypeDaoImpl.getInstance();
    }
}
