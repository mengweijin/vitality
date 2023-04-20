package com.github.mengweijin.vitality.framework.otp;

import cn.hutool.core.codec.Base32;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.otp.TOTP;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import java.time.Duration;
import java.time.Instant;

/**
 * @author mengweijin
 * @date 2023/4/16
 */
public class TOTPService {

    private static final QrConfig QR_CONFIG = new QrConfig();

    public static String generateSecretKey() {
        return TOTP.generateSecretKey(16);
    }

    public static String generateQRCode(String account, String secretKey) {
        String qrCodeContent = StrUtil.format("otpauth://totp/{}?secret={}", account, secretKey);
        return QrCodeUtil.generateAsBase64(qrCodeContent, QR_CONFIG, QrCodeUtil.QR_TYPE_SVG);
    }

    public static boolean validate(String secretKey, int code) {
        TOTP instance = new TOTP(Duration.ofSeconds(30), Base32.decode(secretKey));
        return instance.validate(Instant.now(), 0, code);
    }

}
