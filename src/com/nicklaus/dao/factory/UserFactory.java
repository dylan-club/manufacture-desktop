package com.nicklaus.dao.factory;

import com.nicklaus.dao.UserDao;
import com.nicklaus.dao.impl.UserDaoImpl;

public class UserFactory {

    public static UserDao getInstance(){
        return UserDaoImpl.getInstance();
    }
}
