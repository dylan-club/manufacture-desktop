package com.nicklaus.service.impl;

import com.nicklaus.dao.OrderCapacityDao;
import com.nicklaus.dao.OrderDao;
import com.nicklaus.dao.factory.OrderCapacityFactory;
import com.nicklaus.dao.factory.OrderFactory;
import com.nicklaus.domain.OrderCapacity;
import com.nicklaus.service.OrderCapacityService;
import com.nicklaus.service.OrderService;

import java.util.List;

public class OrderCapacityServiceImpl implements OrderCapacityService {

    private static final OrderCapacityService orderCapacityService = new OrderCapacityServiceImpl();

    private OrderCapacityServiceImpl(){}

    public static OrderCapacityService getInstance(){
        return orderCapacityService;
    }

    private OrderCapacityDao orderCapacityDao = OrderCapacityFactory.getInstance();

    @Override
    public String add(OrderCapacity orderCapacity) {
        String message;

        orderCapacity.setId(orderCapacityDao.findMaxId()+1);
        //保存排产信息
        boolean flag = orderCapacityDao.save(orderCapacity);

        if (flag){
            message = "添加成功！";
        }else{
            message = "添加失败，请联系管理员";
        }

        return message;
    }

    @Override
    public List<OrderCapacity> findByOrderId(String orderId) {
        return orderCapacityDao.findByOrderId(orderId);
    }

    @Override
    public void remove(OrderCapacity orderCapacity) {
        orderCapacityDao.remove(orderCapacity);
    }
}
