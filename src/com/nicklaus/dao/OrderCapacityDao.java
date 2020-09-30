package com.nicklaus.dao;

import com.nicklaus.domain.OrderCapacity;

import java.util.List;

public interface OrderCapacityDao {
    int findMaxId();

    boolean save(OrderCapacity orderCapacity);

    List<OrderCapacity> findByOrderId(String orderId);

    void remove(OrderCapacity orderCapacity);
}
