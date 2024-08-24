package com.github.mengweijin.vitality.framework.cache;

import org.dromara.hutool.extra.spring.SpringUtil;
import org.dromara.hutool.swing.captcha.ICaptcha;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.cache.Cache;
import javax.cache.CacheManager;
import java.util.List;

/**
 * @author mengweijin
 */
@Component
@SuppressWarnings({"rawtypes", "unused"})
public final class CacheManagerFactory {

    private static final CacheManager CACHE_MANAGER = SpringUtil.getBean(CacheManager.class);

    public static Cache<String, List> getDictListCache() {
        return CACHE_MANAGER.getCache(CacheName.DICT_DATA_LIST, String.class, List.class);
    }

    public static Cache<String, String> getDictDataLabelCache() {
        return CACHE_MANAGER.getCache(CacheName.DICT_DATA_VAL_TO_LABEL, String.class, String.class);
    }

    public static Cache<String, String> getDeptIdNameCache() {
        return CACHE_MANAGER.getCache(CacheName.DEPT_ID_TO_NAME, String.class, String.class);
    }

    public static Cache<Long, String> getUserIdUsernameCache() {
        return CACHE_MANAGER.getCache(CacheName.USER_ID_TO_USERNAME, Long.class, String.class);
    }

    public static Cache<Long, String> getUserIdNicknameCache() {
        return CACHE_MANAGER.getCache(CacheName.USER_ID_TO_NICKNAME, Long.class, String.class);
    }

    public static Cache<String, ICaptcha> getCaptchaCache() {
        return CACHE_MANAGER.getCache(CacheName.CAPTCHA, String.class, ICaptcha.class);
    }

    public static Cache<String, Long> getRepeatSubmitCache() {
        return CACHE_MANAGER.getCache(CacheName.REPEAT_SUBMIT, String.class, Long.class);
    }

    public static Cache<String, List> getConfigListCache() {
        return CACHE_MANAGER.getCache(CacheName.CONFIG_LIST, String.class, List.class);
    }

    public static Cache<String, SseEmitter> getSseEmitterMessageCache() {
        return CACHE_MANAGER.getCache(CacheName.SSE_EMITTER_MESSAGE, String.class, SseEmitter.class);
    }
}
