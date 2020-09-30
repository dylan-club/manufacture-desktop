package com.nicklaus.dao.factory;

import com.nicklaus.dao.ProductDao;
import com.nicklaus.dao.ProductTypeDao;
import com.nicklaus.dao.impl.ProductDaoImpl;
import com.nicklaus.dao.impl.ProductTypeDaoImpl;

public class ProductFactory {

    public static ProductDao getInstance(){
        return ProductDaoImpl.getInstance();
    }
}
