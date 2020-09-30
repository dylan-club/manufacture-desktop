package com.nicklaus.dao.impl;

import com.nicklaus.dao.OrderCapacityDao;
import com.nicklaus.dao.OrderDao;
import com.nicklaus.domain.DeviceCapacity;
import com.nicklaus.domain.Order;
import com.nicklaus.domain.OrderCapacity;
import com.nicklaus.util.FileUtils;
import com.nicklaus.util.NavigationUtils;

import java.util.ArrayList;
import java.util.List;

public class OrderCapacityDaoImpl implements OrderCapacityDao {

    //单例模式
    private static final OrderCapacityDao orderCapacityDao = new OrderCapacityDaoImpl();

    private OrderCapacityDaoImpl(){}

    public static OrderCapacityDao getInstance(){
        return orderCapacityDao;
    }

    private FileUtils<OrderCapacity> orderCapacityFileUtils = new FileUtils<>();

    @Override
    public int findMaxId() {
        //找到id最大值
        List<OrderCapacity> capacities = getOrderCapacityList();

        if (capacities == null){
            return 0;
        }else {
            int maxId = 0;
            for (OrderCapacity orderCapacity :
                    capacities) {
                if (orderCapacity.getId() > maxId) {
                    maxId = orderCapacity.getId();
                }
            }
            return maxId;
        }
    }

    @Override
    public boolean save(OrderCapacity orderCapacity) {
        List<OrderCapacity> capacities = getOrderCapacityList();

        if (capacities == null){
            capacities = new ArrayList<>();
        }

        capacities.add(orderCapacity);
        return orderCapacityFileUtils.writeJsonList(NavigationUtils.orderCapacityFile,capacities);
    }

    @Override
    public List<OrderCapacity> findByOrderId(String orderId) {
        List<OrderCapacity> orderCapacities = new ArrayList<>();
        if (getOrderCapacityList() != null){
            getOrderCapacityList().forEach(orderCapacity -> {
                if (orderCapacity.getOrderId().equals(orderId)){
                    orderCapacities.add(orderCapacity);
                }
            });
        }
        return orderCapacities;
    }

    @Override
    public void remove(OrderCapacity orderCapacity) {
        List<OrderCapacity> capacityList = getOrderCapacityList();

        for (OrderCapacity myOrderCapacity:
                capacityList) {
            if (myOrderCapacity.getId() == orderCapacity.getId()){
                capacityList.remove(myOrderCapacity);
                break;
            }
        }

        //修改文件信息
        orderCapacityFileUtils.writeJsonList(NavigationUtils.orderCapacityFile,capacityList);
    }

    private List<OrderCapacity> getOrderCapacityList(){
        return orderCapacityFileUtils.readJsonFileAsList(NavigationUtils.orderCapacityFile,OrderCapacity.class);
    }
}
