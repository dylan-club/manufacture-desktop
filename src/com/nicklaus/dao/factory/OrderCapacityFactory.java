package com.nicklaus.dao.factory;

import com.nicklaus.dao.OrderCapacityDao;
import com.nicklaus.dao.OrderDao;
import com.nicklaus.dao.impl.OrderCapacityDaoImpl;
import com.nicklaus.dao.impl.OrderDaoImpl;

public class OrderCapacityFactory {

    public static OrderCapacityDao getInstance(){
        return OrderCapacityDaoImpl.getInstance();
    }
}
