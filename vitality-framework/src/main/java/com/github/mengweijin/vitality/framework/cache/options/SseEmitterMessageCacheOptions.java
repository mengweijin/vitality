package com.github.mengweijin.vitality.framework.cache.options;

import com.github.mengweijin.vitality.framework.cache.CacheName;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.Duration;

/**
 * @author mengweijin
 */
@Component
@SuppressWarnings({"unused"})
public class SseEmitterMessageCacheOptions implements ICacheOptions<String, SseEmitter> {

    @Override
    public String getCacheName() {
        return CacheName.SSE_EMITTER_MESSAGE;
    }

    @Override
    public Class<String> getKeyType() {
        return String.class;
    }

    @Override
    public Class<SseEmitter> getValueType() {
        return SseEmitter.class;
    }

    @Override
    public Duration getExpiryDuration() {
        return null;
    }
}
