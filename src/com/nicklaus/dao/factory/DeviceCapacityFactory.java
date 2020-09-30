package com.nicklaus.dao.factory;

import com.nicklaus.dao.DeviceCapacityDao;
import com.nicklaus.dao.DeviceDao;
import com.nicklaus.dao.impl.DeviceCapacityDaoImpl;
import com.nicklaus.dao.impl.DeviceDaoImpl;

public class DeviceCapacityFactory {

    public static DeviceCapacityDao getInstance(){
        return DeviceCapacityDaoImpl.getInstance();
    }
}
