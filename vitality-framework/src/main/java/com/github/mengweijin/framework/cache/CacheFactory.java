package com.github.mengweijin.framework.cache;

import org.dromara.hutool.swing.captcha.ICaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.cache.Cache;
import javax.cache.CacheManager;
import java.util.List;

/**
 * @author mengweijin
 */
@Component
public final class CacheFactory {

    @Autowired
    private static CacheManager cacheManager;

    @SuppressWarnings("rawtypes")
    public static Cache<String, List> getDictDataCache() {
        return cacheManager.getCache(CacheName.DICT_DATA, String.class, List.class);
    }

    public static Cache<String, String> getDeptNameCache() {
        return cacheManager.getCache(CacheName.DEPT_NAME, String.class, String.class);
    }

    public static Cache<Long, String> getUsernameCache() {
        return cacheManager.getCache(CacheName.USERNAME, Long.class, String.class);
    }

    public static Cache<Long, String> getUserNicknameCache() {
        return cacheManager.getCache(CacheName.USER_NICKNAME, Long.class, String.class);
    }

    public static Cache<String, ICaptcha> getCaptchaCache() {
        return cacheManager.getCache(CacheName.CAPTCHA, String.class, ICaptcha.class);
    }

    public static Cache<String, Long> getRepeatSubmitCache() {
        return cacheManager.getCache(CacheName.REPEAT_SUBMIT, String.class, Long.class);
    }

}
