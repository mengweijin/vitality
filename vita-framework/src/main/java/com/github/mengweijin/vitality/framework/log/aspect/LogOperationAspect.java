package com.github.mengweijin.vitality.framework.log.aspect;

import com.github.mengweijin.vitality.framework.jackson.mapper.SensitiveObjectMapper;
import com.github.mengweijin.vitality.framework.log.aspect.annotation.Log;
import com.github.mengweijin.vitality.framework.repeatable.RepeatedlyRequestWrapper;
import com.github.mengweijin.vitality.framework.satoken.LoginHelper;
import com.github.mengweijin.vitality.framework.util.ServletUtils;
import com.github.mengweijin.vitality.system.domain.entity.LogOperation;
import com.github.mengweijin.vitality.system.enums.EYesNo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.dromara.hutool.core.io.IoUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.extra.spring.SpringUtil;
import org.dromara.hutool.json.JSONUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
@SuppressWarnings({"unused"})
public class LogOperationAspect {

    private static final ThreadLocal<StopWatch> STOP_WATCH = new ThreadLocal<>();

    @Pointcut("@annotation(com.github.mengweijin.vitality.framework.log.aspect.annotation.Log)")
    public void pointCut() {}

    /**
     * Before 中要获取自定义注解参数，方法参数中的变量名称可以为任意值，但必须和 @annotation(logAnnotation) 中的值一致。
     * AfterReturning、AfterThrowing 同理。
     *
     * @param joinPoint     joinPoint
     * @param logAnnotation logAnnotation
     */
    @Before("pointCut() && @annotation(logAnnotation)")
    public void before(JoinPoint joinPoint, Log logAnnotation) {
        StopWatch stopWatch = new StopWatch();
        STOP_WATCH.set(stopWatch);
        stopWatch.start();
    }

    /**
     * @param joinPoint 切点
     * @param object    返回的对象，参数名必须和注解中配置的名称保持一致。
     */
    @AfterReturning(pointcut = "pointCut() && @annotation(logAnnotation)", returning = "object")
    public void afterReturning(JoinPoint joinPoint, Log logAnnotation, Object object) {
        recordLog(joinPoint, logAnnotation, object, null);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e 异常
     */
    @AfterThrowing(value = "pointCut() && @annotation(logAnnotation)", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Log logAnnotation, Exception e) {
        recordLog(joinPoint, logAnnotation, null, e);
    }

    /**
     * 记录日志
     */
    private void recordLog(JoinPoint joinPoint, Log logAnnotation, Object object, Exception e) {
        try {
            HttpServletRequest request = ServletUtils.getRequest();
            String methodName = joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()";

            LogOperation logOperation = new LogOperation();
            logOperation.setTitle(logAnnotation.title());
            logOperation.setOperationType(logAnnotation.operationType().name());
            logOperation.setHttpMethod(request.getMethod());
            logOperation.setUrl(request.getRequestURI());
            logOperation.setMethodName(methodName);
            if (logAnnotation.saveRequestData()) {
                setRequestData(request, logOperation);
            }
            if (logAnnotation.saveResponseData() && object != null) {
                String responseData = SensitiveObjectMapper.writeValueAsString(object);
                logOperation.setResponseData(StrUtil.subByLength(responseData, 0, 2000));
            }
            logOperation.setSuccess(e == null ? EYesNo.Y.getValue() : EYesNo.N.getValue());
            if (e != null) {
                logOperation.setErrorMsg(StrUtil.subByLength(e.getMessage(), 0, 2000));
            }
            StopWatch stopWatch = STOP_WATCH.get();
            stopWatch.stop();
            logOperation.setCostTime(stopWatch.getDuration().toMillis());

            Long loginUserId = LoginHelper.getLoginUserIdQuietly();
            LocalDateTime now = LocalDateTime.now();
            logOperation.setCreateBy(loginUserId);
            logOperation.setUpdateBy(loginUserId);
            logOperation.setCreateTime(now);
            logOperation.setUpdateTime(now);

            SpringUtil.publishEvent(logOperation);
        } catch (Exception ex) {
            log.error("An exception has occurred to record the logs in the LogOperationAspect!", ex);
        } finally {
            STOP_WATCH.remove();
        }
    }

    private static final String REQUEST_ARGS = "REQUEST_ARGS";

    private static final String REQUEST_BODY = "REQUEST_BODY";

    private void setRequestData(HttpServletRequest request, LogOperation logOperation) throws IOException {
        Map<String, Object> dataMap = new LinkedHashMap<>();

        // request.getParameterMap()也会发生下面注释中说到的流不能重复读取的问题，造成获取不到数据。
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (parameterMap != null && !parameterMap.isEmpty()) {
            dataMap.put(REQUEST_ARGS, parameterMap);
        }

        // 这里会从 request 中通过流的方式读取 requestBody，而默认，流只能读取一次，第二次就读不到数据了。
        // 在 SpringMVC 中，会先解析 @RequestBody 注释的参数，而触发 requestBody 数据的流读取。
        // 此时就造成日志这里因为读取不到流数据而报错。
        // 解决方法：添加可重复读取流的过滤器，详情参见 RepeatableFilter
        if (request instanceof RepeatedlyRequestWrapper repeatedlyRequest) {
            String body = IoUtil.read(repeatedlyRequest.getInputStream(), StandardCharsets.UTF_8);
            if (StrUtil.isNotBlank(body)) {
                if (JSONUtil.isTypeJSONObject(body)) {
                    Map<?, ?> map = SensitiveObjectMapper.readValue(body, Map.class);
                    dataMap.put(REQUEST_BODY, map);
                } else if (JSONUtil.isTypeJSONArray(body)) {
                    List<?> list = SensitiveObjectMapper.readValue(body, List.class);
                    dataMap.put(REQUEST_BODY, list);
                } else {
                    dataMap.put(REQUEST_BODY, body);
                }
            }
        }
        if (!dataMap.isEmpty()) {
            logOperation.setRequestData(SensitiveObjectMapper.writeValueAsString(dataMap));
        }
    }

}
