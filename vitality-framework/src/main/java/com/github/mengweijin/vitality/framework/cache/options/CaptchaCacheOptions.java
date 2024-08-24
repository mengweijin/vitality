package com.github.mengweijin.vitality.framework.cache.options;

import com.github.mengweijin.vitality.framework.cache.CacheName;
import org.dromara.hutool.swing.captcha.ICaptcha;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author mengweijin
 */
@Component
@SuppressWarnings({"unused"})
public class CaptchaCacheOptions implements ICacheOptions<String, ICaptcha> {

    @Override
    public String getCacheName() {
        return CacheName.CAPTCHA;
    }

    @Override
    public Class<String> getKeyType() {
        return String.class;
    }

    @Override
    public Class<ICaptcha> getValueType() {
        return ICaptcha.class;
    }

    @Override
    public Duration getExpiryDuration() {
        return Duration.ofMinutes(1L);
    }
}
