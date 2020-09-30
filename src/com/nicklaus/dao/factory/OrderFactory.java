package com.nicklaus.dao.factory;

import com.nicklaus.dao.OrderDao;
import com.nicklaus.dao.impl.OrderDaoImpl;

public class OrderFactory {

    public static OrderDao getInstance(){
        return OrderDaoImpl.getInstance();
    }
}
