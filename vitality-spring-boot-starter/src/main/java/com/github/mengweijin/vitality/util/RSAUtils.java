package com.github.mengweijin.vitality.util;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import lombok.extern.slf4j.Slf4j;

/**
 * RSA
 * @author mengweijin
 * @date 2021/12/24
 */
@Slf4j
public class RSAUtils {

    private static final RSA rsa = new RSA();

    public static String encrypt(String value) {
        return SecureUtil.rsa(rsa.getPrivateKeyBase64(), null).encryptBcd(value, KeyType.PrivateKey);
    }

    public static String decrypt(String value) {
        return SecureUtil.rsa(null, rsa.getPublicKeyBase64()).decryptStrFromBcd(value, KeyType.PublicKey);
    }

}
