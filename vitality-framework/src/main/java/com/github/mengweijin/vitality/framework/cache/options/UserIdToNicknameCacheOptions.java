package com.github.mengweijin.vitality.framework.cache.options;

import com.github.mengweijin.vitality.framework.cache.CacheName;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author mengweijin
 */
@Component
@SuppressWarnings({"unused"})
public class UserIdToNicknameCacheOptions implements ICacheOptions<Long, String> {

    @Override
    public String getCacheName() {
        return CacheName.USER_ID_TO_NICKNAME;
    }

    @Override
    public Class<Long> getKeyType() {
        return Long.class;
    }

    @Override
    public Class<String> getValueType() {
        return String.class;
    }

    @Override
    public Duration getExpiryDuration() {
        return Duration.ofDays(7L);
    }

}
