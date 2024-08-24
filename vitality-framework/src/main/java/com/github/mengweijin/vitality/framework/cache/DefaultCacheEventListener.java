package com.github.mengweijin.vitality.framework.cache;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

/**
 * @author mengweijin
 * @since 2022/10/29
 */
@Slf4j
@AllArgsConstructor
public class DefaultCacheEventListener implements CacheEventListener<Object, Object> {

    private String cacheName;

    @Override
    public void onEvent(CacheEvent<?, ?> event) {
        log.debug("Cache_name = {}, Cache_event = {}, Key = {},  Old_value = {}, New_value = {}",
                this.cacheName, event.getType(), event.getKey(), event.getOldValue(), event.getNewValue());
    }
}
