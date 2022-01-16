package com.github.mengweijin.quickboot.auth.system.service;

import com.github.mengweijin.quickboot.auth.domain.LoginUser;
import com.github.mengweijin.quickboot.auth.properties.AuthProperties;
import com.github.mengweijin.quickboot.auth.security.SecurityConst;
import com.github.mengweijin.quickboot.auth.utils.TokenUtils;
import com.github.mengweijin.quickboot.framework.redis.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author mengweijin
 * @since 2022-01-07
 */
@Slf4j
@Service
public class TokenService {

    @Autowired
    private AuthProperties authProperties;

    @Autowired
    private RedisCache redisCache;

    /**
     * 获取请求携带的令牌
     * @param request request
     * @return Token
     */
    public String getToken(HttpServletRequest request) {
        return request.getHeader(authProperties.getToken().getHeader());
    }

    public LoginUser getLoginUser(String token) {
        // 从 token 中获取 userId
        String userId = TokenUtils.getUserIdFromToken(authProperties.getToken().getSecret(), token);
        // 根据 userId 从 redis 中查找
        return redisCache.getCacheObject(SecurityConst.REDIS_KEY_LOGIN_USERNAME_TOKEN + userId);
    }

    public void expireRefresh(String username) {
        final int expireTime = authProperties.getToken().getExpire();
        // 刷新 token 过期时间（只要用户有操作，就刷新过期时间，达到自动延长的目的）
        redisCache.expire(SecurityConst.REDIS_KEY_LOGIN_USERNAME_TOKEN + username, expireTime);
    }


}

