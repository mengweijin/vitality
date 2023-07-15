package com.github.mengweijin.vitality.framework.log;

import com.github.mengweijin.vitality.framework.constant.Const;
import com.github.mengweijin.vitality.framework.filter.repeatable.RepeatedlyRequestWrapper;
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
import org.dromara.hutool.core.io.IoUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.http.useragent.UserAgent;
import org.dromara.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
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
            UserAgent userAgent = ServletUtils.getUserAgent(request);
            String requestMethod = request.getMethod();
            String uri = request.getRequestURI();
            if(!HttpMethod.GET.name().equals(requestMethod) && !"/login".equals(uri) && !"/logout".equals(uri)) {
                VtlLogOperation operationLog = new VtlLogOperation();

                // 这里会从 request 中通过流的方式读取 requestBody，而默认，流只能读取一次，第二次就读不到数据了。
                // 在 SpringMVC 中，会先解析 @RequestBody 注释的参数，而触发 requestBody 数据的流读取。
                // 此时就造成日志这里因为读取不到流数据而报错。
                // 解决方法：添加可重复读取流的过滤器，详情参见 RepeatableFilter
                if (request instanceof RepeatedlyRequestWrapper repeatedlyRequest) {
                    String body = IoUtil.read(repeatedlyRequest.getInputStream(), StandardCharsets.UTF_8);
                    if(StrUtil.isNotBlank(body)) {
                        if(JSONUtil.isTypeJSON(body)) {
                            operationLog.setRequestBody(JSONUtil.formatJsonStr(body));
                        } else {
                            operationLog.setRequestBody(body);
                        }
                    }
                }

                // request.getParameterMap()也会发生下面注释中说到的流不能重复读取的问题，造成获取不到数据。
                Map<String, String[]> parameterMap = request.getParameterMap();
                String query = null;
                if(parameterMap != null) {
                    query = parameterMap.keySet().stream()
                            .map(k -> Arrays.stream(Optional.ofNullable(parameterMap.get(k)).orElse(new String[]{}))
                                    .reduce(Const.EMPTY, (result, next) -> result + Const.AMPERSAND + k + Const.EQUAL + next)
                            )
                            .reduce(Const.EMPTY, (result, next) -> result + next);
                }
                query = StrUtil.isBlank(query) ? Const.EMPTY : (Const.QUESTION_MARK + StrUtil.subAfter(query, Const.AMPERSAND, false));
                operationLog.setUrl(uri + query);

                operationLog.setHttpMethod(requestMethod);
                operationLog.setMethodName(joinPoint.getTarget().getClass().getName() + ":" + joinPoint.getSignature().getName());
                operationLog.setBrowser(userAgent.getBrowser().getName());
                operationLog.setOperatingSystem(userAgent.getOs().getName());
                operationLog.setPlatform(userAgent.getPlatform().getName());
                operationLog.setIp(ServletUtils.getClientIP(request));
                operationLog.setIpLocation(Ip2regionUtils.search(operationLog.getIp()));
                operationLog.setSucceeded(1);

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
            operationLog.setSucceeded(e == null ? 1 : 0);
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
