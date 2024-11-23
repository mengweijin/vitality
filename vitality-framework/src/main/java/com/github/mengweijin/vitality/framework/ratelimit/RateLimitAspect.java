package com.github.mengweijin.vitality.framework.ratelimit;

import com.github.mengweijin.vitality.framework.cache.CacheFactory;
import com.github.mengweijin.vitality.framework.constant.Const;
import com.github.mengweijin.vitality.framework.exception.ClientException;
import com.github.mengweijin.vitality.framework.util.ServletUtils;
import com.github.mengweijin.vitality.system.service.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.dromara.hutool.core.date.TimeUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.cache.Cache;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * 限流
 *
 * @author mengweijin
 */
@Slf4j
@Aspect
@Component
@SuppressWarnings({"unused"})
public class RateLimitAspect {

    private static final String CACHE_NAME_PREFIX = "RATE_LIMIT_";

    @Autowired
    private MessageService messageService;

    @Pointcut("@annotation(rateLimit)")
    public void pointCut(RateLimit rateLimit) {
    }

    @Around("pointCut(rateLimit)")
    public Object around(ProceedingJoinPoint joinPoint, RateLimit rateLimit) throws Throwable {
        String cacheKey;
        try {
            cacheKey = getCacheKey(rateLimit.strategy(), joinPoint);
            Cache<String, List<LocalDateTime>> cache = CacheFactory.getRateLimitCache();

            List<LocalDateTime> list = cache.get(cacheKey);
            if (list == null) {
                list = new ArrayList<>();
            }
            LocalDateTime current = LocalDateTime.now();
            // 移除掉已经超过统计时间区间的值
            list = list.stream().filter(item -> TimeUtil.between(item, current, ChronoUnit.SECONDS) < rateLimit.duration()).toList();

            // 未超过最大限制，覆盖更新缓存
            if (list.size() < rateLimit.max()) {
                List<LocalDateTime> cacheList = new ArrayList<>(list);
                cacheList.add(current);
                cache.put(cacheKey, cacheList);
                return joinPoint.proceed();
            }
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            throw e;
        }

        // 超过最大限制，抛出异常
        String msg = StrUtil.format("{} | cacheKey={}", rateLimit.message(), cacheKey);
        log.warn(msg);
        //messageService.sendMessageToRole();
        throw new ClientException(rateLimit.message());
    }

    public String getCacheKey(ERateLimitStrategy strategy, JoinPoint joinPoint) {
        String cacheKey = CACHE_NAME_PREFIX + strategy.name() + Const.UNDERSCORE;
        HttpServletRequest request = ServletUtils.getRequest();
        switch (strategy) {
            case API -> {
                cacheKey += joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()";
            }
            case IP -> {
                cacheKey += ServletUtils.getClientIP(request);
            }
            default -> {
            }
        }
        return cacheKey;
    }

}
