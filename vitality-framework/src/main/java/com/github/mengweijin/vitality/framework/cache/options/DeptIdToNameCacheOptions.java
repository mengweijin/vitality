package com.github.mengweijin.vitality.framework.cache.options;

import com.github.mengweijin.vitality.framework.cache.CacheName;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author mengweijin
 */
@Component
@SuppressWarnings({"unused"})
public class DeptIdToNameCacheOptions implements ICacheOptions<Long, String> {

    @Override
    public String getCacheName() {
        return CacheName.DEPT_ID_TO_NAME;
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
