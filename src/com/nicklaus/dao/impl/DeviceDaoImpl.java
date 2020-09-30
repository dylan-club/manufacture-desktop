package com.nicklaus.dao.impl;

import com.nicklaus.dao.DeviceDao;
import com.nicklaus.dao.DeviceTypeDao;
import com.nicklaus.domain.Device;
import com.nicklaus.domain.DeviceCapacity;
import com.nicklaus.domain.DeviceType;
import com.nicklaus.domain.Product;
import com.nicklaus.util.FileUtils;
import com.nicklaus.util.NavigationUtils;

import java.util.ArrayList;
import java.util.List;

public class DeviceDaoImpl implements DeviceDao {

    //单例模式
    private static final DeviceDao deviceDao = new DeviceDaoImpl();

    private DeviceDaoImpl(){}

    public static DeviceDao getInstance(){
        return deviceDao;
    }

    private FileUtils<Device> deviceFileUtils = new FileUtils<>();

    @Override
    public Device findByName(String name) {
        Device device = null;
        List<Device> deviceList = getDeviceList();
        if (deviceList != null){
            for (Device myDevice: deviceList) {
                if (myDevice.getName().equals(name)){
                    device = myDevice;
                }
            }
        }

        return device;
    }

    private List<Device> getDeviceList() {
        return deviceFileUtils.readJsonFileAsList(NavigationUtils.deviceFile,Device.class);
    }

    @Override
    public int findMaxId() {
        //找到id最大值
        List<Device> deviceList = getDeviceList();

        if (deviceList == null){
            return 0;
        }else {
            int maxId = 0;
            for (Device device :
                    deviceList) {
                if (device.getId() > maxId) {
                    maxId = device.getId();
                }
            }

            return maxId;

        }
    }

    @Override
    public boolean save(Device device) {
        List<Device> deviceList = getDeviceList();

        if (deviceList == null){
            deviceList = new ArrayList<>();
        }

        deviceList.add(device);
        return deviceFileUtils.writeJsonList(NavigationUtils.deviceFile,deviceList);
    }

    @Override
    public List<Device> findAll() {
        List<Device> deviceList = getDeviceList();

        if (deviceList == null){
            deviceList = new ArrayList<>();
        }

        return deviceList;
    }

    @Override
    public List<Device> findByDeviceType(String name) {
        List<Device> deviceList = getDeviceList();
        List<Device> devices = new ArrayList<>();

        if (deviceList != null){
            deviceList.forEach(device -> {
                if (device.getDeviceTypeName().equals(name)){
                    devices.add(device);
                }
            });
        }

        return devices;
    }

    @Override
    public void modifyByDeviceType(String oldTypeName, String newTypeName) {
        List<Device> deviceList = getDeviceList();
        if (deviceList != null){
            //修改设备信息列表
            deviceList.forEach(device -> {
                if (device.getDeviceTypeName().equals(oldTypeName)){
                    device.setDeviceTypeName(newTypeName);
                }
            });


            //将修改信息回写到文件
            deviceFileUtils.writeJsonList(NavigationUtils.deviceFile,deviceList);
        }
    }

    @Override
    public void remove(Device removeDevice) {
        List<Device> deviceList = getDeviceList();

        for (Device device:
                deviceList) {
            if (device.getId() == removeDevice.getId()){
                deviceList.remove(device);
                break;
            }
        }

        //更新文件信息
        deviceFileUtils.writeJsonList(NavigationUtils.deviceFile,deviceList);
    }

    @Override
    public void modify(Device device) {
        List<Device> deviceList = getDeviceList();

        for (Device myDevice:
                deviceList) {
            if (myDevice.getId() == device.getId()){
                myDevice.setName(device.getName());
                myDevice.setDeviceTypeName(device.getDeviceTypeName());
                myDevice.setDeviceSize(device.getDeviceSize());
                myDevice.setDeviceDetail(device.getDeviceDetail());
                break;
            }
        }

        //将修改写入文件
        deviceFileUtils.writeJsonList(NavigationUtils.deviceFile,deviceList);
    }

