package com.github.mengweijin.framework.util;

import com.github.mengweijin.vitality.framework.util.AESUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author mengweijin
 */
class AESUtilsTest {

    @Test
    public void encryptByKey() {
        String KEY = "b8df6136f2588722";
        String VALUE = "Hello";

        byte[] valueBytes = VALUE.getBytes();
        Assertions.assertEquals("72 101 108 108 111", getBytesString(valueBytes));

        byte[] encrypted = AESUtils.encryptByKey(KEY, valueBytes);
        Assertions.assertEquals("-35 73 -2 -107 -125 127 59 -107 94 -102 124 -36 -105 73 -38 109", getBytesString(encrypted));
    }

    @Test
    public void generateSecretKey() {
        byte[] secretKey = AESUtils.generateSecretKey("Vitality", 128);
        System.out.println(getBytesString(secretKey));
    }

    private String getBytesString(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(b).append(", ");
        }
        return builder.toString().trim();
    }
}