package com.github.mengweijin.quickboot.auth.security;

import com.github.mengweijin.quickboot.auth.utils.SecurityUtils;
import com.github.mengweijin.quickboot.auth.async.LoginLogTask;
import com.github.mengweijin.quickboot.framework.util.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 登录失败监听
 * @author Meng Wei Jin
 **/
@Slf4j
@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    @Autowired
    private LoginLogTask loginLogTask;

    @Autowired
    private CacheManager cacheManager;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        WebAuthenticationDetails auth = (WebAuthenticationDetails) event.getAuthentication().getDetails();
        String username = event.getAuthentication().getPrincipal().toString();

        // 加入登录失败用户次数到缓存
        this.putLoginFailedCache(username);

        // 开启异步任务，写入失败登录日志
        this.recordLoginFailed(username, event.getException().getMessage(), auth.getRemoteAddress());
    }

    public void recordLoginFailed(String username, String message, String ip) {
        loginLogTask.addFailureLoginLog(ServletUtils.getRequest(), username, message, ip);
    }

    public void putLoginFailedCache(String username){
        // 加入登录失败用户次数到缓存
        AtomicInteger retryCount = getLoginFailedCacheCount(username);

        // 0 说明当前用户是第一次登录失败，缓存中还没有。添加一条定时清除缓存的任务，自动一段时间后，清除登录失败的缓存。
        // 这样可以避免用户一直没有登录成功，登录次数又达到最大限制时，陷入无法登录的死循环中。
        // 为什么要在第一次登录失败的时候才添加清除缓存的定时任务？
        // 因为一个 username 只需要添加一条清除缓存的任务，这是为了避免重复添加多个相同的任务。
        /*if(retryCount.get() == 0) {
            Expired expired = new Expired();
            expired.setExpire(confluxProperties.getLogin().getExpire());
            expired.setChronoUnit(ChronoUnit.MINUTES);

            CacheExpiredTask cacheExpiredTask = new CacheExpiredTask();
            cacheExpiredTask.setCacheManager(cacheManager);
            cacheExpiredTask.setCacheNames(new HashSet<>(Collections.singleton(SecurityUtils.LOGIN_FAILED_CACHE)));
            cacheExpiredTask.setCacheKey(username);

            cacheExpiredInterceptor.submitExpireTask(expired, cacheExpiredTask);
        }*/

        // 自增 1
        retryCount.incrementAndGet();
        Cache loginFailedCache = cacheManager.getCache(SecurityUtils.LOGIN_FAILED_CACHE);
        assert loginFailedCache != null;
        loginFailedCache.put(username, retryCount);
    }

    public AtomicInteger getLoginFailedCacheCount(String username) {
        Cache loginFailedCache = cacheManager.getCache(SecurityUtils.LOGIN_FAILED_CACHE);
        assert loginFailedCache != null;
        AtomicInteger retryCount = loginFailedCache.get(username, AtomicInteger.class);
        if(retryCount == null) {
            retryCount = new AtomicInteger(0);
        }
        return retryCount;
    }

}
