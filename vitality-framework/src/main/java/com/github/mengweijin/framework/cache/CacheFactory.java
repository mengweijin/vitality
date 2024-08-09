package com.github.mengweijin.framework.cache;

import org.dromara.hutool.extra.spring.SpringUtil;
import org.dromara.hutool.swing.captcha.ICaptcha;
import org.springframework.stereotype.Component;

import javax.cache.Cache;
import javax.cache.CacheManager;
import java.util.List;

/**
 * @author mengweijin
 */
@Component
public final class CacheFactory {

    private static final CacheManager CACHE_MANAGER = SpringUtil.getBean(CacheManager.class);

    @SuppressWarnings("rawtypes")
    public static Cache<String, List> getDictDataCache() {
        return CACHE_MANAGER.getCache(CacheName.DICT_DATA, String.class, List.class);
    }

    public static Cache<String, String> getDeptNameCache() {
        return CACHE_MANAGER.getCache(CacheName.DEPT_NAME, String.class, String.class);
    }

    public static Cache<Long, String> getUsernameCache() {
        return CACHE_MANAGER.getCache(CacheName.USERNAME, Long.class, String.class);
    }

    public static Cache<Long, String> getUserNicknameCache() {
        return CACHE_MANAGER.getCache(CacheName.USER_NICKNAME, Long.class, String.class);
    }

    public static Cache<String, ICaptcha> getCaptchaCache() {
        return CACHE_MANAGER.getCache(CacheName.CAPTCHA, String.class, ICaptcha.class);
    }

    public static Cache<String, Long> getRepeatSubmitCache() {
        return CACHE_MANAGER.getCache(CacheName.REPEAT_SUBMIT, String.class, Long.class);
    }

}
