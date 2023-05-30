package com.github.mengweijin.vitality.framework.log;

import com.github.mengweijin.vitality.framework.util.Ip2regionUtils;
import com.github.mengweijin.vitality.framework.util.ServletUtils;
import com.github.mengweijin.vitality.system.entity.VtlLogOperation;
import com.github.mengweijin.vitality.system.service.VtlLogOperationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

import java.util.function.Consumer;

/**
 * @author Meng Wei Jin
 * <p>
 * - @Before 是在所拦截方法执行之前执行一段逻辑。
 * - @After 是在所拦截方法执行之后执行一段逻辑。
 * - @Around 是可以同时在所拦截方法的前后执行一段逻辑(写在point.proceed方法前就是之前执行, 写在point.proceed方法后就是之后执行)。
 * - @AfterReturning finally块中执行
 * - @AfterThrowing 捕获到异常会执行
 * 执行顺序：Before, After, AfterReturning, AfterThrowing
 * </p>
 **/
@Slf4j
@Aspect
public class LogAspect {

    private final ThreadLocal<VtlLogOperation> threadLocal = new ThreadLocal<>();

    @Autowired
    private VtlLogOperationService operationLogService;

    /**
     * 钩子函数。
     */
    private final Consumer<VtlLogOperation> consumer;

    public LogAspect(Consumer<VtlLogOperation> consumer){
        this.consumer = consumer;
    }

    @Pointcut("@within(org.springframework.stereotype.Controller) || @within(org.springframework.web.bind.annotation.RestController)")
    public void pointCut() {}

    @Before("pointCut()")
    public void before(JoinPoint joinPoint){
        try {
            HttpServletRequest request = ServletUtils.getRequest();
            String requestMethod = request.getMethod();
            if(!HttpMethod.GET.name().equals(requestMethod)) {
                VtlLogOperation operationLog = new VtlLogOperation();
                operationLog.setUrl(request.getRequestURI());
                operationLog.setHttpMethod(requestMethod);
                operationLog.setMethodName(joinPoint.getTarget().getClass().getName() + ":" + joinPoint.getSignature().getName());
                operationLog.setIp(ServletUtils.getClientIP(request));
                operationLog.setIpLocation(Ip2regionUtils.search(operationLog.getIp()));
                operationLog.setSucceeded(true);

                threadLocal.set(operationLog);
            }
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
        recordLog(null);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e 异常
     */
    @AfterThrowing(value = "pointCut()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Exception e) {
        recordLog(e);
    }

    /**
     * 记录日志
     * @param e 异常
     */
    private void recordLog(final Exception e) {
        VtlLogOperation operationLog = threadLocal.get();
        if(operationLog == null) {
            return;
        }

        try {
            operationLog.setSucceeded(e == null ? Boolean.TRUE : Boolean.FALSE);
            if(e != null) {
                operationLog.setErrorInfo(StrUtil.subByLength(e.getMessage(), 0, 500));
            }
            consumer.accept(operationLog);
            operationLogService.save(operationLog);
        } catch (Exception ex) {
            log.error("An exception has occurred to record the Controller logs in the LogAspect!", ex);
        } finally {
            threadLocal.remove();
        }
    }

}
