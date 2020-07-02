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
     * 如果后台没有设置默认语言，页面访问时（thymeleaf）url参数可以添加 lang=zh_CN或lang=en_US这样的参数来实现国际化，也可以不添加。
     * 当不添加lang=en_US参数时，如果测试时语言没变化，一般是由于浏览器缓存，Shift+F5刷新一下即可。
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
