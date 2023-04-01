package com.github.mengweijin.vitality.framework.aop.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mengweijin.vitality.framework.util.ServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

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
//@Aspect
@Deprecated
public class LogAspect {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 钩子函数。比如：可以把 SysLog 保存到数据库作为系统操作日志记录
     */
    private final Consumer<AopLog> consumer;

    public LogAspect(Consumer<AopLog> consumer){
        this.consumer = consumer;
    }

    @Pointcut("@within(org.springframework.stereotype.Controller) || @within(org.springframework.web.bind.annotation.RestController)")
    public void pointCut() {}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ServletUtils.getRequest();
        String requestMethod = request.getMethod();
        if(!HttpMethod.GET.name().equals(requestMethod)) {
            AopLog aopLog = new AopLog();
            try {
                aopLog.setMethodName(joinPoint.getTarget().getClass().getName() + ":" + joinPoint.getSignature().getName());
                aopLog.setUrl(request.getRequestURI());
                aopLog.setHttpMethod(requestMethod);
                aopLog.setCreateTime(LocalDateTime.now());
                aopLog.setIp(ServletUtils.getClientIP(request));
                aopLog.setSuccess(true);

                consumer.accept(aopLog);

                log.debug(objectMapper.writeValueAsString(aopLog));
            } catch (Exception e) {
                aopLog.setSuccess(false);
                aopLog.setError(e.getMessage());
                throw e;
            }
        }
        return joinPoint.proceed();
    }

}
