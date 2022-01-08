package com.github.mengweijin.quickboot.auth.token;

import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author mengweijin
 * @date 2022/1/8
 */
@Slf4j
class TokenServiceTest {

    @Test
    public void hutoolTokenTest(){
        String key = "123456";
        Map<String, Object> map = new HashMap<>();
        map.put("username", "admin");
        final String token = JWTUtil.createToken(map, key.getBytes(StandardCharsets.UTF_8));
        log.info(token);

        final JWT jwt = JWTUtil.parseToken(token);
        final JWTPayload payload = jwt.getPayload();
        Map<String, Object> payloadMap = payload.getClaimsJson();
        assertEquals("admin", payloadMap.get("username"));
    }

}