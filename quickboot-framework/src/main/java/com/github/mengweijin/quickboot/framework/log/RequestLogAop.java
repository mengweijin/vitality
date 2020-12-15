package com.github.mengweijin.quickboot.framework.log;

import cn.hutool.extra.servlet.ServletUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mengweijin.quickboot.framework.constant.Const;
import com.github.mengweijin.quickboot.framework.util.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

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
public class RequestLogAop {

    @Autowired
    private ObjectMapper objectMapper;

    private final ThreadLocal<AopLogger> threadLocal = new ThreadLocal<>();

    @Pointcut("@within(org.springframework.stereotype.Controller) || @within(org.springframework.web.bind.annotation.RestController)")
    public void logPointCut() {
    }

    @Before("logPointCut()")
    public void before(JoinPoint joinPoint){
        try {
            AopLogger aopLogger = new AopLogger();
            HttpServletRequest request = ServletUtils.getRequest();
            String httpMethod = request.getMethod();

            String requestParameter;
            if(HttpMethod.GET.name().equals(httpMethod)) {
                // 保存request，参数和值
                // 获取请求的参数
                // 通过ServletUtils.getRequest().getParameterMap()获得的对象为被锁定的对象，不能修改
                // 这里通过putAll方法，可以对基本数据类型的集合进行深拷贝，而对其他引用类型putAll不能实现深拷贝
                Map<String, String[]> paramsMap = new HashMap<>(request.getParameterMap());
                // 移除_csrf参数
                paramsMap.remove("_csrf");
                requestParameter = objectMapper.writeValueAsString(paramsMap);
            } else {
                Object[] args = joinPoint.getArgs();
                requestParameter = objectMapper.writeValueAsString(args);
            }

            // 设置方法名称
            String methodName = joinPoint.getTarget().getClass().getName() + Const.DOT + joinPoint.getSignature().getName();
            String url = request.getRequestURI();
            String ip = ServletUtil.getClientIP(request);

            aopLogger.setRequestParameter(requestParameter);
            aopLogger.setMethodName(methodName);
            aopLogger.setUrl(url);
            aopLogger.setHttpMethod(httpMethod);
            aopLogger.setOperateUtcTime(ZonedDateTime.now(ZoneOffset.UTC));
            aopLogger.setOperateLocalTime(LocalDateTime.now());
            aopLogger.setIp(ip);

            threadLocal.set(aopLogger);
        } catch (Exception e){
            log.error("An exception has occurred to record the Controller logs in the LogAspect!", e);
        }
    }

    /**
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "object")
    public void afterReturning(JoinPoint joinPoint, Object object) {
        recordLog(joinPoint, object, null);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
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
    protected void recordLog(final JoinPoint joinPoint, final Object object, final Exception e) {
        try {
            AopLogger aopLogger = threadLocal.get();
            aopLogger.setResponseBody(objectMapper.writeValueAsString(object));
            if (e != null) {
                aopLogger.setStatus(Const.FAILURE);
                aopLogger.setErrorInfo(e.getMessage());
            } else {
                aopLogger.setStatus(Const.SUCCESS);
            }

            // record controller logs
            log.debug(objectMapper.writeValueAsString(aopLogger));
        } catch (Exception ex) {
            log.error("An exception has occurred to record the Controller logs in the LogAspect!", ex);
        } finally {
            threadLocal.remove();
        }
    }
}
