package com.mengweijin.mwjwork.framework.util;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @author
 * @description 获取i18n资源文件
 **/
public class MessageSourceUtils {

    /**
     * 根据消息键和参数 获取消息 委托给spring messageSource
     * <p>
     * 页面可使用<spring:message code="message.resource.key"></spring:message>标签获取国际化资源
     * 需导入：<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
     *
     * @param key  消息键
     * @param args 参数
     * @return
     */
    public static String message(String key, Object... args) {
        MessageSource messageSource = SpringUtils.getBean(MessageSource.class);
        return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
    }

}
