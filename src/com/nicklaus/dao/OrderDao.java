package com.nicklaus.dao;

import com.nicklaus.domain.Order;

import java.util.List;

public interface OrderDao {
    int findMaxId();

    boolean save(Order order);

    List<Order> findBySalemanNickName(String username);

    void removeById(int id);

    void modifyById(Order order);

    void modifyOrderState(Order modifyOrder, String orderState);

    List<Order> findByOrderState(String orderState);

    void modifyStateAndFactoryName(Order order, String factoryName);

    List<Order> findForFactory(String factoryName);
}
