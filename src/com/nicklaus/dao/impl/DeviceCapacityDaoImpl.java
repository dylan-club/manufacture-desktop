package com.nicklaus.dao.impl;

import com.nicklaus.dao.DeviceCapacityDao;
import com.nicklaus.dao.DeviceDao;
import com.nicklaus.domain.Device;
import com.nicklaus.domain.DeviceCapacity;
import com.nicklaus.util.FileUtils;
import com.nicklaus.util.NavigationUtils;

import java.util.ArrayList;
import java.util.List;

public class DeviceCapacityDaoImpl implements DeviceCapacityDao {

    //单例模式
    private static final DeviceCapacityDao deviceCapacityDao = new DeviceCapacityDaoImpl();

    private DeviceCapacityDaoImpl(){}

    public static DeviceCapacityDao getInstance(){
        return deviceCapacityDao;
    }

    private FileUtils<DeviceCapacity> deviceCapacityFileUtils = new FileUtils<>();

    @Override
    public DeviceCapacity findByDeviceIdAndProductId(String deviceId, String productId) {
        DeviceCapacity deviceCapacity = null;
        List<DeviceCapacity> capacityList = getDeviceCapacityList();
        if (capacityList != null){
            for (DeviceCapacity capacity: capacityList) {
                if (capacity.getDeviceId().equals(deviceId) && capacity.getProductId().equals(productId)){
                    deviceCapacity = capacity;
                    break;
                }
            }
        }

        return deviceCapacity;
    }

    @Override
    public int findMaxId() {
        //找到id最大值
        List<DeviceCapacity> capacityList = getDeviceCapacityList();

        if (capacityList == null){
            return 0;
        }else {
            int maxId = 0;
            for (DeviceCapacity capacity :
                    capacityList) {
                if (capacity.getId() > maxId) {
                    maxId = capacity.getId();
                }
            }

            return maxId;

        }
    }

    @Override
    public boolean save(DeviceCapacity capacity) {
        List<DeviceCapacity> capacityList = getDeviceCapacityList();

        if (capacityList == null){
            capacityList = new ArrayList<>();
        }

        //写入文件
        capacityList.add(capacity);
        return deviceCapacityFileUtils.writeJsonList(NavigationUtils.deviceCapacityFile,capacityList);
    }

    @Override
    public List<DeviceCapacity> findByDeviceId(String deviceId) {

        List<DeviceCapacity> deviceCapacities = new ArrayList<>();

        if (getDeviceCapacityList() != null){
            getDeviceCapacityList().forEach(deviceCapacity -> {
                if (deviceCapacity.getDeviceId().equals(deviceId)){
                    deviceCapacities.add(deviceCapacity);
                }
            });
        }

        return deviceCapacities;
    }

    @Override
    public void removeById(int id) {
        List<DeviceCapacity> capacityList = getDeviceCapacityList();

        for (DeviceCapacity deviceCapacity:
             capacityList) {
            if (deviceCapacity.getId() == id){
                capacityList.remove(deviceCapacity);
                break;
            }
        }

        //修改文件信息
        deviceCapacityFileUtils.writeJsonList(NavigationUtils.deviceCapacityFile,capacityList);
    }

    @Override
    public List<DeviceCapacity> findByProductId(String productId) {
        List<DeviceCapacity> deviceCapacities = new ArrayList<>();

        if (getDeviceCapacityList() != null){
            getDeviceCapacityList().forEach(deviceCapacity -> {
                if (deviceCapacity.getProductId().equals(productId)){
                    deviceCapacities.add(deviceCapacity);
                }
            });
        }

        return deviceCapacities;
    }

    private List<DeviceCapacity> getDeviceCapacityList(){
        return deviceCapacityFileUtils.readJsonFileAsList(NavigationUtils.deviceCapacityFile,DeviceCapacity.class);
    }
}
