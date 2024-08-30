package com.github.mengweijin.vitality.framework.repeatsubmit;

import com.github.mengweijin.vitality.framework.cache.CacheManagerFactory;
import com.github.mengweijin.vitality.framework.constant.Const;
import com.github.mengweijin.vitality.framework.domain.P;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.framework.util.ServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.dromara.hutool.core.array.ArrayUtil;
import org.dromara.hutool.crypto.SecureUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.cache.Cache;
import java.util.Collection;
import java.util.Map;
import java.util.StringJoiner;

/**
 * 防止重复提交
 *
 * @author mengweijin
 */
@Slf4j
@Aspect
@Component
@SuppressWarnings({"unused"})
public class RepeatSubmitAspect {

    @Pointcut("@annotation(repeatSubmit)")
    public void pointCut(RepeatSubmit repeatSubmit) {
    }

    @Around("pointCut(repeatSubmit)")
    public Object around(ProceedingJoinPoint joinPoint, RepeatSubmit repeatSubmit) throws Throwable {
        try {
            long interval = repeatSubmit.interval();
            if (interval < 1000 || interval > 10000) {
                throw new RuntimeException("Repeat submission interval can not be less than 1 second, and no longer than 10 seconds!");
            }

            String sessionId = ServletUtils.getSession().getId();
            // 请求地址
            String url = ServletUtils.getRequest().getRequestURI();
            // 请求参数
            String args = argsArrayToString(joinPoint.getArgs());
            // 唯一标识（指定key + url + args MD5）
            String key = String.join(Const.COLON, sessionId, url, SecureUtil.md5(args));

            Cache<String, Long> cache = CacheManagerFactory.getRepeatSubmitCache();
            Long cachedTimeMillis = cache.get(key);

            // 缓存中不存在，继续执行方法，然后加入缓存。或者缓存中存在，但超过了时间间隔，则这个 url 不为重复提交。
            if (cachedTimeMillis == null || ((System.currentTimeMillis() - cachedTimeMillis) > interval)) {
                Object object = joinPoint.proceed();
                // 加入/刷新缓存
                cache.put(key, System.currentTimeMillis());
                return object;
            } else {
                // 缓存中存在，并且在时间间隔内，则这个url视为重复提交
                log.warn("Repeat request, please try again later! url={}", url);
                return R.failure(HttpStatus.BAD_REQUEST.value(), repeatSubmit.message());
            }
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        StringJoiner params = new StringJoiner(" ");
        if (ArrayUtil.isEmpty(paramsArray)) {
            return params.toString();
        }
        for (Object obj : paramsArray) {
            if (obj != null && !isFilterObject(obj)) {
                params.add(P.writeValueAsString(obj));
            }
        }
        return params.toString();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param obj 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object obj) {
        Class<?> clazz = obj.getClass();
        if (clazz.isArray()) {
            return MultipartFile.class.isAssignableFrom(clazz.getComponentType());
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) obj;
            for (Object value : collection) {
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) obj;
            for (Object value : map.values()) {
                return value instanceof MultipartFile;
            }
        }
        return obj instanceof MultipartFile || obj instanceof HttpServletRequest || obj instanceof HttpServletResponse || obj instanceof BindingResult;
    }

}
