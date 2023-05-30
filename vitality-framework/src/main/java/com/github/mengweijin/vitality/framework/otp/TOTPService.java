package com.github.mengweijin.vitality.framework.otp;

import org.dromara.hutool.core.codec.binary.Base32;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.crypto.digest.otp.TOTP;
import org.dromara.hutool.extra.qrcode.QrCodeUtil;
import org.dromara.hutool.extra.qrcode.QrConfig;
import java.time.Duration;
import java.time.Instant;

/**
 * 手机客户端可以使用：Microsoft Authenticator
 * @author mengweijin
 * @date 2023/4/16
 */
public class TOTPService {

    private static final QrConfig QR_CONFIG = new QrConfig();

    public static String generateSecretKey() {
        return TOTP.generateSecretKey(16);
    }

    /**
     *
     * @param secretKey 共享密钥。由 generateSecretKey() 生成共享密钥的Base32表示形式。
     * @param label 可以填写用户的名字、或登录名。可以在客户端可以清楚的标识用户信息。
     * @param issuer 代表应用名称，系统名称、代号等，比如 Google。
     * @return 图片 Base64 编码字符串
     */
    public static String generateQrCode(String secretKey, String label, String issuer) {
        String qrCodeContent = StrUtil.format("otpauth://totp/{}?secret={}&issuer={}", label, secretKey, issuer);
        return QrCodeUtil.generateAsBase64DataUri(qrCodeContent, QR_CONFIG, QrCodeUtil.QR_TYPE_SVG);
    }

    public static boolean validate(String secretKey, int code) {
        TOTP instance = new TOTP(Duration.ofSeconds(30), Base32.decode(secretKey));
        return instance.validate(Instant.now(), 0, code);
    }

}
