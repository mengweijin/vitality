package com.github.mengweijin.quickboot.auth.utils;

import com.github.mengweijin.quickboot.auth.properties.AuthProperties;
import com.github.mengweijin.quickboot.auth.security.SecurityConst;
import com.github.mengweijin.quickboot.framework.util.AESUtils;
import com.github.mengweijin.quickboot.framework.util.SpringUtils;
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

    /**
     * 生成一个 token，然后根据这个 username 封装一个 LoginUser 对象，放到 redis 中。
     * 解析的时候再把这个 username 解析出来，根据 username 从 redis 中获取 LoginUser 对象，如果没有获取到，说明 token 已经过期了。
     * @param secretKey secretKey
     * @param username username
     * @return
     */
    public static String createToken(String secretKey, String username) {
        final AuthProperties authProperties = SpringUtils.getBean(AuthProperties.class);
        final String secret = authProperties.getToken().getSecret();
        Map<String, Object> claims = new HashMap<>(1);
        claims.put(SecurityConst.JWT_KEY_LOGIN_USER_ID, AESUtils.encryptByKey(secret, username));
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

    public static String getUserIdFromToken(String secretKey, String token) {
        String usernameEncrypt = parseToken(secretKey, token).get(SecurityConst.JWT_KEY_LOGIN_USER_ID, String.class);
        final AuthProperties authProperties = SpringUtils.getBean(AuthProperties.class);
        final String secret = authProperties.getToken().getSecret();
        return AESUtils.decryptByKey(secret, usernameEncrypt);
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
