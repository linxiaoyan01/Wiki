package top.kaluna.wiki.util;

import top.kaluna.wiki.resp.UserLoginResp;

import java.io.Serializable;

/**
 * @author Yuery
 * @date 2022/1/15/0015 - 23:37
 */

public class LoginUserContext implements Serializable {

    private static ThreadLocal<UserLoginResp> user = new ThreadLocal<>();

    public static UserLoginResp getUser() {
        return user.get();
    }

    public static void setUser(UserLoginResp user) {
        LoginUserContext.user.set(user);
    }

}
