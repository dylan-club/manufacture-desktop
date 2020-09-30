package com.nicklaus.service.impl;

import com.nicklaus.dao.UserDao;
import com.nicklaus.dao.factory.UserFactory;
import com.nicklaus.domain.User;
import com.nicklaus.service.UserService;

import java.util.List;


public class UserServiceImpl implements UserService {

    private static final UserService userService = new UserServiceImpl();

    private UserServiceImpl(){}

    public static UserService getInstance(){
        return userService;
    }

    private UserDao userDao = UserFactory.getInstance();

    @Override
    public String register(User registerUser) {
        String message;
        //查询该用户名是否可用
        boolean flag = userDao.find(registerUser);


        if (flag){
            //用户名存在，提示用户换一个用户名
            message = "该用户名太火爆，请您更换一个！";
            return message;
        }else{

            //判断工厂名称是否可用
            if (registerUser.getPower().equals("云工厂")){
                boolean isExist = userDao.findFactoryName(registerUser.getFactoryName());

                //非法工厂名
                if ("产能中心".equals(registerUser.getFactoryName())){
                    isExist = true;
                }

                if (isExist){
                    message = "该工厂名称已被使用，请跟换一个！";

                    return message;
                }
            }
            //设置用户可用id
            registerUser.setId(userDao.findMaxId()+1);
            //保存用户信息
            boolean isSave = userDao.save(registerUser);

            if (isSave){
                message = "添加成功！";
            }else{
                message = "添加失败，请联系管理员！";
            }
        }

        return message;
    }

    @Override
    public User login(String username, String password) {
        return userDao.findByUsernameAndPassword(username,password);
    }

    @Override
    public List<User> findAllUsers() {
        return userDao.findAll();
    }

    @Override
    public List<User> findByName(String name) {
        return userDao.findByName(name);
    }

    @Override
    public void removeUser(User user) {
        userDao.removeById(user.getId());
    }

    @Override
    public void modifyUser(User modifyUser) {
        userDao.modify(modifyUser);
    }

    @Override
    public List<User> findAllUserByPower(String power) {
        return userDao.findAllByPower(power);
    }

    @Override
    public List<User> findByPowerAndName(String power, String name) {
        return userDao.findByPowerAndName(power,name);
    }

    @Override
    public void toggleStatus(User user) {
        userDao.toggleStatusById(user.getId());
    }
}
