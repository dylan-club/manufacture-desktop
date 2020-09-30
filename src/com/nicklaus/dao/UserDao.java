package com.nicklaus.dao;

import com.nicklaus.domain.User;

import java.util.List;

public interface UserDao {
    boolean find(User registerUser);

    int findMaxId();

    boolean save(User registerUser);

    User findByUsernameAndPassword(String username, String password);

    List<User> findAll();

    List<User> findByName(String name);

    void removeById(int id);

    void modify(User modifyUser);

    List<User> findAllByPower(String power);

    List<User> findByPowerAndName(String power, String name);

    void toggleStatusById(int id);

    boolean findFactoryName(String factoryName);
}
