package com.nicklaus.service.impl;

import com.nicklaus.dao.DeviceCapacityDao;
import com.nicklaus.dao.DeviceDao;
import com.nicklaus.dao.ProductDao;
import com.nicklaus.dao.factory.DeviceCapacityFactory;
import com.nicklaus.dao.factory.DeviceFactory;
import com.nicklaus.dao.factory.ProductFactory;
import com.nicklaus.dao.impl.DeviceDaoImpl;
import com.nicklaus.domain.CapacityView;
import com.nicklaus.domain.DeviceCapacity;
import com.nicklaus.domain.DeviceType;
import com.nicklaus.service.DeviceCapacityService;

import java.util.ArrayList;
import java.util.List;

public class DeviceCapacityServiceImpl implements DeviceCapacityService {
    private static final DeviceCapacityService deviceCapacityService = new DeviceCapacityServiceImpl();

    private DeviceCapacityServiceImpl(){}

    public static DeviceCapacityService getInstance(){
        return deviceCapacityService;
    }

    private DeviceCapacityDao deviceCapacityDao = DeviceCapacityFactory.getInstance();

    private DeviceDao deviceDao = DeviceFactory.getInstance();

    private ProductDao productDao = ProductFactory.getInstance();

    @Override
    public String add(CapacityView capacityView) {
        String message;

        String deviceId = deviceDao.findByName(capacityView.getDeviceName()).getDeviceId();
        String productId = productDao.findByName(capacityView.getProductName()).getProductId();

        DeviceCapacity deviceCapacity = deviceCapacityDao.findByDeviceIdAndProductId(deviceId,productId);

        //判断当前产品是否已经配置
        if (deviceCapacity == null){
            //没有配置，进行配置
            DeviceCapacity capacity = new DeviceCapacity();
            capacity.setId(deviceCapacityDao.findMaxId()+1);
            capacity.setDeviceId(deviceDao.findByName(capacityView.getDeviceName()).getDeviceId());
            capacity.setProductId(productDao.findByName(capacityView.getProductName()).getProductId());
            capacity.setAmount(capacityView.getAmount());
            boolean flag = deviceCapacityDao.save(capacity);

            if (flag){
                message = "添加成功！";
            }else{
                message = "添加失败，请联系管理员";
            }
        }else{
            message = "该产品已被配置，请勿重复添加！";
        }

        return message;
    }

    @Override
    public List<CapacityView> findByDeviceId(String deviceId) {
        //从文件获取PO对象列表
        List<DeviceCapacity> deviceCapacities =  deviceCapacityDao.findByDeviceId(deviceId);
        //新建一个链表存储VO对象列表
        List<CapacityView> capacityViews = new ArrayList<>();

        if (deviceCapacities != null){
            for (DeviceCapacity deviceCapacity :
                 deviceCapacities) {
                //PO和VO对象转换
                CapacityView view = new CapacityView();
                view.setId(deviceCapacity.getId());
                view.setDeviceName(deviceDao.findByDeviceId(deviceCapacity.getDeviceId()));
                view.setProductName(productDao.findByProductId(deviceCapacity.getProductId()));
                view.setAmount(deviceCapacity.getAmount());
                if (view.getDeviceName() != null && view.getProductName() != null){
                    capacityViews.add(view);
                }
            }
        }

        return capacityViews;
    }

    @Override
    public void remove(CapacityView capacityView) {
        deviceCapacityDao.removeById(capacityView.getId());
    }
}
