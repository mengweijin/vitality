package com.github.mengweijin.quickboot.framework.util;

import cn.hutool.crypto.symmetric.AES;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author mengweijin
 * @date 2021/12/24
 */
@Slf4j
class AesUtilsTest {

    @Test
    void generateRandomKey() {
        String randomKey = AesUtils.generateRandomKey();
        log.info(randomKey);
        Assertions.assertEquals(16, randomKey.length());
    }

    @Test
    void encrypt() {
        String password = "123456789";
        log.info("password = " + password);

        String randomKey = AesUtils.generateRandomKey();
        log.info("randomKey = " + randomKey);

        String encrypt = AesUtils.encrypt(randomKey, password);
        log.info("encrypt = " + encrypt);

        String decrypt = AesUtils.decrypt(randomKey, encrypt);
        log.info("decrypt = " + decrypt);

        Assertions.assertEquals(password, decrypt);
    }
}