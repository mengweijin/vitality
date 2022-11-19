package com.github.mengweijin.vitality.util;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import com.github.mengweijin.quickboot.exception.QuickBootException;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author mengweijin
 * @date 2021/12/24
 */
@Slf4j
public class AESUtils {

    private static final AES aes = new AES();

    public static String encrypt(String value) {
        return aes.encryptBase64(value);
    }

    public static String decrypt(String value) {
        return aes.decryptStr(value);
    }

    public static String encryptByKey(String key, String value) {
        byte[] secretKey = generateSecretKey(key, 128);
        AES aes = SecureUtil.aes(secretKey);
        return aes.encryptBase64(value);
    }

    public static String decryptByKey(String key, String value) {
        byte[] secretKey = generateSecretKey(key, 128);
        AES aes = SecureUtil.aes(secretKey);
        return aes.decryptStr(value);
    }

    public static byte[] encryptByKey(String key, InputStream inputStream) {
        byte[] secretKey = generateSecretKey(key, 128);
        AES aes = SecureUtil.aes(secretKey);
        return aes.encrypt(inputStream);
    }

    public static byte[] decryptByKey(String key, InputStream inputStream) {
        byte[] secretKey = generateSecretKey(key, 128);
        AES aes = SecureUtil.aes(secretKey);
        return aes.decrypt(inputStream);
    }

    public static void encryptByKey(String key, FileInputStream in, FileOutputStream out) {
        byte[] secretKey = generateSecretKey(key, 128);
        AES aes = SecureUtil.aes(secretKey);
        aes.encrypt(in, out, true);
    }

    public static void decryptByKey(String key, FileInputStream in, FileOutputStream out) {
        byte[] secretKey = generateSecretKey(key, 128);
        AES aes = SecureUtil.aes(secretKey);
        aes.decrypt(in, out, true);
    }

    public static String generateRandomKey() {
        return IdUtil.fastSimpleUUID().substring(0, 16);
    }

    /**
     * 注意：不要使用下面的方式：
     * keygen.init(128, new SecureRandom(encodeRules.getBytes()));
     *
     * 这可能出现 javax.crypto.BadPaddingException: Given final block not properly padded
     * 原因：SecureRandom 实现完全随操作系统本身的內部状态，除非调用方在调用 getInstance 方法，然后调用 setSeed 方法；
     *      该实现在 windows 上每次生成的 key 都相同，但是在 solaris 或部分 linux 系统上则不同。
     *
     * 解决办法：调用getInstance方法
     * SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG") ;
     * secureRandom.setSeed(key.getBytes(StandardCharsets.UTF_8));
     */
    private static byte[] generateSecretKey(String key, int keySize) {
        try {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key.getBytes(StandardCharsets.UTF_8));

            SecretKey secretKey = KeyUtil.generateKey(SymmetricAlgorithm.AES.getValue(), keySize, secureRandom);
            return secretKey.getEncoded();
        } catch (NoSuchAlgorithmException e) {
            throw new QuickBootException(e);
        }

    }

    /**
     * 使用 generateSecretKey 方法来代替。这里仅做原生的写法展示。
     */
    @Deprecated
    public static byte[] generateSecretKeyByKeyGenerator(String key, int keySize) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(SymmetricAlgorithm.AES.getValue());
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG") ;
        secureRandom.setSeed(key.getBytes(StandardCharsets.UTF_8));
        keyGenerator.init(keySize, secureRandom);
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey.getEncoded();
    }
}
