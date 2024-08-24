package com.github.mengweijin.vitality.framework.cache.options;

import java.time.Duration;

/**
 * @author mengweijin
 */
public interface ICacheOptions<K, V> {

    Long HEAP_ENTRIES_DEFAULT = 1000L;

    String getCacheName();

    Class<K> getKeyType();

    Class<V> getValueType();

    /**
     * 返回 null 则表示缓存永不过期
     *
     * @return Duration
     */
    Duration getExpiryDuration();

    default Long getHeapEntries() {
        return HEAP_ENTRIES_DEFAULT;
    }

}
