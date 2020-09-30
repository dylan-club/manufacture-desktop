package com.nicklaus.dao.impl;

import com.nicklaus.dao.UserDao;
import com.nicklaus.domain.User;
import com.nicklaus.util.FileUtils;
import com.nicklaus.util.NavigationUtils;

import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    //单例模式
    private static final UserDao UserDao = new UserDaoImpl();

    private UserDaoImpl(){}

    public static UserDao getInstance(){
        return UserDao;
    }

    //文件读写
    private final FileUtils<User> userFileUtils = new FileUtils<User>();

    @Override
    public boolean find(User registerUser) {
        if (getUserList() != null){
            for (User user:getUserList()) {
                //用户名一致，返回true
                if (user.getUsername().equals(registerUser.getUsername())){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int findMaxId() {
        //用用户的数量代表唯一标识
        if (getUserList()!=null){
            int maxId = 0;
            for (User user: getUserList()) {
                if (user.getId() > maxId){
                    maxId = user.getId();
                }
            }

            return maxId;
        }else{
            return 0;
        }
    }

    @Override
    public boolean save(User registerUser) {
        List<User> userList = getUserList();
        if (userList==null){
            userList = new ArrayList<User>();
        }
        //讲用户添加到list中并写入文件
        userList.add(registerUser);
        return userFileUtils.writeJsonList(NavigationUtils.userFile,userList);
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User loginUser = null;
        List<User> users = getUserList();

        if (users!=null){
            for (User user: users) {
                //查找用户名和密码是否匹配
                if (user.getUsername().equals(username) && user.getPassword().equals(password)){
                    loginUser = user;
                    break;
                }
            }
        }
        return loginUser;
    }

    @Override
    public List<User> findAll() {
        return getUserList();
    }

    @Override
    public List<User> findByName(String name) {

        List<User> users = new ArrayList<User>();

        getUserList().forEach(user -> {
            if (name.equals(user.getName())){
                users.add(user);
            }
        });
        return users;
    }

    @Override
    public void removeById(int id) {
        List<User> userList = getUserList();
        for (User user: userList) {
            if (user.getId() == id){
                userList.remove(user);
                break;
            }
        }

        //重新回写数据
        userFileUtils.writeJsonList(NavigationUtils.userFile,userList);
    }

    @Override
    public void modify(User modifyUser) {
        List<User> userList = getUserList();

        //修改用户信息
        userList.forEach(user -> {
            if (user.getId() == modifyUser.getId()){
                user.setPassword(modifyUser.getPassword());
                user.setName(modifyUser.getName());
                user.setPhone(modifyUser.getPhone());
                user.setFactoryName(modifyUser.getFactoryName());
                user.setDetail(modifyUser.getDetail());
            }
        });

        //将数据回写进文件
        userFileUtils.writeJsonList(NavigationUtils.userFile,userList);
    }

    @Override
    public List<User> findAllByPower(String power) {
        List<User> factories = new ArrayList<User>();
        List<User> users = getUserList();

        if (users != null){
            //查找所有工厂信息
            for (User user: users) {
                if (user.getPower().equals(power)){
                    factories.add(user);
                }
            }
        }

        return factories;
    }

    @Override
    public List<User> findByPowerAndName(String power, String name) {
        List<User> factories = new ArrayList<User>();

        getUserList().forEach(user -> {
            if (user.getPower().equals(power) && user.getFactoryName().equals(name)){
                factories.add(user);
            }
        });

        return factories;
    }

    @Override
    public void toggleStatusById(int id) {
        List<User> userList = getUserList();

        userList.forEach(user -> {
            if (user.getId() == id){
                if (user.getState().equals("正常")){
                    user.setState("关停");
                }else{
                    user.setState("正常");
                }
            }
        });

        //将修改写入文件
        userFileUtils.writeJsonList(NavigationUtils.userFile, userList);
    }

    @Override
    public boolean findFactoryName(String factoryName) {
        List<User> userList = getUserList();
        boolean flag = false;
        if (userList != null){
            for (User user: userList) {
                if (user.getFactoryName().equals(factoryName)){
                    flag = true;
                    break;
                }
            }
        }

        return flag;
    }

    /**
     * 读取用户列表
     */
    private List<User> getUserList(){
        return userFileUtils.readJsonFileAsList(NavigationUtils.userFile,User.class);
    }

}
