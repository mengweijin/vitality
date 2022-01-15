package com.github.mengweijin.quickboot.auth.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mengweijin
 * @date 2022/1/8
 */
public class TokenUtils {

    public static final String LOGIN_USER_JWT_KEY = "login_user_jwt_key";

    /**
     * 登录用户 token redis key 前缀
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 登录用户 username redis key 前缀
     */
    public static final String LOGIN_USER_KEY = "login_users:";

    /**
     * 根据 uuid 生成一个 token，然后根据这个 uuid 封装一个 LoginUser 对象，放到 redis 中。
     * 解析的时候再把这个 uuid 解析出来，根据 uuid 从 redis 中获取 LoginUser 对象，如果没有获取到，说明 token 已经过期了。
     * @param secretKey secretKey
     * @param uuid uuid
     * @return
     */
    public static String createToken(String secretKey, String uuid) {
        Map<String, Object> claims = new HashMap<>(1);
        claims.put(LOGIN_USER_JWT_KEY, uuid);
        return createToken(secretKey, claims);
    }

    /**
     * 从数据声明生成令牌
     *
     * @param secretKey secretKey
     * @param claims 数据声明
     * @return 令牌
     */
    private static String createToken(String secretKey, Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }

    public static String getUuidFromToken(String secretKey, String token) {
        return parseToken(secretKey, token).get(LOGIN_USER_JWT_KEY, String.class);
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param secretKey secretKey
     * @param token 令牌
     * @return 数据声明
     */
    private static Claims parseToken(String secretKey, String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
}
