package com.github.mengweijin.quickboot.auth.token;

import com.github.mengweijin.quickboot.auth.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author mengweijin
 * @date 2022/1/8
 */
@Slf4j
class TokenUtilsTest {

    private static final String secretKey = "abcdefghijklmnopqrstuvwxyz";

    @Test
    public void createToken() {
        String userId = "aaaa";
        String token = TokenUtils.createToken(secretKey, userId);
        log.debug(token);

        String parseUserId = TokenUtils.getUserIdFromToken(secretKey, token);
        assertEquals(userId, parseUserId);
    }

}