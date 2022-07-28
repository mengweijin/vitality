package com.github.mengweijin.quickboot.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author mengweijin
 * @date 2022/1/16
 */
class RSAUtilsTest {

    @Test
    void encrypt() {
        String password = "password";
        String encrypt = RSAUtils.encrypt(password);
        String decrypt = RSAUtils.decrypt(encrypt);
        Assertions.assertEquals(password, decrypt);
    }
}