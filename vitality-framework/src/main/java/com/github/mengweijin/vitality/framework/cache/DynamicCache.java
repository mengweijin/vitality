package com.github.mengweijin.vitality.framework.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

/**
 * @author mengweijin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DynamicCache<K, V> {

    private Class<K> keyType;

    private Class<V> valueType;

    private Long heapEntries;

    /**
     * 值为 null 则表示缓存永不过期
     */
    private Duration expiry;

}
