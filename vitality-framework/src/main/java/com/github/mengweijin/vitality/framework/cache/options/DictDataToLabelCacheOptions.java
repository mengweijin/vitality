package com.github.mengweijin.vitality.framework.cache.options;

import com.github.mengweijin.vitality.framework.cache.CacheName;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author mengweijin
 */
@Component
@SuppressWarnings({"unused"})
public class DictDataToLabelCacheOptions implements ICacheOptions<String, String> {

    @Override
    public String getCacheName() {
        return CacheName.DICT_DATA_VAL_TO_LABEL;
    }

    @Override
    public Class<String> getKeyType() {
        return String.class;
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