    @Override
    public void modifyDeviceState(Device switchDevice) {
        List<Device> deviceList = getDeviceList();

        deviceList.forEach(device -> {
            if (device.getId() == switchDevice.getId()){
                //如果是闲置中就改为已关闭，否则反之
                if (switchDevice.getDeviceState().equals("闲置中")){
                    device.setDeviceState("已关闭");
                }else if (switchDevice.getDeviceState().equals("已关闭")){
                    device.setDeviceState("闲置中");
                }
            }
        });

        deviceFileUtils.writeJsonList(NavigationUtils.deviceFile,deviceList);
    }

    @Override
    public List<Device> findByFactoryName(String factoryName) {

        List<Device> factoryDevices = new ArrayList<>();

        if(getDeviceList() != null){
            getDeviceList().forEach(device -> {
                if (factoryName.equals(device.getFactoryName())){
                    factoryDevices.add(device);
                }
            });
        }

        return factoryDevices;
    }

    @Override
    public List<Device> findByNameAndFactoryName(String name, String factoryName) {
        List<Device> factoryDevices = new ArrayList<>();

        if (getDeviceList() != null){
            getDeviceList().forEach(device -> {
                if (device.getName().equals(name) && device.getFactoryName().equals(factoryName)){
                    factoryDevices.add(device);
                }
            });
        }

        return factoryDevices;
    }

    @Override
    public void modifyToAdmin(Device removeDevice) {
        List<Device> devices = getDeviceList();

        devices.forEach(device -> {
            if (device.getId() == removeDevice.getId()){
                //归还设备
                device.setFactoryName("产能中心");
                device.setDeviceRentalState("未被租用");
            }
        });

        //写入文件
        deviceFileUtils.writeJsonList(NavigationUtils.deviceFile,devices);
    }

    @Override
    public List<Device> findByRentalStateAndDeviceState(String rentalState, String deviceState) {
        List<Device> availableDevices = new ArrayList<>();

        if (getDeviceList() != null){
            getDeviceList().forEach(device -> {
                if (device.getDeviceRentalState().equals(rentalState) && device.getDeviceState().equals(deviceState)){
                    availableDevices.add(device);
                }
            });
        }

        return availableDevices;
    }

    @Override
    public void modifyRentalStateAndFactoryName(Device device, String factoryName) {
        List<Device> devices = getDeviceList();

        devices.forEach(myDevice -> {
            if (myDevice.getId() == device.getId()){
                //租用该设备
                myDevice.setDeviceRentalState("已被租用");
                myDevice.setFactoryName(factoryName);
            }
        });

        //将修改写入文件
        deviceFileUtils.writeJsonList(NavigationUtils.deviceFile, devices);
    }

    @Override
    public String findByDeviceId(String deviceId) {
        String deviceName =null;

        if (getDeviceList() != null){
            for (Device device:
                 getDeviceList()) {
                if (device.getDeviceId().equals(deviceId)){
                    deviceName = device.getName();
                    break;
                }
            }
        }

        return deviceName;
    }

    @Override
    public Device findForDeviceId(String deviceId) {
        Device device = null;

        for (Device myDevice:
             getDeviceList()) {
            if (myDevice.getDeviceId().equals(deviceId)){
                device = myDevice;
                break;
            }
        }

        return device;
    }

    @Override
    public void modifyDeviceStateByDeviceIdAndState(String deviceId, String deviceState) {
        List<Device> deviceList = getDeviceList();

        if (deviceList != null){
            deviceList.forEach(device -> {
                if (device.getDeviceId().equals(deviceId)){
                    device.setDeviceState(deviceState);
                }
            });
        }

        //将修改写入文件
        deviceFileUtils.writeJsonList(NavigationUtils.deviceFile,deviceList);
    }


}
