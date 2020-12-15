package com.github.mengweijin.quickboot.framework.web;

import com.github.mengweijin.quickboot.framework.constant.Const;
import com.github.mengweijin.quickboot.framework.util.ServletUtils;
import com.github.mengweijin.quickboot.framework.web.handler.ErrorInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * @author Meng Wei Jin
 * @description
 **/
@Slf4j
@Validated
public class BaseController {

    /**
     * 页面重定向: UrlBasedViewResolver.REDIRECT_URL_PREFIX
     * 页面转发: UrlBasedViewResolver.FORWARD_URL_PREFIX
     * 建议使用Restful风格，前后端分离
     */
    public String redirect(String url) {
        return String.format("redirect:%s", url);
    }

    /**
     * 设置请求参数
     * 建议使用Restful风格提供接口获取数据，前后端分离，而不是在后台通过在request中获取数据
     * @param key
     * @param value
     */
    public void setAttribute(String key, Object value){
        ServletUtils.getRequest().setAttribute(key, value);
    }

    /**
     * 数据校验发现不合法结果处理
     * 建议使用全局异常捕获处理
     * @param bindingResult bindingResult
     * @return
     */
    @Deprecated
    public ErrorInfo validateErrorResult(BindingResult bindingResult){
        List<ObjectError> errors = bindingResult.getAllErrors();

        ErrorInfo errorInfo = new ErrorInfo().setCode(HttpStatus.BAD_REQUEST.value());
        for (ObjectError err : errors) {
            errorInfo.addMessage(err.getDefaultMessage() + Const.SEMICOLON);
        }

        return errorInfo;
    }

}
