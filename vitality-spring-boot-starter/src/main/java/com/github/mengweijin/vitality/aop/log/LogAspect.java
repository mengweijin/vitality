package com.github.mengweijin.vitality.aop.log;

import cn.hutool.extra.servlet.ServletUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mengweijin.vitality.util.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
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
     * 钩子函数。比如：可以把 SysLog 保存到数据库作为系统操作日志记录
     */
    private final Consumer<SysLog> consumer;

    public LogAspect(Consumer<SysLog> consumer){
        this.consumer = consumer;
    }

    @Pointcut("@within(org.springframework.stereotype.Controller) || @within(org.springframework.web.bind.annotation.RestController)")
    public void pointCut() {
        // just a point cut, no need do anything.
    }

    @Around("pointCut()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ServletUtils.getRequest();
        String requestMethod = request.getMethod();
        if(HttpMethod.GET.name().equals(requestMethod)) {
            joinPoint.proceed();
            return;
        }

        SysLog sysLog = new SysLog();
        try {
            sysLog.setMethodName(joinPoint.getTarget().getClass().getName() + ":" + joinPoint.getSignature().getName());
            sysLog.setUrl(request.getRequestURI());
            sysLog.setHttpMethod(requestMethod);
            sysLog.setCreateTime(LocalDateTime.now());
            sysLog.setIp(ServletUtil.getClientIP(request));
            sysLog.setSuccess(true);
            joinPoint.proceed();
            log.debug(objectMapper.writeValueAsString(sysLog));
        } catch (Exception e) {
            sysLog.setSuccess(false);
            sysLog.setError(e.getMessage());
            throw e;
        } finally {
            consumer.accept(sysLog);
        }
    }

}
