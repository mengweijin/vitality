package com.github.mengweijin.quickboot.auth.token;

import cn.hutool.core.util.IdUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author mengweijin
 * @date 2022/1/8
 */
@Slf4j
class TokenUtilsTest {

    private static final String secretKey = "abcdefghijklmnopqrstuvwxyz";

    @Test
    public void createToken() {
        String uuid = IdUtil.fastUUID();
        log.debug(uuid);
        String token = TokenUtils.createToken(secretKey, uuid);
        log.debug(token);

        String parseUuid = TokenUtils.getUuidFromToken(secretKey, token);
        assertEquals(uuid, parseUuid);
    }

}