package com.nicklaus.dao.impl;

import com.nicklaus.dao.DeviceDao;
import com.nicklaus.dao.OrderDao;
import com.nicklaus.domain.Device;
import com.nicklaus.domain.Order;
import com.nicklaus.util.FileUtils;
import com.nicklaus.util.NavigationUtils;

import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {

    //单例模式
    private static final OrderDao orderDao = new OrderDaoImpl();

    private OrderDaoImpl(){}

    public static OrderDao getInstance(){
        return orderDao;
    }

    private FileUtils<Order> orderFileUtils = new FileUtils<>();

    @Override
    public int findMaxId() {
        //找到id最大值
        List<Order> orderList = getOrderList();

        if (orderList == null){
            return 0;
        }else {
            int maxId = 0;
            for (Order order :
                    orderList) {
                if (order.getId() > maxId) {
                    maxId = order.getId();
                }
            }
            return maxId;
        }
    }

    @Override
    public boolean save(Order order) {
        List<Order> orderList = getOrderList();

        if (orderList == null){
            orderList = new ArrayList<>();
        }

        orderList.add(order);
        return orderFileUtils.writeJsonList(NavigationUtils.orderFile,orderList);
    }

    @Override
    public List<Order> findBySalemanNickName(String username) {
        List<Order> agentOrders = new ArrayList<>();
        if (getOrderList() != null){
            getOrderList().forEach(order -> {
                if (order.getSalemanNickName().equals(username)){
                    agentOrders.add(order);
                }
            });
        }

        return agentOrders;
    }

    @Override
    public void removeById(int id) {
        List<Order> orderList = getOrderList();

        for (Order order:
             orderList) {
            if (order.getId() == id){
                orderList.remove(order);
                break;
            }
        }

        //将修改写入文件
        orderFileUtils.writeJsonList(NavigationUtils.orderFile,orderList);
    }

    @Override
    public void modifyById(Order order) {
        List<Order> orderList = getOrderList();
        if (orderList != null){
            //修改订单信息
            orderList.forEach(myOrder -> {
                if (myOrder.getId() == order.getId()){
                    myOrder.setProductName(order.getProductName());
                    myOrder.setAmount(order.getAmount());
                    myOrder.setEndDate(order.getEndDate());
                    myOrder.setStartDate(order.getStartDate());
                }
            });


            //将修改信息回写到文件
            orderFileUtils.writeJsonList(NavigationUtils.orderFile,orderList);
        }
    }

    @Override
    public void modifyOrderState(Order modifyOrder, String orderState) {
        List<Order> orders = getOrderList();

        orders.forEach(order -> {
            if (order.getOrderId().equals(modifyOrder.getOrderId())){
                order.setOrderState(orderState);
            }
        });

        //将修改写入文件
        orderFileUtils.writeJsonList(NavigationUtils.orderFile,orders);
    }

    @Override
    public List<Order> findByOrderState(String orderState) {
        List<Order> stateOrders = new ArrayList<>();

        if (getOrderList() != null){
            getOrderList().forEach(order -> {
                if (order.getOrderState().equals(orderState)){
                    stateOrders.add(order);
                }
            });
        }

        return stateOrders;
    }

    @Override
    public void modifyStateAndFactoryName(Order order, String factoryName) {
        List<Order> orders = getOrderList();

        orders.forEach(myOrder -> {
            if (myOrder.getId() == order.getId()){
                myOrder.setOrderState("已接单");
                myOrder.setFactoryName(factoryName);
            }
        });

        //将修改写入文件
        orderFileUtils.writeJsonList(NavigationUtils.orderFile,orders);
    }

    @Override
    public List<Order> findForFactory(String factoryName) {
        List<Order> factoryOrders = new ArrayList<>();

        if (getOrderList()!=null){
            getOrderList().forEach(order -> {
                if (order.getFactoryName()!=null && order.getFactoryName().equals(factoryName)){
                    factoryOrders.add(order);
                }
            });
        }

        return factoryOrders;
    }

    private List<Order> getOrderList(){
        return orderFileUtils.readJsonFileAsList(NavigationUtils.orderFile,Order.class);
    }
}
