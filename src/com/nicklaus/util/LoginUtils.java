package com.nicklaus.util;

import com.nicklaus.domain.User;

public class LoginUtils {

    private static User user;


    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        LoginUtils.user = user;
    }
}
