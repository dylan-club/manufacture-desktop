package com.nicklaus.service;

import com.nicklaus.domain.OrderCapacity;

import java.util.List;

public interface OrderCapacityService {
    String add(OrderCapacity orderCapacity);

    List<OrderCapacity> findByOrderId(String orderId);

    void remove(OrderCapacity orderCapacity);
}
