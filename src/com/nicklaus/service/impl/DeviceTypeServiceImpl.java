package com.nicklaus.service.impl;

import com.nicklaus.dao.DeviceDao;
import com.nicklaus.dao.DeviceTypeDao;
import com.nicklaus.dao.impl.DeviceDaoImpl;
import com.nicklaus.dao.impl.DeviceTypeDaoImpl;
import com.nicklaus.domain.DeviceType;
import com.nicklaus.domain.ProductType;
import com.nicklaus.service.DeviceTypeService;

import java.util.ArrayList;
import java.util.List;

public class DeviceTypeServiceImpl implements DeviceTypeService {

    private static final DeviceTypeService deviceTypeService = new DeviceTypeServiceImpl();

    private DeviceTypeServiceImpl(){}

    public static DeviceTypeService getInstance(){
        return deviceTypeService;
    }

    private DeviceTypeDao deviceTypeDao = DeviceTypeDaoImpl.getInstance();

    private DeviceDao deviceDao = DeviceDaoImpl.getInstance();

    @Override
    public String add(String deviceTypeName) {
        String message;

        DeviceType deviceType = deviceTypeDao.findByName(deviceTypeName);

        //判断当前产品类别是否可用
        if (deviceType == null){
            //名称可用，进行保存
            DeviceType type = new DeviceType();
            type.setId(deviceTypeDao.findMaxId()+1);
            type.setName(deviceTypeName);
            boolean flag = deviceTypeDao.save(type);

            if (flag){
                message = "添加成功！";
            }else{
                message = "添加失败，请联系管理员";
            }
        }else{
            message = "该设备类别已存在，请跟换一个！";
        }

        return message;
    }

    @Override
    public List<DeviceType> findAll() {
        return deviceTypeDao.findAll();
    }

    @Override
    public List<DeviceType> findByName(String name) {
        List<DeviceType> types = new ArrayList<>();
        DeviceType type = deviceTypeDao.findByName(name);

        if (type != null){
            types.add(type);
        }

        return types;
    }

    @Override
    public String remove(DeviceType removeType) {
        String message;

        if (deviceDao.findByDeviceType(removeType.getName()).size() != 0){
            message = "该类别下已经有设备，删除失败！";
        }else{
            deviceTypeDao.remove(removeType);
            message = "删除成功！";
        }
        return message;
    }

    @Override
    public String modify(DeviceType type) {
        String message;
        //查找该名称是否可用
        if (deviceTypeDao.findByName(type.getName()) == null){

            DeviceType oldType = deviceTypeDao.findById(type.getId());
            deviceTypeDao.modify(type);
            //修改类别的同时，将属于该类别的设备一同修改
            deviceDao.modifyByDeviceType(oldType.getName(), type.getName());
            message = "修改成功！";
        }else{
            message = "该设备类别已存在，请更换一个！";
        }
        return message;
    }
}
