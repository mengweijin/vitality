package com.github.mengweijin.quickboot.framework.aspectj;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mengweijin.quickboot.framework.domain.AppLog;
import com.github.mengweijin.quickboot.framework.util.Const;
import com.github.mengweijin.quickboot.framework.util.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author Meng Wei Jin
 *
 * - @Before 是在所拦截方法执行之前执行一段逻辑。
 * - @After 是在所拦截方法执行之后执行一段逻辑。
 * - @Around 是可以同时在所拦截方法的前后执行一段逻辑(写在point.proceed方法前就是之前执行, 写在point.proceed方法后就是之后执行)。
 * - @AfterReturning finally块中执行
 * - @AfterThrowing 捕获到异常会执行
 * 执行顺序：Before, After, AfterReturning, AfterThrowing
 **/
@Slf4j
@Aspect
public class LogAspect {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 钩子函数。比如：可以把 AppLog 保存到数据库作为系统操作日志记录
     */
    private final Consumer<AppLog> consumer;

    public LogAspect(Consumer<AppLog> consumer){
        this.consumer = consumer;
    }

    private final ThreadLocal<AppLog> threadLocal = new ThreadLocal<>();

    @Pointcut("@within(org.springframework.stereotype.Controller) || @within(org.springframework.web.bind.annotation.RestController)")
    public void pointCut() {
        // just a point cut, no need do anything.
    }

    @Before("pointCut()")
    public void before(JoinPoint joinPoint){
        try {
            AppLog appLog = new AppLog();
            HttpServletRequest request = ServletUtils.getRequest();

            // 这里的 request.getParameterMap() 方法的调用必须早于下面 ServletUtils.getBody(request)
            // 否则也会发生下面注释中说到的流不能重复读取的问题，造成获取不到数据。
            Map<String, String[]> argsMap = request.getParameterMap();
            appLog.setArgs(argsMap);

            if(MediaType.APPLICATION_JSON_VALUE.equals(request.getContentType())) {
                // 这里会从 request 中通过流的方式读取 requestBody，而默认，流只能读取一次，第二次就读不到数据了。
                // 在 SpringMVC 中，会先解析 @RequestBody 注释的参数，而触发 requestBody 数据的流读取。
                // 此时就造成日志这里因为读取不到流数据而报错。
                // 解决方法：添加可重复读取流的过滤器，详情参见 RepeatableFilter
                String body = ServletUtil.getBody(request);
                if(StrUtil.isNotBlank(body)) {
                    HashMap<?, ?> requestBodyMap = objectMapper.readValue(body, HashMap.class);
                    appLog.setRequestBody(requestBodyMap);
                }
            }

            String methodName = joinPoint.getTarget().getClass().getName() + Const.DOT + joinPoint.getSignature().getName();
            appLog.setMethodName(methodName);
            appLog.setUrl(request.getRequestURI());
            appLog.setHttpMethod(request.getMethod());
            appLog.setOperateUtcTime(ZonedDateTime.now(ZoneOffset.UTC));
            appLog.setOperateLocalTime(LocalDateTime.now());
            appLog.setIp(ServletUtil.getClientIP(request));

            threadLocal.set(appLog);
        } catch (Exception e){
            log.error("An exception has occurred to record the Controller logs in the LogAspect!", e);
        }
    }

    /**
     * @param joinPoint 切点
     * @param object    返回的对象，参数名必须和注解中配置的名称保持一致。
     */
    @AfterReturning(pointcut = "pointCut()", returning = "object")
    public void afterReturning(JoinPoint joinPoint, Object object) {
        recordLog(joinPoint, object, null);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(value = "pointCut()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Exception e) {
        recordLog(joinPoint, null, e);
    }

    /**
     * 记录日志
     *
     * @param joinPoint
     * @param object
     * @param e
     */
    private void recordLog(final JoinPoint joinPoint, final Object object, final Exception e) {
        AppLog appLog = null;
        try {
            appLog = threadLocal.get();
            appLog.setResponseBody(object);
            if (e != null) {
                appLog.setStatus(Const.FAILURE);
                appLog.setErrorInfo(e.getMessage());
            } else {
                appLog.setStatus(Const.SUCCESS);
            }

            // record controller logs
            log.debug(objectMapper.writeValueAsString(appLog));
        } catch (Exception ex) {
            log.error("An exception has occurred to record the Controller logs in the LogAspect!", ex);
        } finally {
            // 钩子函数。比如：可以把 AppLog 保存到数据库作为系统操作日志记录
            consumer.accept(appLog);
            threadLocal.remove();
        }
    }

    /**
     * 获取方法上指定的注解
     *
     * @param joinPoint       JoinPoint
     * @param annotationClass annotationClass
     * @param <T>             T extends Annotation
     * @return T extends Annotation
     */
    public static <T extends Annotation> T getMethodAnnotation(JoinPoint joinPoint, Class<T> annotationClass) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        return method == null ? null : method.getAnnotation(annotationClass);
    }
}
