package com.github.mengweijin.vitality.framework.util;

import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.crypto.SecureUtil;
import org.dromara.hutool.crypto.symmetric.AES;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * <p>
 * 大文件需要直接写出到文件输出流 FileOutputStream，否则内存溢出。即使用如下方法才行：
 * encrypt(InputStream in, OutputStream out)
 * decrypt(InputStream in, OutputStream out)
 * <p>
 *
 * @author mengweijin
 * @since 2021/12/24
 */
@Slf4j
public class EncryptUtils {

    private static final byte[] KEY = {85, -74, -21, 118, 32, -56, 87, 2, 26, -50, 40, 33, 87, 42, 123, 63};

    private static final AES INSTANCE = SecureUtil.aes(KEY);

    public static String encrypt(String value) {
        return INSTANCE.encryptBase64(value);
    }

    public static String decrypt(String value) {
        return INSTANCE.decryptStr(value);
    }

    public static byte[] encrypt(byte[] data) {
        return INSTANCE.encrypt(data);
    }

    public static byte[] decrypt(byte[] data) {
        return INSTANCE.decrypt(data);
    }

    public static byte[] encrypt(InputStream inputStream) {
        return INSTANCE.encrypt(inputStream);
    }

    public static byte[] decrypt(InputStream inputStream) {
        return INSTANCE.decrypt(inputStream);
    }


    public static void encrypt(InputStream in, OutputStream out) {
        INSTANCE.encrypt(in, out, true);
    }

    public static void decrypt(InputStream in, OutputStream out) {
        INSTANCE.decrypt(in, out, true);
    }

}
