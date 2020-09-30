package com.nicklaus.service.impl;

import com.nicklaus.dao.OrderDao;
import com.nicklaus.dao.factory.OrderFactory;
import com.nicklaus.domain.Device;
import com.nicklaus.domain.Order;
import com.nicklaus.service.DeviceService;
import com.nicklaus.service.OrderService;

import java.util.List;
import java.util.UUID;

public class OrderServiceImpl implements OrderService {

    private static final OrderService orderService = new OrderServiceImpl();

    private OrderServiceImpl(){}

    public static OrderService getInstance(){
        return orderService;
    }

    private OrderDao orderDao = OrderFactory.getInstance();

    @Override
    public String add(Order order) {
        String message;

        order.setId(orderDao.findMaxId()+1);
        //保存订单
        boolean flag = orderDao.save(order);

        if (flag){
            message = "添加成功！";
        }else{
            message = "添加失败，请联系管理员";
        }

        return message;
    }

    @Override
    public List<Order> findForAgent(String username) {
        return orderDao.findBySalemanNickName(username);
    }

    @Override
    public void remove(Order removeOrder) {
        orderDao.removeById(removeOrder.getId());
    }

    @Override
    public String modify(Order order) {

        orderDao.modifyById(order);

        return "修改成功";
    }

    @Override
    public void modifyOrderState(Order modifyOrder, String orderState) {
        orderDao.modifyOrderState(modifyOrder,orderState);
    }

    @Override
    public List<Order> findByOrderState(String orderState) {
        return orderDao.findByOrderState(orderState);
    }

    @Override
    public void modifyStateAndFactoryName(Order order, String factoryName) {
        orderDao.modifyStateAndFactoryName(order,factoryName);
    }

    @Override
    public List<Order> findForFactory(String factoryName) {
        return orderDao.findForFactory(factoryName);
    }
}
