package com.github.mengweijin.quickboot.auth.security;

/**
 * @author mengweijin
 * @date 2022/1/16
 */
public interface SecurityConst {

    String JWT_KEY_LOGIN_USER_ID = "login_user_jwt_key";

    /**
     * 用户登录失败次数 redis key
     * login_failed_times:admin
     */
    String REDIS_KEY_LOGIN_FAILED_TIMES = "login_failed_times:";

    /**
     * 登录用户 token redis key 前缀
     * login_username_token:100001
     */
    String REDIS_KEY_LOGIN_USERNAME_TOKEN = "login_username_token:";

}
