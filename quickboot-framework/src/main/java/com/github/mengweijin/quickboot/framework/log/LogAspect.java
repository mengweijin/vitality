package com.github.mengweijin.quickboot.framework.log;

import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.mengweijin.quickboot.framework.constant.Const;
import com.github.mengweijin.quickboot.framework.util.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mengweijin
 */
@Slf4j
@Aspect
@Component
@RestController
public class LogAspect {

    @Pointcut("@annotation(org.springframework.stereotype.Controller)")
    public void logPointCut() {
    }

    /**
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "responseObject")
    public void afterReturning(JoinPoint joinPoint, Object responseObject) {
        recordLog(joinPoint, responseObject, null);
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
     * @param responseObject
     * @param e
     */
    protected void recordLog(final JoinPoint joinPoint, final Object responseObject, final Exception e) {
        try {
            ControllerLog controllerLog = new ControllerLog();

            // 保存request，参数和值
            // 获取请求的参数
            // 通过ServletUtils.getRequest().getParameterMap()获得的对象为被锁定的对象，不能修改
            // 这里通过putAll方法，可以对基本数据类型的集合进行深拷贝，而对其他引用类型putAll不能实现深拷贝
            Map<String, String[]> map = new HashMap<>(ServletUtils.getRequest().getParameterMap());
            // 移除_csrf参数
            map.remove("_csrf");

            // 设置方法名称
            String method = joinPoint.getTarget().getClass().getName() + Const.DOT + joinPoint.getSignature().getName();
            String url = ServletUtils.getRequest().getRequestURI();
            LocalDateTime time = LocalDateTime.now();
            String operateIP = ServletUtil.getClientIP(ServletUtils.getRequest());

            controllerLog.setResponseObject(responseObject);
            controllerLog.setRequestParameter(JSONObject.toJSONString(map));
            controllerLog.setMethod(method);
            controllerLog.setUrl(url);
            controllerLog.setOperateTime(time);
            controllerLog.setOperateIP(operateIP);
            if (e != null) {
                controllerLog.setStatus(Const.FAILURE);
                controllerLog.setErrorInfo(e.getMessage());
            } else {
                controllerLog.setStatus(Const.SUCCESS);
            }

            // record controller logs
            log.info(controllerLog.toString());
        } catch (Exception ex) {
            log.error("An exception has occurred to record the Controller logs in the LogAspect!", ex);
        }
    }
}
