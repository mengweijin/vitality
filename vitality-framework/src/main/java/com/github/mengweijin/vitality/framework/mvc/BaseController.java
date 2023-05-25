package com.github.mengweijin.vitality.framework.mvc;

import com.github.mengweijin.vitality.framework.util.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

/**
 * @author Meng Wei Jin
 **/
@Slf4j
@Validated
public abstract class BaseController {

    /**
     * 页面重定向: UrlBasedViewResolver.REDIRECT_URL_PREFIX
     * 页面转发: UrlBasedViewResolver.FORWARD_URL_PREFIX
     * 建议使用Restful风格，前后端分离
     */
    public String redirect(String url) {
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + url;
    }

    /**
     * 设置请求参数
     * 建议使用Restful风格提供接口获取数据，前后端分离，而不是在后台通过在request中获取数据
     */
    public void setAttribute(String key, Object value){
        ServletUtils.getRequest().setAttribute(key, value);
    }

}
