package com.nicklaus.service;

import com.nicklaus.domain.User;

import java.util.List;

public interface UserService {
    String register(User registerUser);

    User login(String username, String password);

    List<User> findAllUsers();

    List<User> findByName(String name);

    void removeUser(User user);

    void modifyUser(User modifyUser);

    List<User> findAllUserByPower(String power);

    List<User> findByPowerAndName(String power, String name);

    void toggleStatus(User user);
}
