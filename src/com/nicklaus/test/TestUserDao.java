package com.nicklaus.test;

import com.nicklaus.dao.UserDao;
import com.nicklaus.dao.factory.UserFactory;
import org.junit.Test;

public class TestUserDao {

    UserDao userDao = UserFactory.getInstance();

}
