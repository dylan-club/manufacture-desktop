package com.nicklaus.dao.factory;

import com.nicklaus.dao.ProductTypeDao;
import com.nicklaus.dao.impl.ProductTypeDaoImpl;

public class ProductTypeFactory {

    public static ProductTypeDao getInstance(){
        return ProductTypeDaoImpl.getInstance();
    }
}
