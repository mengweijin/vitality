package com.github.mengweijin.framework.cache;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

/**
 * @author mengweijin
 * @since 2022/10/29
 */
@Slf4j
public class CustomCacheEventListener implements CacheEventListener<Object, Object> {

    @Override
    public void onEvent(CacheEvent<?, ?> event) {
        log.debug("Cache event = {}, Key = {},  Old value = {}, New value = {}",
                event.getType(), event.getKey(), event.getOldValue(), event.getNewValue());
    }
}
