package com.github.mengweijin.vitality.framework.cache.options;

import com.github.mengweijin.vitality.framework.cache.CacheName;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

/**
 * @author mengweijin
 */
@Component
@SuppressWarnings({"rawtypes", "unused"})
public class DictDataListCacheOptions implements ICacheOptions<String, List> {

    @Override
    public String getCacheName() {
        return CacheName.DICT_DATA_LIST;
    }

    @Override
    public Class<String> getKeyType() {
        return String.class;
    }

    @Override
    public Class<List> getValueType() {
        return List.class;
    }

    @Override
    public Duration getExpiryDuration() {
        return Duration.ofDays(7L);
    }

}
