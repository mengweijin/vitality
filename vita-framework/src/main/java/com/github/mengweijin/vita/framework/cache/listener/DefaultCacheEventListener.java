package com.github.mengweijin.vita.framework.cache.listener;

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

    @Override
    public void onEvent(CacheEvent<?, ?> event) {
        log.debug("cache_event = {}, key = {},  old_value = {}, new_value = {}",
                event.getType(), event.getKey(), event.getOldValue(), event.getNewValue());
    }
}
