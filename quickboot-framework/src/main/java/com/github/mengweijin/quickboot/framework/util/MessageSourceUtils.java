package com.github.mengweijin.quickboot.framework.util;

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
     * <p>
     * 如果后台程序没有设置默认语言，页面访问时（thymeleaf）url参数需要添加 lang=zh_CN或lang=en_US这样的参数来实现国际化
     * thymeleaf中取国际化资源：<a th:text="#{key}"></a>
     * <p>
     * messages文件命名规则：
     * messages.properties
     * messages_zh_CN.properties
     * messages_en_US.properties
     *
     * @param key  message key in messages_zh_CN.properties
     * @param args args
     * @return value
     */
    public static String message(String key, Object... args) {
        MessageSource messageSource = SpringUtils.getBean(MessageSource.class);
        return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
    }

}
