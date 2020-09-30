package com.nicklaus.service;

import com.nicklaus.domain.Order;

import java.util.List;

public interface OrderService {
    String add(Order order);

    List<Order> findForAgent(String username);

    void remove(Order removeOrder);

    String modify(Order order);

    void modifyOrderState(Order modifyOrder, String orderState);

    List<Order> findByOrderState(String orderState);

    void modifyStateAndFactoryName(Order order, String factoryName);

    List<Order> findForFactory(String factoryName);
}
