package com.nicklaus.view.validator;

import com.nicklaus.domain.User;

import java.util.regex.Pattern;

public class UserValidator {


    public static String validate(User user){
        String message;

        //先验证必填项是否有空值
        message = checkNotEmpty(user);

        if(message == null){
            //必填项不为空，验证输入是否符合规则
            message = "";
            //验证用户名
            if (!checkUsername(user.getUsername())){
                message += "登录名必须在3-20位合法字符。\n";
            }

            //验证密码
            if (!checkPassword(user.getPassword())){
                message += "密码必须在3-12位的数字。\n";
            }

            //如果输入了联系方式
            if (!"".equals(user.getPhone())){
                //验证联系方式
                if (!checkPhone(user.getPhone())){
                    message += "联系方式为11位的数字。";
                }
            }
        }

        //验证成功
        if ("".equals(message)){
            message = null;
        }

        return message;
    }

    /**
     * 验证手机号
     * @param phone
     * @return
     */
    private static boolean checkPhone(String phone) {
        //定义正则
        String pattern = "\\d{11}";

        return Pattern.matches(pattern,phone);
    }

    /**
     * 验证密码
     * @param password
     * @return
     */
    private static boolean checkPassword(String password) {
        //定义正则
        String pattern = "[0-9]{3,12}";

        return Pattern.matches(pattern,password);
    }

    /**
     * 验证用户名
     * @param username
     * @return
     */
    private static boolean checkUsername(String username) {
        //定义正则
        String pattern = "[a-zA-Z0-9_]{3,20}";

        return Pattern.matches(pattern,username);
    }

    /**
     * 检查必填项是否为空
     * @param user
     * @return
     */
    private static String checkNotEmpty(User user) {

        if ("".equals(user.getUsername()) || "".equals(user.getPassword()) || user.getPower() == null){
            return "登录名、密码、用户类型不能为空。";
        }else{
            return null;
        }
    }



}
