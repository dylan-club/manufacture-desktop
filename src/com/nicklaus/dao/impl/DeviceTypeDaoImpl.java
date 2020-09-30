package com.nicklaus.dao.impl;

import com.nicklaus.dao.DeviceTypeDao;
import com.nicklaus.domain.DeviceType;
import com.nicklaus.domain.ProductType;
import com.nicklaus.util.FileUtils;
import com.nicklaus.util.NavigationUtils;

import java.util.ArrayList;
import java.util.List;

public class DeviceTypeDaoImpl implements DeviceTypeDao {

    //单例模式
    private static final DeviceTypeDao deviceTypeDao = new DeviceTypeDaoImpl();

    private DeviceTypeDaoImpl(){}

    public static DeviceTypeDao getInstance(){
        return deviceTypeDao;
    }

    private FileUtils<DeviceType> deviceTypeFileUtils = new FileUtils<>();

    @Override
    public DeviceType findByName(String deviceTypeName) {
        DeviceType type = null;
        List<DeviceType> typeList = getDeviceTypeList();
        if (typeList != null){
            for (DeviceType deviceType: typeList) {
                if (deviceType.getName().equals(deviceTypeName)){
                    type = deviceType;
                }
            }
        }

        return type;
    }

    @Override
    public int findMaxId() {
        //找到id最大值
        List<DeviceType> typeList = getDeviceTypeList();

        if (typeList == null){
            return 0;
        }else{
            int maxId = 0;
            for (DeviceType deviceType:
                    typeList) {
                if (deviceType.getId() > maxId){
                    maxId = deviceType.getId();
                }
            }

            return maxId;
        }
    }

    @Override
    public boolean save(DeviceType type) {
        List<DeviceType> typeList = getDeviceTypeList();

        if (typeList == null){
            typeList = new ArrayList<>();
        }

        typeList.add(type);
        return deviceTypeFileUtils.writeJsonList(NavigationUtils.deviceTypeFile,typeList);
    }

    @Override
    public List<DeviceType> findAll() {
        List<DeviceType> typeList = getDeviceTypeList();

        if (typeList == null){
            typeList = new ArrayList<>();
        }

        return typeList;
    }

    @Override
    public void remove(DeviceType removeType) {
        List<DeviceType> deviceTypeList = getDeviceTypeList();

        for (DeviceType deviceType:
                deviceTypeList) {
            if (deviceType.getId() == removeType.getId()){
                deviceTypeList.remove(deviceType);
                break;
            }
        }

        //更新文件信息
        deviceTypeFileUtils.writeJsonList(NavigationUtils.deviceTypeFile,deviceTypeList);
    }

    @Override
    public DeviceType findById(int id) {
        List<DeviceType> typeList = getDeviceTypeList();
        DeviceType oldType = null;
        for (DeviceType deviceType:
                typeList) {
            if (deviceType.getId() == id){
                oldType = deviceType;
                break;
            }
        }

        return oldType;
    }

    @Override
    public void modify(DeviceType type) {
        List<DeviceType> typeList = getDeviceTypeList();

        for (DeviceType deviceType:
                typeList) {
            if (deviceType.getId() == type.getId()){
                deviceType.setName(type.getName());
                break;
            }
        }

        //将修改写入文件
        deviceTypeFileUtils.writeJsonList(NavigationUtils.deviceTypeFile,typeList);
    }

    private List<DeviceType> getDeviceTypeList(){
        return deviceTypeFileUtils.readJsonFileAsList(NavigationUtils.deviceTypeFile, DeviceType.class);
    }
}
