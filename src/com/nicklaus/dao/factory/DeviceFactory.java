package com.nicklaus.dao.factory;

import com.nicklaus.dao.DeviceDao;
import com.nicklaus.dao.DeviceTypeDao;
import com.nicklaus.dao.impl.DeviceDaoImpl;
import com.nicklaus.dao.impl.DeviceTypeDaoImpl;

public class DeviceFactory {

    public static DeviceDao getInstance(){
        return DeviceDaoImpl.getInstance();
    }

}
