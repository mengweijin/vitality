package com.github.mengweijin.vitality.framework.cache.options;

import com.github.mengweijin.vitality.framework.cache.CacheName;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author mengweijin
 */
@Component
@SuppressWarnings({"unused"})
public class RepeatSubmitCacheOptions implements ICacheOptions<String, Long> {

    @Override
    public String getCacheName() {
        return CacheName.REPEAT_SUBMIT;
    }

    @Override
    public Class<String> getKeyType() {
        return String.class;
    }

    @Override
    public Class<Long> getValueType() {
        return Long.class;
    }

    @Override
    public Duration getExpiryDuration() {
        return Duration.ofSeconds(10L);
    }
}
