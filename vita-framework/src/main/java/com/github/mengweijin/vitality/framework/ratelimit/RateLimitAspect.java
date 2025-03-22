package com.github.mengweijin.vitality.framework.ratelimit;

import com.github.mengweijin.vitality.framework.cache.CacheFactory;
import com.github.mengweijin.vitality.framework.constant.Const;
import com.github.mengweijin.vitality.framework.exception.ClientException;
import com.github.mengweijin.vitality.framework.satoken.LoginHelper;
import com.github.mengweijin.vitality.framework.util.ServletUtils;
import com.github.mengweijin.vitality.system.constant.ConfigConst;
import com.github.mengweijin.vitality.system.constant.UserConst;
import com.github.mengweijin.vitality.system.domain.entity.Config;
import com.github.mengweijin.vitality.system.enums.EMessageCategory;
import com.github.mengweijin.vitality.system.enums.EMessageTemplate;
import com.github.mengweijin.vitality.system.service.ConfigService;
import com.github.mengweijin.vitality.system.service.MessageService;
import com.github.mengweijin.vitality.system.service.RoleService;
import com.github.mengweijin.vitality.system.service.UserRoleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.date.TimeUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Component;

import javax.cache.Cache;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 限流
 *
 * @author mengweijin
 */
@Slf4j
@Aspect
@Component
@AllArgsConstructor
@SuppressWarnings({"unused"})
public class RateLimitAspect {

    private static final String CACHE_NAME_PREFIX = "RATE_LIMIT_";

    private MessageService messageService;

    private ConfigService configService;

    private RoleService roleService;

    private UserRoleService userRoleService;

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

        Set<Long> userIds = this.getMessageReceivers();
        Long loginId = LoginHelper.getLoginUserIdQuietly();
        String methodName = joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()";
        String rateLimitStrategyName = rateLimit.strategy().name();
        messageService.sendMessageToUsersAsync(userIds, EMessageCategory.ALERT, EMessageTemplate.TRIGGER_RATE_LIMIT, loginId, methodName, rateLimitStrategyName);

        throw new ClientException(rateLimit.message());
    }

    private Set<Long> getMessageReceivers() {
        Set<Long> userIds = new HashSet<>();
        Config config = configService.getByCode(ConfigConst.SYSTEM_ADMIN_ROLE_CODE);
        if (config != null && StrUtil.isNotBlank(config.getVal())) {
            userIds = userRoleService.getUserIdsByRoleCode(config.getVal());
        }
        if (CollUtil.isEmpty(userIds)) {
            userIds.add(UserConst.ADMIN_USER_ID);
        }
        return userIds;
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
