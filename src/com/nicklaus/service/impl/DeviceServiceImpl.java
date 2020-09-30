package com.nicklaus.service.impl;

import com.nicklaus.dao.DeviceCapacityDao;
import com.nicklaus.dao.DeviceDao;
import com.nicklaus.dao.DeviceTypeDao;
import com.nicklaus.dao.ProductDao;
import com.nicklaus.dao.factory.DeviceCapacityFactory;
import com.nicklaus.dao.factory.DeviceFactory;
import com.nicklaus.dao.factory.DeviceTypeFactory;
import com.nicklaus.dao.factory.ProductFactory;
import com.nicklaus.domain.*;
import com.nicklaus.service.DeviceCapacityService;
import com.nicklaus.service.DeviceService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DeviceServiceImpl implements DeviceService {

    private static final DeviceService deviceService = new DeviceServiceImpl();

    private DeviceServiceImpl(){}

    public static DeviceService getInstance(){
        return deviceService;
    }

    private DeviceTypeDao deviceTypeDao = DeviceTypeFactory.getInstance();

    private DeviceDao deviceDao = DeviceFactory.getInstance();

    private DeviceCapacityDao deviceCapacityDao = DeviceCapacityFactory.getInstance();

    private ProductDao productDao = ProductFactory.getInstance();

    @Override
    public String add(Device device, User loginUser) {
        String message;

        Device findDevice = deviceDao.findByName(device.getName());

        //判断当前工厂名称是否可用
        if (findDevice == null){
            //名称可用，进行保存
            device.setId(deviceDao.findMaxId()+1);
            device.setDeviceId(UUID.randomUUID().toString());
            device.setDeviceState("已关闭");
            device.setDeviceRoot("租用设备");

            //判断是产能中心的设备还是云工厂的设备
            if (loginUser.getPower().equals("超级管理员")){
                device.setDeviceRentalState("未被租用");
                device.setFactoryName("产能中心");
            }else{
                device.setDeviceRentalState("工厂设备");
                device.setFactoryName(loginUser.getFactoryName());
                device.setDeviceRoot("自有设备");
            }
            boolean flag = deviceDao.save(device);

            if (flag){
                message = "添加成功！";
            }else{
                message = "添加失败，请联系管理员";
            }
        }else{
            message = "该设备名称已存在，请跟换一个！";
        }

        return message;
    }

    @Override
    public List<String> findAllTypes() {
        List<String> types = new ArrayList<>();

        List<DeviceType> typeList = deviceTypeDao.findAll();

        if (typeList != null){
            typeList.forEach(deviceType -> {
                types.add(deviceType.getName());
            });
        }

        return types;
    }

    @Override
    public List<Device> findAll() {
        return deviceDao.findAll();
    }

    @Override
    public List<Device> findByName(String name) {
        List<Device> devices = new ArrayList<>();
        Device device = deviceDao.findByName(name);

        if (device != null){
            devices.add(device);
        }

        return devices;
    }

    @Override
    public void remove(Device removeDevice) {
        deviceDao.remove(removeDevice);
    }

    @Override
    public String modify(Device device) {
        String message;
        //查找该设备名称是否可用
        if (deviceDao.findByName(device.getName()) == null){

            deviceDao.modify(device);

            message = "修改成功！";
        }else{
            message = "该设备名称已存在，请更换一个！";
        }
        return message;
    }

    @Override
    public void modifyDeviceState(Device switchDevice) {
        deviceDao.modifyDeviceState(switchDevice);
    }

    @Override
    public List<Device> findByFactoryName(String factoryName) {
        return deviceDao.findByFactoryName(factoryName);
    }

    @Override
    public List<Device> findForFactory(String name, String factoryName) {
        return deviceDao.findByNameAndFactoryName(name,factoryName);
    }

    @Override
    public void withDrawDevice(Device removeDevice) {
        deviceDao.modifyToAdmin(removeDevice);
    }

    @Override
    public List<Device> findAvailableDevices() {
        return deviceDao.findByRentalStateAndDeviceState("未被租用","闲置中");
    }

    @Override
    public void rentalDevice(Device device, String factoryName) {
        deviceDao.modifyRentalStateAndFactoryName(device, factoryName);
    }

    @Override
    public List<String> findAvailableDevicesByProductName(String productName, String factoryName) {

        Product product = productDao.findByName(productName);

        //先找出能生产该产品的所有设备
        List<DeviceCapacity> deviceCapacities = deviceCapacityDao.findByProductId(product.getProductId());

        List<String> availableDevice = new ArrayList<>();
        //再找出该工厂的设备
        for (DeviceCapacity capacity:deviceCapacities) {
            Device device = deviceDao.findForDeviceId(capacity.getDeviceId());

            if (device.getFactoryName().equals(factoryName) && device.getDeviceState().equals("闲置中")){
                availableDevice.add(device.getName());
            }
        }

        return availableDevice;
    }

    @Override
    public void modifyDeviceStateByDeviceIdAndState(String deviceId, String deviceState) {
        deviceDao.modifyDeviceStateByDeviceIdAndState(deviceId,deviceState);
    }
}
